package egovframework.com.mip.mva.sp.comm.vo;

import java.io.Serializable;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.comm.vo
 * @FileName T520VO.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 3.
 * @Description QR-CPM 시작용 VO
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
public class T520VO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Command */
	private String cmd;
	/** 서비스코드 */
	private String svcCode;

	/** Base64로 인코딩된 M120 메세지 */
	private String m120Base64;

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getSvcCode() {
		return svcCode;
	}

	public void setSvcCode(String svcCode) {
		this.svcCode = svcCode;
	}

	public String getM120Base64() {
		return m120Base64;
	}

	public void setM120Base64(String m120Base64) {
		this.m120Base64 = m120Base64;
	}

}
