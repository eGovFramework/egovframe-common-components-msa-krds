package egovframework.com.mip.mva.sp.websocket.vo;

import egovframework.com.mip.mva.sp.config.ConfigBean;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.websocket.vo
 * @FileName MsgWaitVerify.java
 * @Author Min Gi Ju
 * @Date 2022. 5. 31.
 * @Description wait_verify 메세지 VO
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
public class MsgWaitVerify {

	private String msg = ConfigBean.WAIT_VERIFY;
	private String trxcode;
	private String nonce;

	/**
	 * 생성자
	 * 
	 * @param trxcode 거래코드
	 * @param nonce Nonce
	 */
	public MsgWaitVerify(String trxcode, String nonce) {
		this.trxcode = trxcode;
		this.nonce = nonce;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTrxcode() {
		return trxcode;
	}

	public void setTrxcode(String trxcode) {
		this.trxcode = trxcode;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

}
