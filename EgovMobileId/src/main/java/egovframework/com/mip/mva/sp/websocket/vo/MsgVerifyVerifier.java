package egovframework.com.mip.mva.sp.websocket.vo;

import egovframework.com.mip.mva.sp.config.ConfigBean;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.websocket.vo
 * @FileName MsgVerifyVerifier.java
 * @Author Min Gi Ju
 * @Date 2022. 5. 31.
 * @Description verify_verifier 메세지 VO
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
public class MsgVerifyVerifier {

	private String msg = ConfigBean.VERIFY_VERIFIER;
	private String trxcode;
	private String didAssertion;

	/**
	 * 생성자
	 * 
	 * @param trxcode 거래코드
	 * @param didAssertion DID Assertion
	 */
	public MsgVerifyVerifier(String trxcode, String didAssertion) {
		this.trxcode = trxcode;
		this.didAssertion = didAssertion;
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

	public String getDidAssertion() {
		return didAssertion;
	}

	public void setDidAssertion(String didAssertion) {
		this.didAssertion = didAssertion;
	}

}
