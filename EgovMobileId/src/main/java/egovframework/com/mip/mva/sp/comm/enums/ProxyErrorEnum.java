package egovframework.com.mip.mva.sp.comm.enums;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.comm.enums
 * @FileName ProxyErrorEnum.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 7.
 * @Description Proxy 오류 Enum
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
public enum ProxyErrorEnum {

	PACKET_ERROR(1001, "packet error"), //
	MISSING_MANDATORY_ITEM(1002, "missing mandatory item"), //
	INVALID_DATA(1010, "invalid data"), //
	INVALID_MSG(1011, "invalid msg"), //
	UNSUPPORTED_MESSAGE_VERSION(1013, "unsupported message version"), //
	TRXCODE_NOT_FOUND(1014, "trxcode not found"), //
	DID_AUTH_FAILED(1031, "DID auth failed"), //
	SEQUENCE_ERROR(2001, "sequence error"), //
	TIMEOUT_ERROR(2002, "timeout error"), //
	UNEXPECTED_DISCONNECTION(2003, "unexpected disconnection"), //
	MISMATCHING_NONCE(2004, "mismatching nonce"), //
	MISMATCHING_AUTH_TYPE(2005, "mismatching auth type"), //
	CLOSE_REQUESTED_BY_HOLDER(9001, "Close requested by holder"), //
	CLOSE_REQUESTED_BY_VERIFIER(9002, "Close requested by verifier"), //
	UNKNOWN_ERROR(9999, "unknown error") //
	;

	/** Proxy 오류코드 */
	private Integer code;
	/** Proxy 오류메세지 */
	private String msg;
	/** Proxy 오류사유 */
	private String reason;

	/**
	 * 생성자
	 * 
	 * @param code 오류코드
	 * @param msg 오류메세지
	 */
	ProxyErrorEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * Enum 조회
	 * 
	 * @MethodName  getEnum
	 * @param code Enum Code
	 * @return ProxyErrorEnum
	 */
	public static ProxyErrorEnum getEnum(Integer code) {
		for (ProxyErrorEnum item : ProxyErrorEnum.values()) {
			if (item.getCode() == code) {
				return item;
			}
		}

		return null;
	}

}
