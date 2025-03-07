package egovframework.com.mip.mva.sp.qrcpm.web;

import egovframework.com.mip.mva.sp.comm.exception.SpException;
import egovframework.com.mip.mva.sp.comm.util.Base64Util;
import egovframework.com.mip.mva.sp.comm.vo.MipApiDataVO;
import egovframework.com.mip.mva.sp.comm.vo.T520VO;
import egovframework.com.mip.mva.sp.config.ConfigBean;
import egovframework.com.mip.mva.sp.qrcpm.service.QrcpmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.qrcpm.web
 * @FileName QrcpmController.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 3.
 * @Description QR-CPM 인터페이스 검증 처리 Controller
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
@RestController
@RequestMapping("/qrcpm")
public class QrcpmController {

	private static final Logger LOGGER = LoggerFactory.getLogger(QrcpmController.class);

	/** QR-CPM Service */
	private final QrcpmService qrcpmService;

	/**
	 * 생성자
	 * 
	 * @param qrcpmService QR-CPM Service
	 */
	public QrcpmController(QrcpmService qrcpmService) {
		this.qrcpmService = qrcpmService;
	}

	/**
	 * QR-CPM 시작
	 * 
	 * @MethodName start
	 * @param t520 QR-CPM 정보
	 * @return {"result": true, data: QR-CPM 정보 + Base64로 인코딩된 M120 메시지}
	 * @throws SpException
	 */
	@PostMapping(value = "/start")
	public MipApiDataVO start(@RequestBody T520VO t520) throws SpException {
		LOGGER.debug("QR-CPM 시작!");

		T520VO data = qrcpmService.start(t520);

		MipApiDataVO mipApiData = new MipApiDataVO();

		mipApiData.setResult(true);
		mipApiData.setData(Base64Util.encode(ConfigBean.gson.toJson(data)));

		return mipApiData;
	}

}
