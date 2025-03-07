package egovframework.com.mip.mva.sp.websocket.client.cpm;

import com.google.gson.Gson;
import egovframework.com.mip.mva.sp.comm.enums.ProxyErrorEnum;
import egovframework.com.mip.mva.sp.comm.exception.SpException;
import egovframework.com.mip.mva.sp.comm.vo.WsInfoVO;
import egovframework.com.mip.mva.sp.config.ConfigBean;
import egovframework.com.mip.mva.sp.websocket.proc.cpm.*;
import egovframework.com.mip.mva.sp.websocket.vo.MsgError;
import org.eclipse.jetty.websocket.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.websocket.client.cpm
 * @FileName CpmBranch.java
 * @Author Min Gi Ju
 * @Date 2022. 5. 31.
 * @Description CPM 메세지 분기 Class
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
@SuppressWarnings("unchecked")
public class CpmBranch {

	private static final Logger LOGGER = LoggerFactory.getLogger(CpmBranch.class);

	/**
	 * 메세지 분기 처리
	 * 
	 * <pre>
	 * 패킷 헤더를 검사하여 각 패킷 처리 메서드로 분기한다.
	 * 연결을 끊는 것은 중계서버가 담당한다.
	 * 오류 메시지 생성 위치와 연결 끊기에 대해서는 다음과 같이 정의한다.
	 * 
	 * | 오류 메시지 생성 위치 | 오류 전파                      | 연결 끊기 |
	 * |-----------------------|--------------------------------|-----------|
	 * | 신분증앱              | 신분증앱 => 중계서버 => SP서버 | 중계서버  |
	 * | SP서버                | SP서버 => 중계서버 => 신분증앱 | 중계서버  |
	 * | 중계서버              | 중계서버 => 신분증앱, SP서버   | 중계서버  |
	 * 
	 * 중계서버는 오류를 수신하거나 생성 시 신분증앱과 SP서버 양쪽으로 모두 전파 후 양쪽 연결을 모두 끊는다.
	 * 그러므로 SP서버는 오류 메시지 송수신 후 별도로 연결을 끊을 필요가 없다.
	 * </pre>
	 * 
	 * @MethodName packetChoose
	 * @param message 메세지
	 * @param session 웹소켓 세션
	 * @param wsInfo 웹소켓 정보
	 * @throws SpException
	 */
	public void packetChoose(String message, Session session, WsInfoVO wsInfo) throws SpException {
		LOGGER.debug("...............................packetChoose start..................................");
		LOGGER.debug("message: {}", message);

		Map<String, Object> messageMap = ConfigBean.gson.fromJson(message, HashMap.class);
		String msg = (String) messageMap.get("msg");

		if (msg == null) {
			// message에 msg 항목이 없는 경우 에러 메시지를 전송하고 종료
			String trxcode = wsInfo.getTrxcode();

			trxcode = (trxcode != null) ? trxcode : "";

			MsgError msgError = new MsgError(trxcode, ProxyErrorEnum.MISSING_MANDATORY_ITEM.getCode(), ProxyErrorEnum.MISSING_MANDATORY_ITEM.getMsg());

			String sendMsg = new Gson().toJson(msgError);

			LOGGER.error("Response Error Message: {}", sendMsg);

			try {
				session.getRemote().sendString(sendMsg);
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		} else {
			switch (msg) {
				// DID Auth 요청 처리
				case ConfigBean.WAIT_VERIFY:
					LOGGER.debug("...............................received wait_verify.................................");

					CpmVerify cpmVerify = new CpmVerify();

					cpmVerify.procWaitVerify(message, session, wsInfo);

					break;
				// 프로파일 처리
				case ConfigBean.WAIT_PROFILE:
					LOGGER.debug("...............................received wait_profile................................");

					CpmProfile cpmProfile = new CpmProfile();

					cpmProfile.procWaitProfile(message, session, wsInfo);

					break;
				// VP 검증 처리
				case ConfigBean.VP:
					LOGGER.debug("...............................received vp..........................................");

					CpmVp cpmVp = new CpmVp();

					cpmVp.procVp(message, session, wsInfo);

					break;
				// Error 처리
				case ConfigBean.ERROR:
					LOGGER.debug("...............................received error.......................................");

					CpmError cpmError = new CpmError();

					cpmError.procError(message, session, wsInfo);

					break;
				default:
					LOGGER.debug("...............................received others......................................");

					CpmDefaultProc cpmDefaultProc = new CpmDefaultProc();

					cpmDefaultProc.procDefault(message, session, wsInfo);

					break;
			}
		}
	}

}
