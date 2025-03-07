package egovframework.com.mip.mva.sp.qrmpm.web;

import egovframework.com.mip.mva.sp.config.ConfigBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.qrmpm.web
 * @FileName QrmpmViewController.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 3.
 * @Description QR-MPM 페이지 이동 Controller
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * 2025.02.13,     박성완           표준프레임워크 v4.3 - qrmpmView 메서드 수정
 * </pre>
 */
@Controller
//@RequestMapping("/qrmpm")
public class QrmpmViewController {

	/** 설정정보 */
	private final ConfigBean configBean;

	/**
	 * 생성자
	 * 
	 * @param configBean ConfigBean
	 */
	public QrmpmViewController(ConfigBean configBean) {
		this.configBean = configBean;
	}

	/**
	 * QR-MPM 테스트 페이지로 이동
	 * 
	 * @MethodName qrmpmView
	 * @return 페이지 URL
	 * 
	 * qrmpmView 메서드 수정(2025.02.13 박성완)
	 *   - Annotation : GetMapping에서 RequestMapping으로 수정
	 *   - URL : PathVariable로 "/{license}" 추가
	 *   - model : model에 "license" 추가
	 */
	@RequestMapping(value="/mip/main/{license}", method={RequestMethod.GET, RequestMethod.POST})
	public String qrmpmView(@PathVariable("license") String license, Model model) {
		model.addAttribute("serviceList", this.configBean.getVerifyConfig().getServiceList());
		model.addAttribute("license", license);
		return "mip/main";
	}
	
}
