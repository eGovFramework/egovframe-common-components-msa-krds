package egovframework.com.mip.mva.sp.config.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.comm.vo
 * @FileName DidWalletFileVO.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 3.
 * @Description DID 파일 설정 VO
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
public class DidWalletFileVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Wallet 파일 경로 */
	private String keymanagerPath;
	/** Wallet 파일 비밀번호 */
	private String keymanagerPassword;
	/** 서명 키아이디 */
	private String signKeyId;
	/** 암호화 키아이디 */
	private String encryptKeyId;
	/** DID Document 파일 경로 */
	private String didFilePath;

	public String getKeymanagerPath() {
		return keymanagerPath;
	}

	public void setKeymanagerPath(String keymanagerPath) {
		this.keymanagerPath = keymanagerPath;
	}

	public String getKeymanagerPassword() {
		return keymanagerPassword;
	}

	public void setKeymanagerPassword(String keymanagerPassword) {
		this.keymanagerPassword = keymanagerPassword;
	}

	public String getSignKeyId() {
		return signKeyId;
	}

	public void setSignKeyId(String signKeyId) {
		this.signKeyId = signKeyId;
	}

	public String getEncryptKeyId() {
		return encryptKeyId;
	}

	public void setEncryptKeyId(String encryptKeyId) {
		this.encryptKeyId = encryptKeyId;
	}

	public String getDidFilePath() {
		return didFilePath;
	}

	public void setDidFilePath(String didFilePath) {
		this.didFilePath = didFilePath;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
	}

}
