package egovframework.com.mip.mva.sp.config.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.comm.vo
 * @FileName VerifyConfigVO.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 3.
 * @Description 검증 설정 VO
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
public class VerifyConfigVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 블록체인 설정 */
	private egovframework.com.mip.mva.sp.config.vo.BlockchainVO blockchain;
	/** DID 파일 설정 */
	private egovframework.com.mip.mva.sp.config.vo.DidWalletFileVO didWalletFile;
	/** SP 설정 */
	private egovframework.com.mip.mva.sp.config.vo.SpVO sp;
	/** 서비스 설정 */
	private LinkedHashMap<String, egovframework.com.mip.mva.sp.config.vo.ServiceVO> services;
	/** 중계서버 설정 */
	private egovframework.com.mip.mva.sp.config.vo.ProxyVO proxy;
	/** 푸시 설정 */
	private egovframework.com.mip.mva.sp.config.vo.PushVO push;
	/** DB 설정 */
	private egovframework.com.mip.mva.sp.config.vo.DbVO db;

	/** 서비스 목록 */
	private List<egovframework.com.mip.mva.sp.config.vo.ServiceVO> serviceList;
	/** CA 목록 */
	private List<egovframework.com.mip.mva.sp.config.vo.CaVO> caList;

	public egovframework.com.mip.mva.sp.config.vo.BlockchainVO getBlockchain() {
		return blockchain;
	}

	public void setBlockchain(egovframework.com.mip.mva.sp.config.vo.BlockchainVO blockchain) {
		this.blockchain = blockchain;
	}

	public egovframework.com.mip.mva.sp.config.vo.DidWalletFileVO getDidWalletFile() {
		return didWalletFile;
	}

	public void setDidWalletFile(egovframework.com.mip.mva.sp.config.vo.DidWalletFileVO didWalletFile) {
		this.didWalletFile = didWalletFile;
	}

	public egovframework.com.mip.mva.sp.config.vo.SpVO getSp() {
		return sp;
	}

	public void setSp(egovframework.com.mip.mva.sp.config.vo.SpVO sp) {
		this.sp = sp;
	}

	public LinkedHashMap<String, egovframework.com.mip.mva.sp.config.vo.ServiceVO> getServices() {
		return services;
	}

	public void setServices(LinkedHashMap<String, egovframework.com.mip.mva.sp.config.vo.ServiceVO> services) {
		this.services = services;
	}

	public egovframework.com.mip.mva.sp.config.vo.ProxyVO getProxy() {
		return proxy;
	}

	public void setProxy(egovframework.com.mip.mva.sp.config.vo.ProxyVO proxy) {
		this.proxy = proxy;
	}

	public egovframework.com.mip.mva.sp.config.vo.PushVO getPush() {
		return push;
	}

	public void setPush(egovframework.com.mip.mva.sp.config.vo.PushVO push) {
		this.push = push;
	}

	public List<egovframework.com.mip.mva.sp.config.vo.ServiceVO> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<egovframework.com.mip.mva.sp.config.vo.ServiceVO> serviceList) {
		this.serviceList = serviceList;
	}

	public List<egovframework.com.mip.mva.sp.config.vo.CaVO> getCaList() {
		return caList;
	}

	public void setCaList(List<egovframework.com.mip.mva.sp.config.vo.CaVO> caList) {
		this.caList = caList;
	}

	public egovframework.com.mip.mva.sp.config.vo.DbVO getDb() {
		return db;
	}

	public void setDb(egovframework.com.mip.mva.sp.config.vo.DbVO db) {
		this.db = db;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
	}

}
