package egovframework.com.mip.mva.sp.web.service;

import egovframework.com.mip.mva.sp.comm.exception.SpException;
import egovframework.com.mip.mva.sp.comm.vo.T510VO;
import egovframework.com.mip.mva.sp.comm.vo.T530VO;
import egovframework.com.mip.mva.sp.comm.vo.T540VO;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.push.service
 * @FileName PushQrService.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 3.
 * @Description 푸시 인터페이스 검증 처리 Service
 * 
 *              <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 *              </pre>
 */
public interface WebService {

	/**
	 * 푸시 시작
	 * 
	 * @MethodName pushStart
	 * @param t540 푸시 정보
	 * @return 푸시 정보 + Base64로 인코딩된 M200 메시지
	 * @throws SpException
	 */
	public T540VO pushStart(T540VO t540) throws SpException;

	/**
	 * QR-MPM 시작
	 * 
	 * @MethodName qrMpmStart
	 * @param t510 QR-MPM 정보
	 * @return QR-MPM 정보 + Base64로 인코딩된 M200 메시지
	 * @throws SpException
	 */
	public T510VO qrMpmStart(T510VO t510) throws SpException;

	/**
	 * App to App 시작
	 * 
	 * @MethodName appToAppStart
	 * @param t530 App to App 정보
	 * @return App to App 정보 + Base64로 인코딩된 M200 메시지
	 * @throws SpException
	 */
	public T530VO appToAppStart(T530VO t530) throws SpException;

	/**
	 * 인증 완료
	 * 
	 * @MethodName crtfcCmpl
	 * @param trxcode 거래코드
	 * @throws SpException
	 */
	public void crtfcCmpl(String trxcode) throws SpException;

	/**
	 * RSA 암호화
	 * 
	 * @MethodName rsaEncrypt
	 * @param data      평문 데이터
	 * @param targetDid 복호화 대상 DID
	 * @return 암호화 데이터
	 */
	public String rsaEncrypt(String data, String targetDid) throws SpException;

	/**
	 * RSA 복호화
	 * 
	 * @MethodName rsaDecrypt
	 * @param data 암호화 데이터
	 * @return 복호화 데이터
	 */
	public String rsaDecrypt(String data) throws SpException;

}
