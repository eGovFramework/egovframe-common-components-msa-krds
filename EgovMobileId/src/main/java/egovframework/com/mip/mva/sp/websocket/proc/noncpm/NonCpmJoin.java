package egovframework.com.mip.mva.sp.websocket.proc.noncpm;

import com.google.gson.Gson;
import egovframework.com.mip.mva.sp.comm.enums.ProxyErrorEnum;
import egovframework.com.mip.mva.sp.comm.exception.SpException;
import egovframework.com.mip.mva.sp.comm.vo.WsInfoVO;
import egovframework.com.mip.mva.sp.config.ConfigBean;
import egovframework.com.mip.mva.sp.websocket.vo.MsgWaitJoin;
import org.eclipse.jetty.websocket.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.websocket.proc.noncpm
 * @FileName NonCpmJoin.java
 * @Author Min Gi Ju
 * @Date 2022. 5. 31.
 * @Description Non CPM Join 처리
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
public class NonCpmJoin {

	private static final Logger LOGGER = LoggerFactory.getLogger(NonCpmJoin.class);

	/**
	 * wait_join 메세지 처리
	 * 
	 * @MethodName procWaitJoin
	 * @param message 메세지
	 * @param session Websocket 세션
	 * @param wsInfo Websocket 정보
	 * @throws SpException
	 */
	public void procWaitJoin(String message, Session session, WsInfoVO wsInfo) throws SpException {
		LOGGER.debug("message : {}", message);

		try {
			MsgWaitJoin msgWaitJoin = new Gson().fromJson(message, MsgWaitJoin.class);

			String trxcode = wsInfo.getTrxcode();

			if (ObjectUtils.isEmpty(trxcode)) {

				throw new SpException(ProxyErrorEnum.MISSING_MANDATORY_ITEM, null, "trxcode");
			} else {
				if (!trxcode.equals(msgWaitJoin.getTrxcode())) {
					throw new SpException(ProxyErrorEnum.TRXCODE_NOT_FOUND, null, "trxcode");
				}
			}
		} catch (SpException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

		wsInfo.setStatus(ConfigBean.WAIT_JOIN);
	}

}
