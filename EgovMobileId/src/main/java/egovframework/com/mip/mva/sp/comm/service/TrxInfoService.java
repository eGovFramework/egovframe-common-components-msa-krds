package egovframework.com.mip.mva.sp.comm.service;

import egovframework.com.mip.mva.sp.comm.exception.SpException;
import egovframework.com.mip.mva.sp.comm.vo.TrxInfoVO;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.comm.service
 * @FileName TrxInfoService.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 3.
 * @Description 거래정보 Service
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
public interface TrxInfoService {

	/**
	 * 거래정보 조회
	 * 
	 * @MethodName getTrxInfo
	 * @param trxcode 거래코드
	 * @return 거래정보
	 * @throws SpException
	 */
	public TrxInfoVO getTrxInfo(String trxcode) throws SpException;

	/**
	 * 거래정보 등록
	 * 
	 * @MethodName registTrxInfo
	 * @param trxInfo 거래정보
	 * @throws SpException
	 */
	public void registTrxInfo(TrxInfoVO trxInfo) throws SpException;

	/**
	 * 거래정보 수정
	 * 
	 * @MethodName modifyTrxInfo
	 * @param trxInfo 거래정보
	 * @throws SpException
	 */
	public void modifyTrxInfo(TrxInfoVO trxInfo) throws SpException;

	/**
	 * 거래정보 삭제
	 * 
	 * @MethodName removeTrxInfo
	 * @param trxcode 거래코드
	 * @throws SpException
	 */
	public void removeTrxInfo(String trxcode) throws SpException;

}
