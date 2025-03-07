package egovframework.com.mip.mva.sp.websocket.vo;

import egovframework.com.mip.mva.sp.config.ConfigBean;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.websocket.vo
 * @FileName MsgStartNonCpm.java
 * @Author Min Gi Ju
 * @Date 2022. 5. 31.
 * @Description start_noncpm 메세지 VO
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
public class MsgStartNonCpm {

	private String msg = ConfigBean.START_NONCPM;
	private String type = ConfigBean.TYPE;
	private String version = ConfigBean.VERSION;
	private Boolean proxyprofile;

	/**
	 * 생성자
	 * 
	 * @param proxyprofile Profile 제출여부
	 */
	public MsgStartNonCpm(Boolean proxyprofile) {
		this.proxyprofile = proxyprofile;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Boolean getProxyprofile() {
		return proxyprofile;
	}

	public void setProxyprofile(Boolean proxyprofile) {
		this.proxyprofile = proxyprofile;
	}

}
