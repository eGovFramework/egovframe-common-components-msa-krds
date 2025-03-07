package egovframework.com.mip.mva.sp.comm.vo;

import java.io.Serializable;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.comm.vo
 * @FileName WsInfoVO.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 3.
 * @Description 웹소켓 정보 VO
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
public class WsInfoVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 연결 URL */
	private String connUrl;
	/** 연결 Timeout 시간 */
	private Integer timeout;
	/** 거래코드 */
	private String trxcode;
	/** 서비스코드 */
	private String svcCode;
	/** 메세지 */
	private String result;
	/** 상태 */
	private String status;

	public String getConnUrl() {
		return connUrl;
	}

	public void setConnUrl(String connUrl) {
		this.connUrl = connUrl;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public String getTrxcode() {
		return trxcode;
	}

	public void setTrxcode(String trxcode) {
		this.trxcode = trxcode;
	}

	public String getSvcCode() {
		return svcCode;
	}

	public void setSvcCode(String svcCode) {
		this.svcCode = svcCode;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
