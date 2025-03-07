package egovframework.com.mip.mva.sp.qrcpm.service;

import egovframework.com.mip.mva.sp.comm.exception.SpException;
import egovframework.com.mip.mva.sp.comm.vo.T520VO;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.qrcpm.service
 * @FileName QrcpmService.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 3.
 * @Description QR-CPM 인터페이스 검증 처리 Service
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
public interface QrcpmService {

	/**
	 * QR-CPM 시작
	 * 
	 * @MethodName start
	 * @param t520 QR-CPM 정보
	 * @return QR-CPM 정보
	 * @throws SpException
	 */
	public T520VO start(T520VO t520) throws SpException;

}
