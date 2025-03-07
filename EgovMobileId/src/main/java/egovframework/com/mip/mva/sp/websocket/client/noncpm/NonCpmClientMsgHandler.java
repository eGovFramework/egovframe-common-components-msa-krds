package egovframework.com.mip.mva.sp.websocket.client.noncpm;

import com.google.gson.Gson;
import egovframework.com.mip.mva.sp.comm.exception.SpException;
import egovframework.com.mip.mva.sp.comm.vo.WsInfoVO;
import egovframework.com.mip.mva.sp.websocket.vo.MsgStartNonCpm;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.websocket.client.noncpm
 * @FileName NonCpmClientMsgHandler.java
 * @Author Min Gi Ju
 * @Date 2022. 5. 31.
 * @Description Non CPM 클라이언트 메세지 핸들러 Class
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
@WebSocket(maxTextMessageSize = 3145728)
public class NonCpmClientMsgHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(NonCpmClientMsgHandler.class);

	private final CountDownLatch closeLatch;
	private final WsInfoVO wsInfo;

	private Session session;

	/**
	 * 생성자
	 * 
	 * @param wsInfo 웹소켓 정보
	 */
	public NonCpmClientMsgHandler(WsInfoVO wsInfo) {
		this.closeLatch = new CountDownLatch(1);
		this.wsInfo = wsInfo;
	}

	/**
	 * 웹소켓 종료 정보 설정
	 * 
	 * @MethodName awaitClose
	 * @param duration 기간
	 * @param unit 단위
	 * @return
	 * @throws InterruptedException
	 */
	public Boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException {
		return this.closeLatch.await(duration, unit);
	}

	/**
	 * 웹소켓 종료
	 * 
	 * @MethodName onClose
	 * @param statusCode 상태코드
	 * @param reason 사유
	 */
	@OnWebSocketClose
	public void onClose(int statusCode, String reason) {
		LOGGER.debug("connection closed: {} - {}", statusCode, reason);

		this.session = null;
		this.closeLatch.countDown();
	}

	/**
	 * 웹소켓 연결
	 * 
	 * @MethodName onConnect
	 * @param session Websocket 세션
	 */
	@OnWebSocketConnect
	public void onConnect(Session session) {
		LOGGER.debug("connected: {}", session);

		this.session = session;

		try {
			MsgStartNonCpm msgStartNonCpm = new MsgStartNonCpm(true);

			String jsonString = new Gson().toJson(msgStartNonCpm);

			LOGGER.debug("send: {}", jsonString);

			session.getRemote().sendString(jsonString);
		} catch (Throwable t) {
			LOGGER.error(t.getMessage(), t);
		}
	}

	/**
	 * 메세지 수신
	 * 
	 * @MethodName onMessage
	 * @param msg 메세지
	 */
	@OnWebSocketMessage
	public void onMessage(String msg) {
		LOGGER.debug("received: {}", msg);

		if ((session != null) && (session.isOpen())) {
			egovframework.com.mip.mva.sp.websocket.client.noncpm.NonCpmBranch nonCpmBranch = new egovframework.com.mip.mva.sp.websocket.client.noncpm.NonCpmBranch();

			try {
				nonCpmBranch.packetChoose(msg, session, wsInfo);
			} catch (SpException e) {
				LOGGER.error("시스템 IO 에러", e);
			}
		}
	}

}
