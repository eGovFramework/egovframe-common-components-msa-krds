package egovframework.com.mip.mva.sp.websocket.proc.cpm;

import egovframework.com.mip.mva.sp.comm.vo.WsInfoVO;
import org.eclipse.jetty.websocket.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.websocket.proc.cpm
 * @FileName CpmDefaultProc.java
 * @Author Min Gi Ju
 * @Date 2022. 5. 31.
 * @Description Default 메세지 처리 Class
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
public class CpmDefaultProc {

	private static final Logger LOGGER = LoggerFactory.getLogger(CpmDefaultProc.class);

	/**
	 * default 메세지 처리
	 * 
	 * @MethodName procDefault
	 * @param message 메세지
	 * @param session Websocket 세션
	 * @param wsInfo Websocket 정보
	 */
	public void procDefault(String message, Session session, WsInfoVO wsInfo) {
		// default 처리를 위해 있는 class 로서 현재로서는 아무것도 하지 않고 들어온 메세지는 discard 된다.
		LOGGER.debug("message : {}", message);
	}

}
