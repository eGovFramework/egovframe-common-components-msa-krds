package egovframework.com.mip.mva.sp.app2app.web;

import egovframework.com.mip.mva.sp.app2app.service.App2AppService;
import egovframework.com.mip.mva.sp.comm.exception.SpException;
import egovframework.com.mip.mva.sp.comm.util.Base64Util;
import egovframework.com.mip.mva.sp.comm.vo.MipApiDataVO;
import egovframework.com.mip.mva.sp.comm.vo.T530VO;
import egovframework.com.mip.mva.sp.config.ConfigBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.app2app.web
 * @FileName App2AppController.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 8.
 * @Description App to App 인터페이스 검증 처리 Controller
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
@RestController
@RequestMapping("/app2app")
public class App2AppController {

	private static final Logger LOGGER = LoggerFactory.getLogger(App2AppController.class);

	/** App to App Service */
	private final App2AppService app2AppService;

	/**
	 * 생성자
	 * 
	 * @param app2AppService App to App Service
	 */
	public App2AppController(App2AppService app2AppService) {
		this.app2AppService = app2AppService;
	}

	/**
	 * App to App 시작
	 * 
	 * @MethodName start
	 * @param t530 App to App 정보
	 * @return App to App 정보 + Base64로 인코딩된 M200 메시지
	 * @throws SpException
	 */
	@PostMapping(value = "/start")
	public MipApiDataVO start(@RequestBody T530VO t530) throws SpException {
		LOGGER.debug("App to App 시작!");

		T530VO data = app2AppService.start(t530);

		MipApiDataVO mipApiData = new MipApiDataVO();

		mipApiData.setResult(true);
		mipApiData.setData(Base64Util.encode(ConfigBean.gson.toJson(data)));

		return mipApiData;
	}

}
