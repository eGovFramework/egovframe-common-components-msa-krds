package egovframework.com.mip.mva.sp.comm.enums;

import org.springframework.util.ObjectUtils;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.comm.enums
 * @FileName AuthTypeEnum.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 7.
 * @Description 인증유형 Enum
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
public enum AuthTypeEnum {

	PIN("pin"), // Pin 번호 인증
	BIO("bio"), // 생체 인증
	FACE("face") // Face ID 인증
	;

	/** 인증유형 값 */
	private String val;

	/**
	 * 생성자
	 * 
	 * @param val 인증유형 값
	 */
	AuthTypeEnum(String val) {
		this.val = val;
	}

	public String getVal() {
		return val;
	}

	/**
	 * Enum 조회
	 * 
	 * @param val Enum Value
	 * @return AuthTypeEnum
	 */
	public static AuthTypeEnum getEnum(String val) {
		if (ObjectUtils.isEmpty(val)) {
			return null;
		}

		for (AuthTypeEnum item : AuthTypeEnum.values()) {
			if (item.getVal().equals(val.toLowerCase())) {
				return item;
			}
		}

		return null;
	}

}
