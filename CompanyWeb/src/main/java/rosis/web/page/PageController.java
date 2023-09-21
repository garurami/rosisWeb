package rosis.web.page;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import rosis.web.common.CustomerSlideGetImage;
import rosis.web.util.DeptUtil;
import rosis.web.util.ImageUtil;
import rosis.web.vo.DeptVO;
import rosis.web.vo.SolutionVO;

@Controller
public class PageController {
	
	@Value("${rosis.web.image}")
	private String imagePath;
	
	/**
	 * 메인 화면
	 * @param model
	 * @return
	 */
	@GetMapping("/")
	public String mainPageMove(Model model) {
		
		CustomerSlideGetImage imgListMap = new CustomerSlideGetImage();
		
		model.addAttribute("imgListMap", imgListMap.getImgListMap(imagePath));
		
		return "main";
	}
	
	
	/**
	 * 회사 소개
	 * @param about
	 * @return
	 */
	@GetMapping("/about")
	public String aboutPageMove(@RequestParam("about") String about, Model model) {
		
		model.addAttribute("detailTitle", "회사 소개");
		
		// dept가 여러개 일 경우 
		DeptUtil deptUtil = new DeptUtil();
		List<DeptVO> deptList = new ArrayList<>();
		
		switch(about) {
			case "1" :
				deptList = deptUtil.deptList(new String[] {"CEO인사말"});
				break;
			case "2" :
				deptList = deptUtil.deptList(new String[] {"회사개요"});
				break;
			case "3" :
				deptList = deptUtil.deptList(new String[] {"연혁"});
				break;
			case "4" :
				deptList = deptUtil.deptList(new String[] {"비젼"});
				break;
			case "5" :
				deptList = deptUtil.deptList(new String[] {"오시는 길"});
				break;
		}
		
		model.addAttribute("deptList", deptList);
		
		return "aboutUs/about" + about;
	}
	
	/**
	 * 사업분야
	 * @return
	 */
	@GetMapping("/services")
	public String servicePageMove(Model model) {
		
		model.addAttribute("detailTitle", "사업분야");
		
		return "services";
	}
	
	/**
	 * 솔루션 목록
	 * @return
	 */
	@GetMapping("/solutionList")
	public String solutionListPageMove(Model model){
		
		
		ImageUtil imgUtil = new ImageUtil();
		
		/**
		 * solution 이미지 위치
		 * solution 목록을 추가 하려면 해당 위치에 이미지 등록
		 * 단 solution에 이미지 등록할때 solution 뒤에 숫자 하나씩 더 증가 시켜야 됨
		 */
		List<String> imageList = imgUtil.getImage(imagePath + "solution");
		
		/**
		 * 솔루션 이름 목록
		 */
		@SuppressWarnings("serial")
		List<String> solutionNmList = new ArrayList<>() {{
			add("신분증 진위확인 솔루션");
			add("법원 문서 인식 솔루션");
			add("출금이체증빙(CMS) 솔루션");
		}};
		
		/**
		 * 솔루션 이름에 맞게 이미지 삽입
		 */
		@SuppressWarnings("serial")
		List<SolutionVO> solutionList = new ArrayList<>() {{
			for(int i = 0; i < solutionNmList.size(); i++) {
				SolutionVO solutionMap = new SolutionVO();
				solutionMap.setSolutionNm(solutionNmList.get(i));
				solutionMap.setSolutionImageSrc(imageList.get(i));
				
				add(solutionMap);
			}
		}};
		
		
		model.addAttribute("detailTitle", "솔루션");
		model.addAttribute("imageList", imageList);
		model.addAttribute("solutionNmList", solutionNmList);
		model.addAttribute("solutionList", solutionList);
		
		return "solution/solutionList";
	}
	
	@GetMapping("/solutionDetail")
	public String solutionDetail(@RequestParam String solution, Model model) {
		
		model.addAttribute("detailTitle", "솔루션");
		
		// dept가 여러개 일 경우
		DeptUtil deptUtil = new DeptUtil();
		List<DeptVO> deptList = new ArrayList<>();
		
		String solutionPage = "";
		
		switch(solution) {
			case "1" :
				deptList = deptUtil.deptList(new String[] {"신분증 진위확인 솔루션"});
				solutionPage = "solution/solution1";
				break;
			case "2" :
				deptList = deptUtil.deptList(new String[] {"법원 문서 인식 솔루션"});
				solutionPage = "solution/solution2";
				break;
			case "3" :
				deptList = deptUtil.deptList(new String[] {"출금이체증빙(CMS) 솔루션"});
				solutionPage = "solution/solution3";
				break;
		}
		
		model.addAttribute("deptList", deptList);
		
		return solutionPage;
	}
	
	/**
	 * 고객사
	 * @return
	 */
	@GetMapping("customer")
	public String customerPageMove(Model model) {
		
		CustomerSlideGetImage imgListMap = new CustomerSlideGetImage();
		
		model.addAttribute("detailTitle", "고객사");
		model.addAttribute("imgListMap", imgListMap.getImgListMap(imagePath));
		
		return "customer/customer";
	}
	
	/**
	 * 제품소개
	 * @return
	 */
	@GetMapping("product")
	public String productPageMove(Model model) {
		
		model.addAttribute("detailTitle", "제품소개");
		
		return "product";
	}
	
	/**
	 * 개발 사업 범위
	 * @return
	 */
	@GetMapping("devProjectScope")
	public String devProjectScopePageMove(Model model) {
		
		model.addAttribute("detailTitle", "개발사업범위");
		
		return "devProjectScope";
	}
	
}
