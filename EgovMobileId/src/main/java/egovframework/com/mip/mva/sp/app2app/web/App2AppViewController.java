package egovframework.com.mip.mva.sp.app2app.web;

import egovframework.com.mip.mva.sp.config.ConfigBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.app2app.web
 * @FileName App2AppViewController.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 3.
 * @Description App to App 테스트 페이지 이동 Controller
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
@Controller
@RequestMapping("/app2app")
public class App2AppViewController {

	/** 설정정보 */
	private final ConfigBean configBean;

	/**
	 * 생성자
	 * 
	 * @param configBean ConfigBean
	 */
	public App2AppViewController(ConfigBean configBean) {
		this.configBean = configBean;
	}

	/**
	 * Web to App 테스트 페이지 이동
	 * 
	 * @MethodName web2appView
	 * @return 페이지 URL
	 */
	@GetMapping("/web2appView")
	public String web2appView(Model model) {
		model.addAttribute("serviceList", this.configBean.getVerifyConfig().getServiceList());

		return "app2app/web2appView";
	}

}
