package rosis.web.test;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

public class TestController {
	
	@Value("${rosis.web.image}")
	private String imagePath;
	

	public static void main(String[] args) {
		
//		Date now = new Date();
//		String fileName = sdf.format(now);
//		System.out.println(fileName);
		
		
//		File file = new File("C:\\Users\\User\\Desktop\\정리\\카카오받은파일\\KakaoTalk_20230901_131715844.jpg");
		File dir = new File("C:\\Users\\User\\Desktop\\정리\\카카오받은파일");
		
		try {
			
			File[] files = dir.listFiles();
			
			for(int i = 0; i < files.length; i++) {
				if(files[i].isFile()) {
					String ext = files[i].getName().substring(files[i].getName().lastIndexOf(".")+1);
					if("png".equals(ext)) {
						System.out.println("ext : " + ext);
					}
				}
			}
			
			
//			FileInputStream fin = new FileInputStream(file);
//			int len = fin.available();
			
//			System.out.println(len);
//			byte[] fByte = fin.readAllBytes();
//			System.out.println(Base64.encodeBase64String(fByte).toString());
//			fin.read();
//			fin.close();
			
			
			
		}catch(Exception e) {
			
			e.printStackTrace();
			
		}
		
		
		
		
		//이미지 목록을 가져올 폴더위치를 지정(절대경로, 상대경로 모두 가능)
//		List<File> list = getImgFileList("D:\\workspace\\CompanyWeb\\src\\main\\resources\\static\\images\\businessImages");
//		
//		try {
//			
//			for(File f : list) {
//				System.out.println("getName() ====> " + f.getName()); // 파일 이름 출력
//				System.out.println("getPath() ====> " + f.getPath()); // 경로
//				System.out.println("getAbsolutePath() ====> " + f.getAbsolutePath()); // 절대 경로
//				System.out.println("getCanonicalPath() ====> " + f.getCanonicalPath()); // 정규 경로
//			}
//		}catch(IOException e) {
//			e.printStackTrace();
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
	}
	
	// 해당 경로의 이미지 파일 목록 반환
	public static List<File> getImgFileList(String path){
		return getImgFileList(new File(path));
	}
	
	// 해당 경로의 이미지 파일 목록 반환
	public static List<File> getImgFileList(File file){
		List<File> resultList = new ArrayList<File>(); // 이미지 파일을 저장할 리스트 생성
		
		// 지정한 이미지폴더가 존재 할지 않을경우 빈 리스트 반환
		System.out.println("파일존재여부 ==> " + file.exists());
		
		if(!file.exists()) return resultList;
		
		File[] list = file.listFiles(new FileFilter() {// 원하는 파일만 가져오기 위해 FileFilter정의
			String strImgExt = "jpg|jpeg|png|gif|bmp"; // 허용할 이미지 타입
			
			@Override
			public boolean accept(File pathname) {
				System.out.println(pathname);
				boolean chkResult = false;
				
				if(pathname.isFile()) {
					
					String ext = pathname.getName().substring(pathname.getName().lastIndexOf(".")+1);
                    //System.out.println("확장자:"+ext);
                    chkResult = strImgExt.contains(ext.toLowerCase());        
                    //System.out.println(chkResult +" "+ext.toLowerCase());

				}else {
					chkResult = true;
				}
				return chkResult;
			}
		}); 
		
		for(File f : list) {            
            if(f.isDirectory()) {
                //폴더이면 이미지목록을 가져오는 현재메서드를 재귀호출
                resultList.addAll(getImgFileList(f));                 
            }else {            
                //폴더가 아니고 파일이면 리스트(resultList)에 추가
                resultList.add(f);
            }
        }            
        return resultList; 
	}
	
    //확장자를 제외한 파일 이름 만 출력
    public static String getFileNameNoExt(String filepath){        
        String fileName = filepath.substring(0,  filepath.lastIndexOf("."));
        return fileName;
    }    
    
    //파일 확장자만 출력
    public static String getFileExt(String filepath){
        String ext = filepath.substring(filepath.lastIndexOf(".")+1);
        return ext;
    }
    
    //파일패스에서 이미지 상대경로 출력
    //절대경로에서 이미지폴더(images)를 중심으로 상대경로를 반환.
    //패스 : 절대결로/images/1-A/1-A_0.jpg 
    //    => 1-A/1-A_0.jpg
    public static String getImgSrc(File target){
        String url = target.getPath().substring(target.getPath().lastIndexOf("images"));
        return url;
    }
    
    //이미지를 포함하고있는 폴더의 이름 얻기. ( 개인 용도로 작성한 메서드 이므로 무시해도 무방 )
    public static String getImgDirName(File target){
        String url = getImgSrc(target);
        
        //System.out.println(url);
        //System.out.println(url.indexOf("\\")+1+"/"+url.lastIndexOf("\\"));
        
        int comp = url.lastIndexOf("\\") - (url.indexOf("\\")+1) ;
        //System.out.println(comp);
        String dirName ="";
        if(comp<0) {
            dirName ="이미지";
        }else  {
            dirName = url.substring(url.indexOf("\\")+1,url.lastIndexOf("\\"));    
        }        
        System.out.println("폴더명:"+dirName);
        
        return dirName;
    }
}
