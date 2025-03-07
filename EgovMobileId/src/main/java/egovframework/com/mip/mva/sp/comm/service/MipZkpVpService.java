package egovframework.com.mip.mva.sp.comm.service;

import egovframework.com.mip.mva.sp.comm.exception.SpException;
import egovframework.com.mip.mva.sp.comm.vo.TrxInfoVO;
import egovframework.com.mip.mva.sp.comm.vo.VP;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.comm.service
 * @FileName MipZkpVpService.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 3.
 * @Description 영지식 VP 검증 Service
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
public interface MipZkpVpService {

	/**
	 * Profile 요청
	 * 
	 * @MethodName getProfile
	 * @param trxInfo 거래정보
	 * @return Base64로 인코딩된 Profile
	 * @throws SpException
	 */
	public String getProfile(TrxInfoVO trxInfo) throws SpException;

	/**
	 * VP 검증
	 * 
	 * @MethodName verifyVP
	 * @param trxInfo 거래정보
	 * @param vp VP 정보
	 * @return 검증 결과
	 * @throws SpException
	 */
	public Boolean verifyVP(TrxInfoVO trxInfo, VP vp) throws SpException;

	/**
	 * VP data 조회
	 * 
	 * @MethodName getVPData
	 * @param vp VP
	 * @throws SpException
	 */
	public String getVPData(VP vp) throws SpException;

}
