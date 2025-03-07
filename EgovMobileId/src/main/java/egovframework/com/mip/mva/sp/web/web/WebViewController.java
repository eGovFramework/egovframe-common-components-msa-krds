package egovframework.com.mip.mva.sp.web.web;

import egovframework.com.mip.mva.sp.config.ConfigBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.web.web
 * @FileName WebViewController.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 3.
 * @Description Web 테스트 페이지 이동 Controller
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
@Controller
@RequestMapping("/web")
public class WebViewController {

	/** 설정정보 */
	private final ConfigBean configBean;

	/**
	 * 생성자
	 * 
	 * @param webService Web Service
	 */
	public WebViewController(ConfigBean configBean) {
		this.configBean = configBean;
	}

	/**
	 * 웹 테스트 페이지로 이동
	 * 
	 * @MethodName pcView
	 * @return 페이지 URL
	 */
	@GetMapping("/pcView")
	public String pcView(Model model) {
		model.addAttribute("serviceList", this.configBean.getVerifyConfig().getServiceList());
		model.addAttribute("caList", this.configBean.getVerifyConfig().getCaList());

		return "web/pcView";
	}

	/**
	 * 모바일웹 테스트 페이지로 이동
	 * 
	 * @MethodName mobileView
	 * @return 페이지 URL
	 */
	@GetMapping("/mobileView")
	public String mobileView(Model model) {
		model.addAttribute("serviceList", this.configBean.getVerifyConfig().getServiceList());
		model.addAttribute("caList", this.configBean.getVerifyConfig().getCaList());

		return "web/mobileView";
	}

}
