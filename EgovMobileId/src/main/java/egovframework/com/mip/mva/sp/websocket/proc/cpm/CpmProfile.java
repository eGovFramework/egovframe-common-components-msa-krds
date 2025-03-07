package egovframework.com.mip.mva.sp.websocket.proc.cpm;

import egovframework.com.mip.mva.sp.comm.enums.ProxyErrorEnum;
import egovframework.com.mip.mva.sp.comm.exception.SpException;
import egovframework.com.mip.mva.sp.comm.service.ProxyService;
import egovframework.com.mip.mva.sp.comm.util.SpringUtil;
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
 * @PackageName mip.mva.sp.websocket.proc.cpm
 * @FileName CpmProfile.java
 * @Author Min Gi Ju
 * @Date 2022. 5. 31.
 * @Description CPM Profile 메세지 처리 Class
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
public class CpmProfile {

	private static final Logger LOGGER = LoggerFactory.getLogger(CpmProfile.class);

	/**
	 * wait_profile 메세지 처리
	 * 
	 * @MethodName procWaitProfile
	 * @param message 메세지
	 * @param session Websocket 세션
	 * @param wsInfo Websocket 정보
	 * @throws SpException
	 */
	public void procWaitProfile(String message, Session session, WsInfoVO wsInfo) throws SpException {
		LOGGER.debug("message : {}", message);

		String trxcode = null;
		String sendMsg = null;

		try {
			MsgWaitProfile msgWaitProfile = ConfigBean.gson.fromJson(message, MsgWaitProfile.class);

			trxcode = msgWaitProfile.getTrxcode();

			if (ObjectUtils.isEmpty(trxcode)) {
				throw new SpException(ProxyErrorEnum.MISSING_MANDATORY_ITEM, null, "trxcode");
			} else {
				if (!trxcode.equals(wsInfo.getTrxcode())) {
					throw new SpException(ProxyErrorEnum.TRXCODE_NOT_FOUND, trxcode);
				}
			}

			ConfigBean configBean = (ConfigBean) SpringUtil.getBean(ConfigBean.class);

			ProxyService proxyService = (ProxyService) SpringUtil.getBean(ProxyService.class);

			String profile = proxyService.getProfile(trxcode);
			String image = configBean.getVerifyConfig().getSp().getBiImageBase64();
			Boolean ci = configBean.getVerifyConfig().getSp().getIsCi();
			Boolean telno = configBean.getVerifyConfig().getSp().getIsTelno();

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

}
