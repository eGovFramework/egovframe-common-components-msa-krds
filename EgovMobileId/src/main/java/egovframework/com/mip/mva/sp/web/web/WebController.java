package egovframework.com.mip.mva.sp.web.web;

import egovframework.com.mip.mva.sp.comm.exception.SpException;
import egovframework.com.mip.mva.sp.comm.util.Base64Util;
import egovframework.com.mip.mva.sp.comm.vo.*;
import egovframework.com.mip.mva.sp.config.ConfigBean;
import egovframework.com.mip.mva.sp.web.service.WebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.web.web
 * @FileName WebController.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 3.
 * @Description Web 검증 Controller
 * 
 *              <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 *              </pre>
 */
@RestController
@RequestMapping("/web")
public class WebController {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebController.class);

	/** Web Service */
	private final WebService webService;

	/**
	 * 생성자
	 * 
	 * @param webService Web Service
	 */
	public WebController(WebService webService) {
		this.webService = webService;
	}

	/**
	 * 푸시 시작
	 * 
	 * @MethodName pushStart
	 * @param t540 푸시 정보
	 * @return {"result": true, data: 푸시 정보 + Base64로 인코딩된 M200 메시지}
	 * @throws SpException
	 */
	@PostMapping(value = "/push/start")
	public MipApiDataVO pushStart(@RequestBody T540VO t540) throws SpException {
		LOGGER.debug("Push 시작!");

		T540VO data = webService.pushStart(t540);

		MipApiDataVO mipApiData = new MipApiDataVO();

		mipApiData.setResult(true);
		mipApiData.setData(Base64Util.encode(ConfigBean.gson.toJson(data)));

		return mipApiData;
	}

	/**
	 * QR-MPM 시작
	 * 
	 * @MethodName qrStart
	 * @param t510 QR-MPM 정보
	 * @return {"result": true, data: QR-MPM 정보 + Base64로 인코딩된 M200 메시지}
	 * @throws SpException
	 */
	@PostMapping(value = "/qr/start")
	public MipApiDataVO qrStart(@RequestBody T510VO t510) throws SpException {
		LOGGER.debug("QR-MPM 시작!");

		T510VO data = webService.qrMpmStart(t510);

		MipApiDataVO mipApiData = new MipApiDataVO();

		mipApiData.setResult(true);
		mipApiData.setData(Base64Util.encode(ConfigBean.gson.toJson(data)));

		return mipApiData;
	}

	/**
	 * App to App 시작
	 * 
	 * @MethodName start
	 * @param t530 App to App 정보
	 * @return {"result": true, data: App to App 정보 + Base64로 인코딩된 M200 메시지}
	 * @throws SpException
	 */
	@PostMapping(value = "/apptoapp/start")
	public MipApiDataVO appToAppStart(@RequestBody T530VO t530) throws SpException {
		LOGGER.debug("App to App 시작!");

		T530VO data = webService.appToAppStart(t530);

		MipApiDataVO mipApiData = new MipApiDataVO();

		mipApiData.setResult(true);
		mipApiData.setData(Base64Util.encode(ConfigBean.gson.toJson(data)));

		return mipApiData;
	}

	/**
	 * 인증 완료
	 * 
	 * @MethodName crtfcCmpl
	 * @param trxcode 거래코드
	 * @return {"result": true}
	 * @throws SpException
	 */
	@PostMapping(value = "/crtfc/cmpl")
	public MipApiDataVO crtfcCmpl(@RequestBody TrxInfoVO trxInfo) throws SpException {
		LOGGER.debug("인증 완료!");

		webService.crtfcCmpl(trxInfo.getTrxcode());

		MipApiDataVO mipApiData = new MipApiDataVO();

		mipApiData.setResult(true);

		return mipApiData;
	}

	/**
	 * RSA 암호화
	 * 
	 * @MethodName rsaEncrypt
	 * @param {"data": 평문 데이터, "decryptTargetDid": 복호화 대상 DID}
	 * @return {"result": true, data: 암호화 데이터}
	 * @throws SpException
	 */
	@PostMapping(value = "/rsa/encrypt")
	public MipApiDataVO rsaEncrypt(@RequestBody Map<String, String> data) throws SpException {
		LOGGER.debug("RSA 암호화!");

		String pranText = data.get("data");
		String decryptTargetDid = data.get("decryptTargetDid");

		String encryptData = webService.rsaEncrypt(pranText, decryptTargetDid);

		MipApiDataVO mipApiData = new MipApiDataVO();

		mipApiData.setResult(true);
		mipApiData.setData(encryptData);

		return mipApiData;
	}

	/**
	 * RSA 복호화
	 * 
	 * @MethodName rsaDecrypt
	 * @param data 암호화 데이터
	 * @return {"result": true, data: 복호화 데이터}
	 * @throws SpException
	 */
	@PostMapping(value = "/rsa/decrypt")
	public MipApiDataVO rsaDecrypt(@RequestBody Map<String, String> data) throws SpException {
		LOGGER.debug("RSA 복호화!");

		String encryptText = data.get("data");

		String decryptData = webService.rsaDecrypt(encryptText);

		MipApiDataVO mipApiData = new MipApiDataVO();

		mipApiData.setResult(true);
		mipApiData.setData(decryptData);

		return mipApiData;
	}

}
