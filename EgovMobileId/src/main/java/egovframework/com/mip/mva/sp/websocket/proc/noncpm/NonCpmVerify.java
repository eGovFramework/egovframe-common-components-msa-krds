package egovframework.com.mip.mva.sp.websocket.proc.noncpm;

import egovframework.com.mip.mva.sp.comm.enums.ProxyErrorEnum;
import egovframework.com.mip.mva.sp.comm.exception.SpException;
import egovframework.com.mip.mva.sp.comm.service.ProxyService;
import egovframework.com.mip.mva.sp.comm.util.Base64Util;
import egovframework.com.mip.mva.sp.comm.util.SpringUtil;
import egovframework.com.mip.mva.sp.comm.vo.WsInfoVO;
import egovframework.com.mip.mva.sp.config.ConfigBean;
import egovframework.com.mip.mva.sp.websocket.vo.MsgError;
import egovframework.com.mip.mva.sp.websocket.vo.MsgVerifyVerifier;
import egovframework.com.mip.mva.sp.websocket.vo.MsgWaitVerify;
import org.eclipse.jetty.websocket.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.io.IOException;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.websocket.proc.noncpm
 * @FileName NonCpmVerify.java
 * @Author Min Gi Ju
 * @Date 2022. 5. 31.
 * @Description Non CPM 검증자 검증 메세지 처리 Class
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
public class NonCpmVerify {

	private static final Logger LOGGER = LoggerFactory.getLogger(NonCpmVerify.class);

	/**
	 * wait_verify 메세지 처리
	 * 
	 * @MethodName procWaitVerify
	 * @param message 메세지
	 * @param session Websocket 세션
	 * @param wsInfo Websocket 정보
	 */
	public void procWaitVerify(String message, Session session, WsInfoVO wsInfo) throws SpException {
		LOGGER.debug("message : {}", message);

		String trxcode = "";
		String sendMsg = "";

		try {
			MsgWaitVerify msgWaitVerify = ConfigBean.gson.fromJson(message, MsgWaitVerify.class);

			trxcode = msgWaitVerify.getTrxcode();

			if (ObjectUtils.isEmpty(trxcode)) {
				throw new SpException(ProxyErrorEnum.MISSING_MANDATORY_ITEM, null, "trxcode");
			}

			String nonce = msgWaitVerify.getNonce();

			if (ObjectUtils.isEmpty(nonce)) {
				throw new SpException(ProxyErrorEnum.MISSING_MANDATORY_ITEM, trxcode, "nonce");
			}

			// wait_verify 메시지를 수신할 경우 이 메시지에서 처음으로 거래코드가 지정됨
			wsInfo.setTrxcode(trxcode);

			// DID Assertion 생성
			ProxyService proxyService = (ProxyService) SpringUtil.getBean(ProxyService.class);

			String didAssertion = Base64Util.encode(proxyService.makeDIDAssertion(nonce));

			// verify 응답 메시지 생성
			MsgVerifyVerifier msgVerifyVerifier = new MsgVerifyVerifier(trxcode, didAssertion);

			sendMsg = ConfigBean.gson.toJson(msgVerifyVerifier);
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
			wsInfo.setStatus(ConfigBean.WAIT_VERIFY);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);

			throw new SpException(ProxyErrorEnum.UNKNOWN_ERROR, trxcode, "sendString");
		}
	}

}
