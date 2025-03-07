package egovframework.com.mip.mva.sp.push.web;

import egovframework.com.mip.mva.sp.config.ConfigBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.push.web
 * @FileName PushViewController.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 3.
 * @Description 푸시 테스트 페이지 이동 Controller
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
@Controller
@RequestMapping("/push")
public class PushViewController {

	/** 설정정보 */
	private final ConfigBean configBean;

	/**
	 * 생성자
	 * 
	 * @param configBean ConfigBean
	 */
	public PushViewController(ConfigBean configBean) {
		this.configBean = configBean;
	}

	/**
	 * 푸시 테스트 페이지로 이동
	 * 
	 * @MethodName pushView
	 * @return 페이지 URL
	 */
	@GetMapping("/pushView")
	public String pushView(Model model) {
		model.addAttribute("serviceList", this.configBean.getVerifyConfig().getServiceList());
		model.addAttribute("caList", this.configBean.getVerifyConfig().getCaList());

		return "push/pushView";
	}

}
