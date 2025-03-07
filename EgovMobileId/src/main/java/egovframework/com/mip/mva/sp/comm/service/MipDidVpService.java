package egovframework.com.mip.mva.sp.comm.service;

import com.raonsecure.omnione.core.data.iw.Unprotected;
import egovframework.com.mip.mva.sp.comm.exception.SpException;
import egovframework.com.mip.mva.sp.comm.vo.TrxInfoVO;
import egovframework.com.mip.mva.sp.comm.vo.VP;

import java.util.List;
import java.util.Map;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.comm.service
 * @FileName MipDidVpService.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 3.
 * @Description VP 검증 Service
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * 2025.02. 13.    박성완           mapPrivacy 메서드 생성
 * </pre>
 */
public interface MipDidVpService {

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
	 * @return 검증 성공 여부
	 * @throws SpException
	 */
	public Boolean verifyVP(TrxInfoVO trxInfo, VP vp) throws SpException;

	/**
	 * VP 재검증(부인방지)
	 * 
	 * @MethodName reVerifyVP
	 * @param vp VP 정보
	 * @return 재검증 성공 여부
	 * @throws SpException
	 */
	public Boolean reVerifyVP(VP vp) throws SpException;

	/**
	 * VP data 조회
	 * 
	 * @MethodName getVPData
	 * @param vp VP
	 * @return 복호화된 VP data
	 * @throws SpException
	 */
	public String getVPData(VP vp) throws SpException;

	/**
	 * Privacy 조회
	 * 
	 * @MethodName getPrivacy
	 * @param trxcode 거래코드
	 * @return Privacy 목록
	 * @throws SpException
	 */
	public List<Unprotected> getPrivacy(String trxcode) throws SpException;
	
	/**
	 * Privacy를 LicenseVO로 매핑
	 * 
	 * @MethodName mapPrivacy
	 * @param license 라이선스명
	 * @param privacy Privacy 목록
	 * @return LicenseVO로 매핑된 privacy 목록
	 * @throws SpException
	 */
	public Map<String, Object> mapPrivacy(String license, List<Unprotected> privacy) throws SpException;

	/**
	 * 이미지 변환(Hex String to byte Array)
	 * 
	 * @MethodName transImageHexToByte
	 * @param imageData String
	 * @return 변환된 이미지 데이터
	 * @throws SpException
	 */
	public byte[] transImageHexToByte(String imageData) throws SpException;

}
