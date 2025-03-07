package egovframework.com.mip.mva.sp.comm.vo;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.comm.vo
 * @FileName PushInfoVO.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 3.
 * @Description 푸시 요청 VO
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
public class PushInfoVO {

	/** MS-CODE(조폐공사에 등록하여 할당 받음) */
	private String mscode;
	/** 푸시유형 */
	private String pushType;
	/** 성명 */
	private String name;
	/** 전화번호 */
	private String telno;
	/** Base64로 인코딩된 M200메세지 */
	private String data;

	/** 푸시키 */
	private Integer key;
	/** 결과 */
	private Boolean result;
	/** 결과메세지 */
	private String resultMsg;
	/** 오류코드 */
	private Integer errcode;
	/** 오류메세지 */
	private String errmsg;

	public String getMscode() {
		return mscode;
	}

	public void setMscode(String mscode) {
		this.mscode = mscode;
	}

	public String getPushType() {
		return pushType;
	}

	public void setPushType(String pushType) {
		this.pushType = pushType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelno() {
		return telno;
	}

	public void setTelno(String telno) {
		this.telno = telno;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public Integer getErrcode() {
		return errcode;
	}

	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

}
