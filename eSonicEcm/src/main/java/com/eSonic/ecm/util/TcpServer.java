package com.eSonic.ecm.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eSonic.ecm.VO.EsContentVO;
import com.eSonic.ecm.VO.EsVolumeVO;
import com.eSonic.ecm.service.EsContentService;
import com.eSonic.ecm.service.EsUserService;
import com.eSonic.ecm.service.EsVolumeService;




public class TcpServer {
	
	public String g_Used_Volume = "";
    private static final Logger logger = LoggerFactory.getLogger(TcpServer.class);

//	dos.writeUTF(id + "_" + pwStr + "_" + contentClass);
//	
//
//	dos.writeUTF(archiveId + "_" + ext + "_" + fileSize);

	public void runServer() throws IOException {
		int port = 2102;
		ServerSocket serverSocket = new ServerSocket(port);
		
        logger.info("Server started on port " + port);

		while (true) {
	        logger.info("Waiting for client...");
			Socket socket = serverSocket.accept();

			DataInputStream dis = new DataInputStream(socket.getInputStream());
			PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
			String rtnStr = dis.readUTF();
	        logger.info("rtnStr : " + rtnStr);
			String id = rtnStr.split("_")[0];
			String pwStr = rtnStr.split("_")[1];
			String contentClass = rtnStr.split("_")[2];

			// ID PW 조회
			String rtnUserSearch = searchUser(id, pwStr);
			System.out.println(rtnUserSearch);
			switch (rtnUserSearch) {
			case "SUCCESS":
				pw.println(rtnUserSearch); // send response to client

				// create ();
				String archiveId = dis.readUTF().split("_")[0];
				String ext = dis.readUTF().split("_")[1];
				long fileSize = Long.parseLong(dis.readUTF().split("_")[2]);
				// volumeid 로 저장패스 지정
				String saveVol = searchVolume(archiveId, fileSize);
				// elementid 생성
				String filekey = createFileKey(saveVol);

				String savePath = saveVol + filekey;

				//DB 에 volume 업데이트
				EsVolumeVO esVolumeVO = null;
				esVolumeVO.setEsVolumeId(g_Used_Volume);
				esVolumeVO.setEsFileSize(fileSize);
				EsVolumeService esVolumeService = null;
				int udtCnt = esVolumeService.updateUsedVolume(esVolumeVO);
				
				
				
				//파일 저장
				FileOutputStream fos = new FileOutputStream(savePath);

				byte[] buffer = new byte[4096];
				int read;
				long totalRead = 0L;

				while (totalRead < fileSize) {
					read = dis.read(buffer);
					totalRead += read;
					fos.write(buffer, 0, read);
				}

				fos.close();
				// DB 에 Content insert
				EsContentService esContentService = null;
				EsContentVO esContentVO = null;
				esContentVO.setEsContentId(filekey);
				esContentVO.setEsContentClassId(contentClass);
				esContentVO.setEsArchiveId(archiveId);
				esContentVO.setEsFilePath(savePath);
				
				esContentVO.setEsFileExt(ext);
				esContentVO.setEsFileSize(fileSize);
				
				esContentVO.setEsCreateUser(id);
				esContentVO.setEsLastAccessUser(id);
				int rtnInt = esContentService.insertEsContentMyBatis(esContentVO);
				if(rtnInt == 1) {

					// 리턴
					pw.println(filekey); // send response to client
				}else {

					pw.println("-1"); // send response to client
				}
				


				break;

			default:
				System.out.println("Exit");
				pw.println(rtnUserSearch); // send response to client

				dis.close();
				pw.close();
				socket.close();

				break;

			}


			dis.close();
			pw.close();
			socket.close();
		}

	}

	public String createFileKey(String volumePath) {
		String rtnFileKey = "";
		try {
			// get current time
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
			String currentTime = now.format(formatter);

			// count the number of files in the directory that start with the current time
			long count;
			count = Files.list(Paths.get(volumePath))
					.filter(path -> path.getFileName().toString().startsWith(currentTime)).count();
			

			// generate file name
			String twoDigitNumber = String.format("%02d", (count + 1));
			String fileName = currentTime + twoDigitNumber;

			return fileName;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return rtnFileKey;
		}

	}

    private HttpClient client = HttpClient.newHttpClient();
    public String rtnUrl(String url, String esUserId) {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url + esUserId))
            .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	public String searchUser(String id, String pw) {
        logger.info("test : " + id);
		String rtnStr = "";
		EsUserService esUserService = null;
		String password = rtnUrl("http://localhost:8080/eSonicEcm/interface/search/one/",id);
        logger.info("password : " + password);
        
        
//		if (password.split(" : ")[0].equals("NODATA")) {
//
//		}
//
//		switch (password.split(" : ")[0]) {
//		case "NODATA":
//			rtnStr = password.split(" : ")[0];
//			break;
//		case "EXCEPTION":
//			rtnStr = password;
//			break;
//		case "SUCCESS":
//			if (pw.equals(password.split(" : ")[1])) {
//				rtnStr = "SUCCESS";
//			} else {
//
//				rtnStr = "FAILED";
//			}
//			break;
//
//		}

		return rtnStr;
	}
	

	public String searchVolume(String ARCHIVE_ID, long FileSize) {
		String rtnVol = "";
		EsVolumeService esVolumeService = null;
		//Mybatis 로 할거임 여기는
		EsVolumeVO esVolumeVO = null;
		esVolumeVO.setEsArchiveId(ARCHIVE_ID);
		esVolumeVO.setEsFileSize(FileSize);
		esVolumeVO = esVolumeService.getUsedVolume(esVolumeVO);
		rtnVol = esVolumeVO.getEsVolumeName() + "\\" + esVolumeVO.getEsArchiveName() + "\\" ;
		g_Used_Volume = esVolumeVO.getEsVolumeId();
		File directory = new File(rtnVol);

		 // 모든 하위 폴더를 가져옵니다.
        File[] subfolders = directory.listFiles(File::isDirectory);

        // 폴더가 없을 경우 1을 반환합니다.
        if (subfolders == null || subfolders.length == 0) {
            return rtnVol + "\\1\\";
        }

        // 가장 마지막 폴더를 찾습니다.
        File lastFolder = null;
        for (File folder : subfolders) {
            if (lastFolder == null || folder.lastModified() > lastFolder.lastModified()) {
                lastFolder = folder;
            }
        }
        // 폴더명을 출력합니다.
        if (lastFolder != null) {
        	 File[] files = lastFolder.listFiles();

            
        	if(files.length>=100) {

            	rtnVol = rtnVol + "\\" + (Integer.parseInt(lastFolder.getName()) + 1) + "\\";
        	}else {
            	rtnVol = rtnVol + "\\" + lastFolder.getName() + "\\";
        		
        	}
        }else {

        	rtnVol =  rtnVol + "\\1\\";
        }
		
		
		return rtnVol;

	}

}