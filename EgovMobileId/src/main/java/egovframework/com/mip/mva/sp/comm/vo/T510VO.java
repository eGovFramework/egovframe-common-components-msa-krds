package egovframework.com.mip.mva.sp.comm.vo;

import java.io.Serializable;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.comm.vo
 * @FileName T510VO.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 3.
 * @Description QR-MPM 시작용 VO
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
public class T510VO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Command */
	private String cmd;
	/** 모드 */
	private String mode;
	/** 서비스코드 */
	private String svcCode;
	/** 앱 */
	private String appCode;

	/** Base64로 인코딩된 M200 메세지 */
	private String m200Base64;

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getSvcCode() {
		return svcCode;
	}

	public void setSvcCode(String svcCode) {
		this.svcCode = svcCode;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getM200Base64() {
		return m200Base64;
	}

	public void setM200Base64(String m200Base64) {
		this.m200Base64 = m200Base64;
	}

}
