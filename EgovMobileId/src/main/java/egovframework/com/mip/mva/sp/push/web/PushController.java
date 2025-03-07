package egovframework.com.mip.mva.sp.push.web;

import egovframework.com.mip.mva.sp.comm.exception.SpException;
import egovframework.com.mip.mva.sp.comm.util.Base64Util;
import egovframework.com.mip.mva.sp.comm.vo.MipApiDataVO;
import egovframework.com.mip.mva.sp.comm.vo.T540VO;
import egovframework.com.mip.mva.sp.config.ConfigBean;
import egovframework.com.mip.mva.sp.push.service.PushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.push.web
 * @FileName PushController.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 3.
 * @Description 푸시 인터페이스 검증 처리 Controller
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
@RestController
@RequestMapping("/push")
public class PushController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PushController.class);

	/** 푸시 Service */
	private final PushService pushService;

	/**
	 * 생성자
	 * 
	 * @param pushService 푸시 Service
	 */
	public PushController(PushService pushService) {
		this.pushService = pushService;
	}

	/**
	 * 푸시 시작
	 * 
	 * @MethodName start
	 * @param t540 푸시 정보
	 * @return {"result": true, data: 푸시 정보 + Base64로 인코딩된 M200 메시지}
	 * @throws SpException
	 */
	@PostMapping(value = "/start")
	public MipApiDataVO start(@RequestBody T540VO t540) throws SpException {
		LOGGER.debug("Push 시작!");

		T540VO data = pushService.start(t540);

		MipApiDataVO mipApiData = new MipApiDataVO();

		mipApiData.setResult(true);
		mipApiData.setData(Base64Util.encode(ConfigBean.gson.toJson(data)));

		return mipApiData;
	}

}
