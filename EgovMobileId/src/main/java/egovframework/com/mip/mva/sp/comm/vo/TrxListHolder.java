package egovframework.com.mip.mva.sp.comm.vo;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 인메모리 DB처럼 활용할 List를 생성한다.
 * @author 표준프레임워크 박성완
 * @since 2025.02.13
 * @version 4.3
 * <pre>
 *
 * ==================================================
 * DATE            AUTHOR          NOTE
 * ==================================================
 * 2025.02.13.     박성완           표준프레임워크 v4.3 - 최초생성
 *
 * </pre>
 */
@Component
public class TrxListHolder {
	
	// 인메모리 DB처럼 활용
	private final List<egovframework.com.mip.mva.sp.comm.vo.TrxInfoVO> trxList = new ArrayList<>();
	
	public List<egovframework.com.mip.mva.sp.comm.vo.TrxInfoVO> getTrxList(){
		return trxList;
	}
	

}
