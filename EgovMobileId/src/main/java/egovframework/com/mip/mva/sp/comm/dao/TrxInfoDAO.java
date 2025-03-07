package egovframework.com.mip.mva.sp.comm.dao;

import egovframework.com.mip.mva.sp.comm.exception.SpException;
import egovframework.com.mip.mva.sp.comm.vo.TrxInfoVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.comm.dao
 * @FileName TrxInfoDAO.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 7.
 * @Description 거래정보 DAO
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
@Mapper
public interface TrxInfoDAO {

	/**
	 * 거래정보 조회
	 * 
	 * @MethodName selectTrxInfo
	 * @param trxcode 거래코드
	 * @return 거래정보
	 * @throws SpException
	 */
	public TrxInfoVO selectTrxInfo(String trxcode) throws SpException;

	/**
	 * 거래정보 등록
	 * 
	 * @MethodName insertTrxInfo
	 * @param trxInfo 거래정보
	 * @throws SpException
	 */
	public void insertTrxInfo(TrxInfoVO trxInfo) throws SpException;

	/**
	 * 거래정보 수정
	 * 
	 * @MethodName updateTrxInfo
	 * @param trxInfo 거래정보
	 * @throws SpException
	 */
	public void updateTrxInfo(TrxInfoVO trxInfo) throws SpException;

	/**
	 * 거래정보 삭제
	 * 
	 * @MethodName deleteTrxInfo
	 * @param trxcode 거래코드
	 * @throws SpException
	 */
	public void deleteTrxInfo(String trxcode) throws SpException;

}
