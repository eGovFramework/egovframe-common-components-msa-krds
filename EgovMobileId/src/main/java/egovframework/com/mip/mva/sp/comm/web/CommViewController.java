package egovframework.com.mip.mva.sp.comm.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.comm.web
 * @FileName CommViewController.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 3.
 * @Description 공통 페이지 이동 Controller
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * 2025.02.13.     박성완           표준프레임워크 v4.3 - main 메서드의 Annotation 수정
 * </pre>
 */
@Controller
public class CommViewController {

	/**
	 * Index 페이지 이동
	 * 
	 * @MethodName index
	 * @return 페이지 URL
	 */
	@GetMapping("/")
	public String index() {
		return "index";
	}

	/**
	 * Main 페이지 이동
	 * 
	 * @MethodName index
	 * @return 페이지 URL
	 */
	// @GetMapping > @RequestMapping, Mapping URL 수정 (2025.02.13. 박성완)
	@RequestMapping(value = "/mip/main", method={RequestMethod.GET, RequestMethod.POST})
	public String main() {
		return "mip/main";
	}

	/**
	 * 재검증 테스트 페이지 이동
	 * 
	 * @MethodName revpView
	 * @return 페이지 URL
	 */
	@GetMapping("/comm/revpView")
	public String qrmpmView() {
		return "comm/revpView";
	}

	/**
	 * VP 데이터 조회 테스트 페이지 이동
	 * 
	 * @MethodName vpdataView
	 * @return 페이지 URL
	 */
	@GetMapping("/comm/vpdataView")
	public String vpDataView() {
		return "comm/vpdataView";
	}

	/**
	 * Util 테스트 페이지 이동
	 * 
	 * @MethodName utilView
	 * @return 페이지 URL
	 */
	@GetMapping("/comm/utilView")
	public String utilView() {
		return "comm/utilView";
	}

	/**
	 * verifyConfig.json 생성 페이지 이동
	 * 
	 * @MethodName verifyConfig
	 * @return 페이지 URL
	 */
	@GetMapping("/comm/verifyConfig")
	public String createVerifyConfig() {
		return "comm/verifyConfigView";
	}

}
