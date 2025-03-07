package egovframework.com.mip.mva.sp.websocket.proc.cpm;

import egovframework.com.mip.mva.sp.comm.enums.ProxyErrorEnum;
import egovframework.com.mip.mva.sp.comm.exception.SpException;
import egovframework.com.mip.mva.sp.comm.service.ProxyService;
import egovframework.com.mip.mva.sp.comm.util.SpringUtil;
import egovframework.com.mip.mva.sp.comm.vo.VP;
import egovframework.com.mip.mva.sp.comm.vo.WsInfoVO;
import egovframework.com.mip.mva.sp.config.ConfigBean;
import egovframework.com.mip.mva.sp.websocket.vo.MsgError;
import egovframework.com.mip.mva.sp.websocket.vo.MsgFinish;
import egovframework.com.mip.mva.sp.websocket.vo.MsgVp;
import org.eclipse.jetty.websocket.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.io.IOException;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.websocket.proc.cpm
 * @FileName CpmVp.java
 * @Author Min Gi Ju
 * @Date 2022. 5. 31.
 * @Description CPM 검증 메세지 처리 Class
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
public class CpmVp {

	private static final Logger LOGGER = LoggerFactory.getLogger(CpmVp.class);

	/**
	 * vp 메세지 처리
	 * 
	 * @MethodName procVp
	 * @param message 메세지
	 * @param session Websocket 세션
	 * @param wsInfo vp 정보
	 */
	public void procVp(String message, Session session, WsInfoVO wsInfo) throws SpException {
		LOGGER.debug("message : {}", message);

		String trxcode = null;
		String sendMsg = null;

		try {
			MsgVp msgVp = ConfigBean.gson.fromJson(message, MsgVp.class);

			trxcode = wsInfo.getTrxcode();

			if (ObjectUtils.isEmpty(trxcode)) {
				throw new SpException(ProxyErrorEnum.MISSING_MANDATORY_ITEM, null, "trxcode");
			} else {
				if (!trxcode.equals(msgVp.getTrxcode())) {
					throw new SpException(ProxyErrorEnum.TRXCODE_NOT_FOUND, trxcode);
				}
			}

			VP vp = msgVp.getVp();

			if (ObjectUtils.isEmpty(vp)) {
				throw new SpException(ProxyErrorEnum.MISSING_MANDATORY_ITEM, trxcode, "vp");
			}

			ProxyService proxyService = (ProxyService) SpringUtil.getBean(ProxyService.class);

			proxyService.verifyVP(trxcode, vp);

			MsgFinish msgFinish = new MsgFinish(trxcode);

			sendMsg = ConfigBean.gson.toJson(msgFinish);
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
			session.close();

			wsInfo.setResult(sendMsg);
			wsInfo.setStatus(ConfigBean.VP);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);

			throw new SpException(ProxyErrorEnum.UNKNOWN_ERROR, trxcode, "sendString");
		}
	}

}
