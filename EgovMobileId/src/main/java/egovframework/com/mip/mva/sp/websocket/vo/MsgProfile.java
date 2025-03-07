package egovframework.com.mip.mva.sp.websocket.vo;

import egovframework.com.mip.mva.sp.config.ConfigBean;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.websocket.vo
 * @FileName MsgProfile.java
 * @Author Min Gi Ju
 * @Date 2022. 5. 31.
 * @Description profile 메세지 VO
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
public class MsgProfile {

	private String msg = ConfigBean.PROFILE;
	private String trxcode;
	private String profile;
	private String image;
	private Boolean ci;
	private Boolean telno;

	/**
	 * 생성자
	 * 
	 * @param trxcode 거래코드
	 * @param profile Base64로 인코딩된 프로파일
	 * @param image Base64로 인코딩된 이미지
	 * @param ci CI 제출여부
	 * @param ci 전화번호 제출여부
	 */
	public MsgProfile(String trxcode, String profile, String image, Boolean ci, Boolean telno) {
		this.trxcode = trxcode;
		this.profile = profile;
		this.image = image;
		this.ci = ci;
		this.telno = telno;
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

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Boolean getCi() {
		return ci;
	}

	public void setCi(Boolean ci) {
		this.ci = ci;
	}

	public Boolean getTelno() {
		return telno;
	}

	public void setTelno(Boolean telno) {
		this.telno = telno;
	}

}
