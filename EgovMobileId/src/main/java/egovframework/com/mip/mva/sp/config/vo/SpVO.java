package egovframework.com.mip.mva.sp.config.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.comm.vo
 * @FileName SpVO.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 3.
 * @Description SP 설정 VO
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
public class SpVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** SP 서버주소 */
	private String serverDomain;
	/** SP BI 이미지 URL */
	private String biImageUrl;
	/** SP BI Data(Base64 인코딩) */
	private String biImageBase64;
	/** CI 제공 여부 */
	private Boolean isCi;
	/** 전화번호 제공 여부 */
	private Boolean isTelno;
	/** 필수 Privacy 확인 여부 */
	private Boolean checkRequiredPrivacy;
	/** 만료일자 확인 여부 */
	private Boolean checkVcExpirationDate;

	public String getServerDomain() {
		return serverDomain;
	}

	public void setServerDomain(String serverDomain) {
		this.serverDomain = serverDomain;
	}

	public String getBiImageUrl() {
		return biImageUrl;
	}

	public void setBiImageUrl(String biImageUrl) {
		this.biImageUrl = biImageUrl;
	}

	public String getBiImageBase64() {
		return biImageBase64;
	}

	public void setBiImageBase64(String biImageBase64) {
		this.biImageBase64 = biImageBase64;
	}

	public Boolean getIsCi() {
		return isCi;
	}

	public void setIsCi(Boolean isCi) {
		this.isCi = isCi;
	}

	public Boolean getIsTelno() {
		return isTelno;
	}

	public void setIsTelno(Boolean isTelno) {
		this.isTelno = isTelno;
	}

	public Boolean getCheckRequiredPrivacy() {
		return checkRequiredPrivacy;
	}

	public void setCheckRequiredPrivacy(Boolean checkRequiredPrivacy) {
		this.checkRequiredPrivacy = checkRequiredPrivacy;
	}

	public Boolean getCheckVcExpirationDate() {
		return checkVcExpirationDate;
	}

	public void setCheckVcExpirationDate(Boolean checkVcExpirationDate) {
		this.checkVcExpirationDate = checkVcExpirationDate;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
	}

}
