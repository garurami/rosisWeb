package rosis.web.util;

import java.util.ArrayList;
import java.util.List;

import rosis.web.vo.DeptVO;

public class DeptUtil {

	/**
	 * dept가 증가하는 util
	 * dept1 > dept2 > dept 3
	 * @param str
	 * @return
	 */
	public List<DeptVO> deptList(String[] deptArr){
		
		List<DeptVO> deptList = new ArrayList<>();
		
		for(int i = 0; i < deptArr.length; i++) {
			deptList.add(new DeptVO(deptArr[i]));
		}
		
		return deptList;
	}
	
}
