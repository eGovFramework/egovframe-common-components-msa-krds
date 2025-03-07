package egovframework.com.mip.mva.sp.websocket.proc.noncpm;

import egovframework.com.mip.mva.sp.comm.enums.ModeEnum;
import egovframework.com.mip.mva.sp.comm.enums.ProxyErrorEnum;
import egovframework.com.mip.mva.sp.comm.enums.TrxStsCodeEnum;
import egovframework.com.mip.mva.sp.comm.exception.SpException;
import egovframework.com.mip.mva.sp.comm.service.ProxyService;
import egovframework.com.mip.mva.sp.comm.service.TrxInfoService;
import egovframework.com.mip.mva.sp.comm.util.SpringUtil;
import egovframework.com.mip.mva.sp.comm.vo.TrxInfoVO;
import egovframework.com.mip.mva.sp.comm.vo.WsInfoVO;
import egovframework.com.mip.mva.sp.config.ConfigBean;
import egovframework.com.mip.mva.sp.websocket.vo.MsgError;
import egovframework.com.mip.mva.sp.websocket.vo.MsgProfile;
import egovframework.com.mip.mva.sp.websocket.vo.MsgWaitProfile;
import org.eclipse.jetty.websocket.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.io.IOException;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.websocket.proc.noncpm
 * @FileName NonCpmProfile.java
 * @Author Min Gi Ju
 * @Date 2022. 5. 31.
 * @Description Non CPM Profile 메세지 처리 Class
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
public class NonCpmProfile {

	private static final Logger LOGGER = LoggerFactory.getLogger(NonCpmProfile.class);

	/**
	 * wait_profile 메세지 처리
	 * 
	 * @MethodName procWaitProfile
	 * @param message 메세지
	 * @param session Websocket 세션
	 * @param wsInfo Websocket 정보
	 */
	public void procWaitProfile(String message, Session session, WsInfoVO wsInfo) throws SpException {
		LOGGER.debug("message : {}", message);

		String trxcode = "";
		String sendMsg = "";

		try {
			ConfigBean configBean = (ConfigBean) SpringUtil.getBean(ConfigBean.class);
			MsgWaitProfile msgWaitProfile = ConfigBean.gson.fromJson(message, MsgWaitProfile.class);

			trxcode = msgWaitProfile.getTrxcode();

			if (ObjectUtils.isEmpty(trxcode)) {
				throw new SpException(ProxyErrorEnum.MISSING_MANDATORY_ITEM, null, "trxcode");
			} else {
				if (!trxcode.equals(wsInfo.getTrxcode())) {
					throw new SpException(ProxyErrorEnum.TRXCODE_NOT_FOUND, trxcode);
				}
			}

			String profile = this.getProfile(wsInfo);
			String image = configBean.getVerifyConfig().getSp().getBiImageBase64();
			Boolean ci = configBean.getVerifyConfig().getSp().getIsCi();
			Boolean telno = configBean.getVerifyConfig().getSp().getIsTelno();

			// Non-CPM에서는 ci, image 설정하지 않고 profile만 설정
			MsgProfile msgProfile = new MsgProfile(trxcode, profile, image, ci, telno);

			sendMsg = ConfigBean.gson.toJson(msgProfile);
		} catch (SpException e) {
			LOGGER.error(e.getMessage(), e);

			MsgError msgError = new MsgError(wsInfo.getTrxcode(), e.getErrcode(), e.getErrmsg());

			sendMsg = ConfigBean.gson.toJson(msgError);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);

			MsgError msgError = new MsgError(wsInfo.getTrxcode(), ProxyErrorEnum.UNKNOWN_ERROR.getCode(), ProxyErrorEnum.UNKNOWN_ERROR.getMsg());

			sendMsg = ConfigBean.gson.toJson(msgError);
		}

		LOGGER.debug("sendMsg : {}", sendMsg);

		try {
			session.getRemote().sendString(sendMsg);

			wsInfo.setResult(sendMsg);
			wsInfo.setStatus(ConfigBean.WAIT_PROFILE);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);

			throw new SpException(ProxyErrorEnum.UNKNOWN_ERROR, trxcode, "sendString");
		}
	}

	/**
	 * Profile 생성 요청
	 * 
	 * @MethodName getProfile
	 * @param wsInfo Websocket 정보
	 * @return String Profile 정보
	 * @throws Exception
	 */
	private String getProfile(WsInfoVO wsInfo) throws Exception {
		ProxyService proxyService = (ProxyService) SpringUtil.getBean(ProxyService.class);
		TrxInfoService trxInfoService = (TrxInfoService) SpringUtil.getBean(TrxInfoService.class);

		// 중계서버로 받은 trxcode 를 insert 한다.
		TrxInfoVO trxInfo = new TrxInfoVO();

		trxInfo.setTrxcode(wsInfo.getTrxcode());
		trxInfo.setSvcCode(wsInfo.getSvcCode());
		trxInfo.setMode(ModeEnum.PROXY.getVal());
		trxInfo.setTrxStsCode(TrxStsCodeEnum.SERCIVE_REQ.getVal());

		// 거래코드 등록
		trxInfoService.registTrxInfo(trxInfo);

		return proxyService.getProfile(wsInfo.getTrxcode());
	}

}
