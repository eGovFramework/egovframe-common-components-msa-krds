package egovframework.com.mip.mva.sp.config.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.comm.vo
 * @FileName ProxyVO.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 3.
 * @Description 중계서버 설정 VO
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
public class ProxyVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 중계서버주소 */
	private String proxyServer;
	/** 중계서버 Connection Timeout */
	private Integer proxyConnTimeOut;

	public String getProxyServer() {
		return proxyServer;
	}

	public void setProxyServer(String proxyServer) {
		this.proxyServer = proxyServer;
	}

	public Integer getProxyConnTimeOut() {
		return proxyConnTimeOut;
	}

	public void setProxyConnTimeOut(Integer proxyConnTimeOut) {
		this.proxyConnTimeOut = proxyConnTimeOut;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
	}

}
