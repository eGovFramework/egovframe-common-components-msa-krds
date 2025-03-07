package egovframework.com.mip.mva.sp.config.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.comm.vo
 * @FileName BlockchainVO.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 3.
 * @Description 블록체인 설정 VO
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
public class BlockchainVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 블록체인 계정 */
	private String account;
	/** 블록체인 서버 도메인 */
	private String serverDomain;
	/** Connect Timeout */
	private Integer connectTimeout;
	/** Read Timeout */
	private Integer readTimeout;
	/** 캐시 사용여부 */
	private Boolean useCache;
	/** SDK 상세로그여부 */
	private Boolean sdkDetailLog;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getServerDomain() {
		return serverDomain;
	}

	public void setServerDomain(String serverDomain) {
		this.serverDomain = serverDomain;
	}

	public Integer getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(Integer connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public Integer getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(Integer readTimeout) {
		this.readTimeout = readTimeout;
	}

	public Boolean getUseCache() {
		return useCache;
	}

	public void setUseCache(Boolean useCache) {
		this.useCache = useCache;
	}

	public Boolean getSdkDetailLog() {
		return sdkDetailLog;
	}

	public void setSdkDetailLog(Boolean sdkDetailLog) {
		this.sdkDetailLog = sdkDetailLog;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
	}

}
