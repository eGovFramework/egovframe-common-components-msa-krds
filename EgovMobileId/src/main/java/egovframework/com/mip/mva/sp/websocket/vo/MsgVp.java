package egovframework.com.mip.mva.sp.websocket.vo;

import egovframework.com.mip.mva.sp.comm.vo.VP;
import egovframework.com.mip.mva.sp.config.ConfigBean;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.websocket.vo
 * @FileName MsgVp.java
 * @Author Min Gi Ju
 * @Date 2022. 5. 31.
 * @Description vp 메세지 VO
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
public class MsgVp {

	private String msg = ConfigBean.VP;
	private String request;
	private String trxcode;
	private VP vp;

	/**
	 * 생성자
	 * 
	 * @param request 요청구분
	 * @param trxcode 거래코드
	 * @param vp VP 정보
	 */
	public MsgVp(String request, String trxcode, VP vp) {
		this.request = request;
		this.trxcode = trxcode;
		this.vp = vp;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getTrxcode() {
		return trxcode;
	}

	public void setTrxcode(String trxcode) {
		this.trxcode = trxcode;
	}

	public VP getVp() {
		return vp;
	}

	public void setVp(VP vp) {
		this.vp = vp;
	}

}
