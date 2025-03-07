package egovframework.com.mip.mva.sp.comm.vo;

import com.raonsecure.omnione.core.data.iw.Unprotected;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 모바일 재외국민 신원확인증에 담긴 정보들의 VO
 * @author 표준프레임워크 박성완
 * @since 2025.02.13
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
public class LicenseExpatVO implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private String name;              // 이름
    private String ihidnum;           // 주민등록번호
    private String address;           // 주소
    private String birth;             // 생년월일
    private String dlphotoimage;      // 사진이미지
    
    private String expatno;           // 재외국민번호
    private String expatnosn;         // 재외국민번호 일련번호
    private String rephonno;          // 대표 보훈번호
    private String title;             // 타이틀
    private String usrname;           // 영문 성
    private String givennames;        // 영문 이름
    private String passporttype;      // 종류
    private String countrycode;       // 발행국
    private String passportno;        // 여권번호
    private String nationality;       // 국적
    private String issude;            // 발급일
    private String expirdate;         // 만료일
    private String sex;               // 성별
    private String bearersigna;       // 여권소지인의서명
    private String authority;         // 발행관청
    private String emblnm;            // 관할공관
    private String ci;                // 연계정보

    public LicenseExpatVO() {
    }

    /**
	 * Privacy 목록과 License VO를 매핑
	 * 
	 * @MethodName mapFromList
	 * @param privacy Privacy목록
	 * @return LicenseExpatVO
	 */
    public LicenseExpatVO mapFromList(List<Unprotected> privacy) {
        LicenseExpatVO vo = new LicenseExpatVO();

        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < privacy.size(); i++) {
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
        vo.setExpatno(map.get("expatno"));
        vo.setExpatnosn(map.get("expatnosn"));
        vo.setRephonno(map.get("rephonno"));
        vo.setTitle(map.get("title"));
        vo.setUsrname(map.get("usrname"));
        vo.setGivennames(map.get("givennames"));
        vo.setPassporttype(map.get("passporttype"));
        vo.setCountrycode(map.get("countrycode"));
        vo.setPassportno(map.get("passportno"));
        vo.setNationality(map.get("nationality"));
        vo.setIssude(map.get("issude"));
        vo.setExpirdate(map.get("expirdate"));
        vo.setSex(map.get("sex"));
        vo.setBearersigna(map.get("bearersigna"));
        vo.setAuthority(map.get("authority"));
        vo.setEmblnm(map.get("emblnm"));
        vo.setCi(map.get("ci"));

        return vo;
    }

    // Getter & Setter methods
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

    public String getExpatno() { return expatno; }
    public void setExpatno(String expatno) { this.expatno = expatno; }

    public String getExpatnosn() { return expatnosn; }
    public void setExpatnosn(String expatnosn) { this.expatnosn = expatnosn; }

    public String getRephonno() { return rephonno; }
    public void setRephonno(String rephonno) { this.rephonno = rephonno; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getUsrname() { return usrname; }
    public void setUsrname(String usrname) { this.usrname = usrname; }

    public String getGivennames() { return givennames; }
    public void setGivennames(String givennames) { this.givennames = givennames; }

    public String getPassporttype() { return passporttype; }
    public void setPassporttype(String passporttype) { this.passporttype = passporttype; }

    public String getCountrycode() { return countrycode; }
    public void setCountrycode(String countrycode) { this.countrycode = countrycode; }

    public String getPassportno() { return passportno; }
    public void setPassportno(String passportno) { this.passportno = passportno; }

    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }

    public String getIssude() { return issude; }
    public void setIssude(String issude) { this.issude = issude; }

    public String getExpirdate() { return expirdate; }
    public void setExpirdate(String expirdate) { this.expirdate = expirdate; }

    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }

    public String getBearersigna() { return bearersigna; }
    public void setBearersigna(String bearersigna) { this.bearersigna = bearersigna; }

    public String getAuthority() { return authority; }
    public void setAuthority(String authority) { this.authority = authority; }

    public String getEmblnm() { return emblnm; }
    public void setEmblnm(String emblnm) { this.emblnm = emblnm; }

    public String getCi() { return ci; }
    public void setCi(String ci) { this.ci = ci; }
}
