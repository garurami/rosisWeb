package rosis.web.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rosis.web.util.ImageUtil;

public class CustomerSlideGetImage {
	
	public Map<String, Object> getImgListMap(String imagePath) {
		
		System.out.println("imagePath(CustomerSlideGetImage.class) ====> " + imagePath);
		
		ImageUtil imgUtil = new ImageUtil();
		
		Map<String, Object> imgListMap = new HashMap<>();
		
		/**
		 * 이미지 폴더 위치
		 * 목록을 추가 하려면 해당 위치에 이미지 등록
		 */
		List<String> insImageList = imgUtil.getImage(imagePath + "insurance");	// 보험
		List<String> banImageList = imgUtil.getImage(imagePath + "bank");		// 금융/은행
		List<String> savImageList = imgUtil.getImage(imagePath + "save");		// 저축
		List<String> capImageList = imgUtil.getImage(imagePath + "capital");	// 캐피탈
		List<String> pubImageList = imgUtil.getImage(imagePath + "public");		// 공기업
		List<String> etcImageList = imgUtil.getImage(imagePath + "etc");		// 기타
		
		imgListMap.put("detailTitle", "고객사");
		imgListMap.put("insImageList", insImageList);
		imgListMap.put("banImageList", banImageList);
		imgListMap.put("savImageList", savImageList);
		imgListMap.put("capImageList", capImageList);
		imgListMap.put("pubImageList", pubImageList);
		imgListMap.put("etcImageList", etcImageList);
		
		return imgListMap;
	}

}
