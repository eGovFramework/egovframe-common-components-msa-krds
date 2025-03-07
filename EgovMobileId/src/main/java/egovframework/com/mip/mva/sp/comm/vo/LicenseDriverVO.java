package egovframework.com.mip.mva.sp.comm.vo;

import com.raonsecure.omnione.core.data.iw.Unprotected;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 모바일 운전면허증에 담긴 정보들의 VO
 * @author 표준프레임워크 박성완
 * @since 2025.02.13.
 * @version 4.3
 * <pre>
 *
 * ==================================================
 * DATE            AUTHOR          NOTE
 * ==================================================
 * 2025.02.13,     박성완           표준프레임워크 v4.3 - 최초생성
 *
 * </pre>
 */
public class LicenseDriverVO implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private String name;              // 이름
    private String ihidnum;           // 주민등록번호
    private String address;           // 주소
    private String birth;             // 생년월일
    private String dlphotoimage;      // 사진이미지
    
    private String dlno;              // 운전면허증번호
    private String asort;             // 면허종별
    private String inspctbegend;      // 적성검사시작종료일자
    private String issude;            // 발급일자
    private String locpanm;           // 지방경찰청명의
    private String passwordsn;        // 암호일련번호
    private String inorgdonnyn;       // 장기기증여부
    private String lcnscndcdnm;       // 면허조건명
    private String engnm;             // 영문이름
    private String engsex;            // 성별
    private String engbirth;          // 영문생년월일
    private String engaddr;           // 영문주소
    private String ci;				  // 연계 정보

    public LicenseDriverVO() {
    	
    }

    /**
	 * Privacy 목록과 License VO를 매핑
	 * 
	 * @MethodName mapFromList
	 * @param privacy Privacy목록
	 * @return LicenseDriverVO
	 */
    public LicenseDriverVO mapFromList(List<Unprotected> privacy) {
    	LicenseDriverVO vo = new LicenseDriverVO();
    	
    	Map<String, String> map = new HashMap<>();
		for(int i=0; i<privacy.size(); i++) {
			map.put(
				privacy.get(i).getType(),
				privacy.get(i).getValue().equals("") ? null : privacy.get(i).getValue()
			);
		}
		
		vo.setName(map.get("name"));
		vo.setIhidnum(map.get("ihidnum"));
		vo.setDlphotoimage(map.get("dlphotoimage"));
		vo.setAddress(map.get("address"));
		vo.setBirth(map.get("birth"));
		vo.setDlno(map.get("dlno"));
		vo.setAsort(map.get("asort"));
		vo.setInspctbegend(map.get("inspctbegend"));
		vo.setIssude(map.get("issude"));
		vo.setLocpanm(map.get("locpanm"));
		vo.setPasswordsn(map.get("passwordsn"));
		vo.setInorgdonnyn(map.get("inorgdonnyn"));
		vo.setLcnscndcdnm(map.get("lcnscndcdnm"));
		vo.setEngnm(map.get("engnm"));
		vo.setEngsex(map.get("engsex"));
		vo.setEngbirth(map.get("engbirth"));
		vo.setEngaddr(map.get("engaddr"));
		vo.setCi(map.get("ci"));
        
        return vo;
    }

    // Getter & Setter
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIhidnum() { return ihidnum; }
    public void setIhidnum(String ihidnum) { this.ihidnum = ihidnum; }

    public String getDlphotoimage() { return dlphotoimage; }
    public void setDlphotoimage(String dlphotoimage) { this.dlphotoimage = dlphotoimage; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getBirth() { return birth; }
    public void setBirth(String birth) { this.birth = birth; }

    public String getDlno() { return dlno; }
    public void setDlno(String dlno) { this.dlno = dlno; }

    public String getAsort() { return asort; }
    public void setAsort(String asort) { this.asort = asort; }

    public String getInspctbegend() { return inspctbegend; }
    public void setInspctbegend(String inspctbegend) { this.inspctbegend = inspctbegend; }

    public String getIssude() { return issude; }
    public void setIssude(String issude) { this.issude = issude; }

    public String getLocpanm() { return locpanm; }
    public void setLocpanm(String locpanm) { this.locpanm = locpanm; }

    public String getPasswordsn() { return passwordsn; }
    public void setPasswordsn(String passwordsn) { this.passwordsn = passwordsn; }

    public String getInorgdonnyn() { return inorgdonnyn; }
    public void setInorgdonnyn(String inorgdonnyn) { this.inorgdonnyn = inorgdonnyn; }

    public String getLcnscndcdnm() { return lcnscndcdnm; }
    public void setLcnscndcdnm(String lcnscndcdnm) { this.lcnscndcdnm = lcnscndcdnm; }

    public String getEngnm() { return engnm; }
    public void setEngnm(String engnm) { this.engnm = engnm; }

    public String getEngsex() { return engsex; }
    public void setEngsex(String engsex) { this.engsex = engsex; }

    public String getEngbirth() { return engbirth; }
    public void setEngbirth(String engbirth) { this.engbirth = engbirth; }

    public String getEngaddr() { return engaddr; }
    public void setEngaddr(String engaddr) { this.engaddr = engaddr; }
    
    public String getCi() { return ci; }
    public void setCi(String ci) { this.ci = ci; }
}
