package egovframework.com.mip.mva.sp.app2app.service.impl;

import egovframework.com.mip.mva.sp.app2app.service.App2AppService;
import egovframework.com.mip.mva.sp.comm.exception.SpException;
import egovframework.com.mip.mva.sp.comm.service.DirectService;
import egovframework.com.mip.mva.sp.comm.util.Base64Util;
import egovframework.com.mip.mva.sp.comm.vo.M200VO;
import egovframework.com.mip.mva.sp.comm.vo.T530VO;
import egovframework.com.mip.mva.sp.config.ConfigBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.app2app.service.impl
 * @FileName App2AppService.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 8.
 * @Description App to App 인터페이스 검증 처리 ServiceImpl
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
@Service("app2AppService")
public class App2AppServiceImpl implements App2AppService {

	private static final Logger LOGGER = LoggerFactory.getLogger(App2AppServiceImpl.class);

	/** Direct 검증 Service */
	private final DirectService directService;

	/**
	 * 생성자
	 * 
	 * @param directService Direct 검증 Service
	 */
	public App2AppServiceImpl(DirectService directService) {
		this.directService = directService;
	}

	/**
	 * App to App 시작
	 * 
	 * @MethodName start
	 * @param t530 App to App 정보
	 * @return App to App 정보 + Base64로 인코딩된 M200 메시지
	 * @throws SpException
	 */
	@Override
	public T530VO start(T530VO t530) throws SpException {
		LOGGER.debug("t530 : {}", ConfigBean.gson.toJson(t530));

		String mode = t530.getMode();
		String svcCode = t530.getSvcCode();

		M200VO m200 = directService.getM200(mode, svcCode, true);

		t530.setM200Base64(Base64Util.encode(ConfigBean.gson.toJson(m200)));

		return t530;
	}

}
