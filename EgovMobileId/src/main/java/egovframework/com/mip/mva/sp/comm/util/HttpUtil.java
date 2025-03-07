package egovframework.com.mip.mva.sp.comm.util;

import egovframework.com.mip.mva.sp.comm.enums.MipErrorEnum;
import egovframework.com.mip.mva.sp.comm.exception.SpException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.comm.util
 * @FileName HttpUtil.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 3.
 * @Description Http Call Util
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
public class HttpUtil {

	/**
	 * Http Call(POST) 실행
	 * 
	 * @MethodName executeHttpPost
	 * @param url URL
	 * @param param 파라미터
	 * @return 결과
	 * @throws SpException
	 */
	public static String executeHttpPost(String url, Object param) throws SpException {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = null;

		try {
			response = restTemplate.postForEntity(url, param, String.class);
		} catch (RestClientException e) {
			throw new SpException(MipErrorEnum.SP_NETWORK_ERROR);
		}

		return response.getBody();
	}

}
