package egovframework.com.mip.mva.sp.comm.exception;

import egovframework.com.mip.mva.sp.comm.enums.MipErrorEnum;
import egovframework.com.mip.mva.sp.comm.util.Base64Util;
import egovframework.com.mip.mva.sp.comm.vo.M900VO;
import egovframework.com.mip.mva.sp.comm.vo.MipApiDataVO;
import egovframework.com.mip.mva.sp.config.ConfigBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.comm.exception
 * @FileName SpExceptionHandler.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 7.
 * @Description SP Exception Controller Advice
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
@RestControllerAdvice
public class SpExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpExceptionHandler.class);

	/**
	 * SP Excpetion 핸들러
	 * 
	 * @MethodName handleSpException
	 * @param e SP Exception
	 * @return
	 */
	@ExceptionHandler(SpException.class)
	public MipApiDataVO handleSpException(SpException e) {
		MipApiDataVO mipApiData = new MipApiDataVO();

		M900VO m900 = new M900VO();

		m900.setType(ConfigBean.TYPE);
		m900.setVersion(ConfigBean.VERSION);
		m900.setCmd(ConfigBean.M900);
		m900.setErrcode(e.getErrcode());
		m900.setErrmsg(e.getErrmsg());
		m900.setTrxcode(e.getTrxcode());

		mipApiData.setResult(false);
		mipApiData.setData(Base64Util.encode(ConfigBean.gson.toJson(m900)));

		LOGGER.error("code[{}], msg[{}], trxcode[{}]", e.getErrcode(), e.getErrmsg(), e.getTrxcode(), e);

		return mipApiData;
	}

	/**
	 * Excpetion 핸들러
	 * 
	 * @MethodName handleException
	 * @param e Excpetion
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public MipApiDataVO handleException(Exception e) {
		MipApiDataVO mipApiData = new MipApiDataVO();

		M900VO m900 = new M900VO();

		m900.setType(ConfigBean.TYPE);
		m900.setVersion(ConfigBean.VERSION);
		m900.setCmd(ConfigBean.M900);
		m900.setErrcode(MipErrorEnum.UNKNOWN_ERROR.getCode());
		m900.setErrmsg(MipErrorEnum.UNKNOWN_ERROR.getMsg());

		mipApiData.setResult(false);
		mipApiData.setData(Base64Util.encode(ConfigBean.gson.toJson(m900)));

		LOGGER.error("{} : {}", MipErrorEnum.UNKNOWN_ERROR.getCode(), e.getMessage(), e);

		return mipApiData;
	}

}
