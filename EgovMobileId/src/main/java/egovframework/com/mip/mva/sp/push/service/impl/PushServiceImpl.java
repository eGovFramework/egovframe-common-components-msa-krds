package egovframework.com.mip.mva.sp.push.service.impl;

import egovframework.com.mip.mva.sp.comm.enums.MipErrorEnum;
import egovframework.com.mip.mva.sp.comm.exception.SpException;
import egovframework.com.mip.mva.sp.comm.service.DirectService;
import egovframework.com.mip.mva.sp.comm.util.Base64Util;
import egovframework.com.mip.mva.sp.comm.util.HttpUtil;
import egovframework.com.mip.mva.sp.comm.vo.M200VO;
import egovframework.com.mip.mva.sp.comm.vo.PushInfoVO;
import egovframework.com.mip.mva.sp.comm.vo.T540VO;
import egovframework.com.mip.mva.sp.config.ConfigBean;
import egovframework.com.mip.mva.sp.push.service.PushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.push.service.impl
 * @FileName PushServiceImpl.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 3.
 * @Description 푸시 인터페이스 검증 처리 ServiceImpl
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
@Service("pushService")
public class PushServiceImpl implements PushService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PushServiceImpl.class);

	/** 설정정보 */
	private final ConfigBean configBean;

	/** Direct 검증 Service */
	private final DirectService directService;

	/**
	 * 생성자
	 * 
	 * @param configBean 설정정보
	 * @param directService Direct 검증 Service
	 */
	public PushServiceImpl(ConfigBean configBean, DirectService directService) {
		this.configBean = configBean;
		this.directService = directService;
	}

	/**
	 * 푸시 시작
	 * 
	 * @MethodName start
	 * @param t540 푸시 정보
	 * @return 푸시 정보 + Base64로 인코딩된 M200 메시지
	 * @throws SpException
	 */
	@Override
	public T540VO start(T540VO t540) throws SpException {
		LOGGER.debug("t540 : {}", ConfigBean.gson.toJson(t540));

		String appCode = t540.getAppCode();
		String svcCode = t540.getSvcCode();
		String mode = t540.getMode();
		String name = t540.getName();
		String telno = t540.getTelno();

		try {
			String serverDomain = configBean.getVerifyConfig().getPush().getPushServer();
			String msCode = configBean.getVerifyConfig().getPush().getPushMsCode();
			String pushType = configBean.getVerifyConfig().getPush().getPushType();

			// M200 메시지 생성
			M200VO m200 = directService.getM200(mode, svcCode, false);

			String data = Base64Util.encode(ConfigBean.gson.toJson(m200));

			t540.setM200Base64(data);

			PushInfoVO pushInfo = new PushInfoVO();

			pushInfo.setMscode(msCode);
			pushInfo.setPushType(pushType);
			pushInfo.setName(name);
			pushInfo.setTelno(telno);
			pushInfo.setData(data);

			// 푸시 요청 원문 생성
			Map<String, Object> pushMap = new HashMap<String, Object>();

			// 삼성페이 설정
			if (!ObjectUtils.isEmpty(appCode) && !"100".equals(appCode)) {
				serverDomain = configBean.getVerifyConfig().getPush().getOpnPushServer();

				pushMap.put("apiType", ConfigBean.TYPE);
				pushMap.put("appCode", appCode);
			}

			pushMap.put("data", Base64Util.encode(ConfigBean.gson.toJson(pushInfo)));

			LOGGER.debug("pushMap : {}", ConfigBean.gson.toJson(pushMap));

			String pushResult = HttpUtil.executeHttpPost(serverDomain, ConfigBean.gson.toJson(pushMap));

			LOGGER.debug("pushResult : {}", pushResult);

			pushInfo = ConfigBean.gson.fromJson(pushResult, PushInfoVO.class);

			if (!pushInfo.getResult()) {
				throw new SpException(MipErrorEnum.UNKNOWN_ERROR, m200.getTrxcode(), pushInfo.getErrmsg());
			}
		} catch (SpException e) {
			throw e;
		} catch (Exception e) {
			throw new SpException(MipErrorEnum.UNKNOWN_ERROR, null, e.getMessage());
		}

		return t540;
	}

}
