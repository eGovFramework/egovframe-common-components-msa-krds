package egovframework.com.mip.mva.sp.comm.vo;

import com.raonsecure.omnione.core.data.iw.Unprotected;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 모바일 국가보훈등록증에 담긴 정보들의 VO
 * @author 표준프레임워크 박성완
 * @since 20202.13
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
public class LicenseHonorVO implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private String name;              // 이름
    private String ihidnum;           // 주민등록번호
    private String address;           // 주소
    private String birth;             // 생년월일
    private String dlphotoimage;      // 사진이미지
    
    private String title;             // 신분증명
    private String reptitle;          // 대표 타이틀
    private String rephonno;          // 대표 보훈번호
    private String reptarget;         // 대표 보훈대상
    private String qualiflist;        // 경합 보훈대상 목록
    private String nolist;            // 보훈번호 및 보훈대상 목록
    private String issude;            // 발행일
    private String qualifnm;          // 보훈대상명
    private String honoreeno;         // 보훈번호
    private String target;            // 대상
    private String honrelation;       // 유공자와의 관계
    private String registde;          // 등록일
    private String disabledgrd;       // 상이 및 장애 등급
    private String regno;             // 등록번호
    private String ci;				  // 연계 정보

    public LicenseHonorVO() {
    }

    /**
	 * Privacy 목록과 License VO를 매핑
	 * 
	 * @MethodName mapFromList
	 * @param privacy Privacy목록
	 * @return LicenseHonorVO
	 */
    public LicenseHonorVO mapFromList(List<Unprotected> privacy) {
        LicenseHonorVO vo = new LicenseHonorVO();

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
        vo.setTitle(map.get("title"));
        vo.setReptitle(map.get("reptitle"));
        vo.setRephonno(map.get("rephonno"));
        vo.setReptarget(map.get("reptarget"));
        vo.setQualiflist(map.get("qualiflist"));
        vo.setNolist(map.get("nolist"));
        vo.setIssude(map.get("issude"));
        vo.setQualifnm(map.get("qualifnm"));
        vo.setHonoreeno(map.get("honoreeno"));
        vo.setTarget(map.get("target"));
        vo.setHonrelation(map.get("honrelation"));
        vo.setRegistde(map.get("registde"));
        vo.setDisabledgrd(map.get("disabledgrd"));
        vo.setRegno(map.get("regno"));
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

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getReptitle() { return reptitle; }
    public void setReptitle(String reptitle) { this.reptitle = reptitle; }

    public String getRephonno() { return rephonno; }
    public void setRephonno(String rephonno) { this.rephonno = rephonno; }

    public String getReptarget() { return reptarget; }
    public void setReptarget(String reptarget) { this.reptarget = reptarget; }

    public String getQualiflist() { return qualiflist; }
    public void setQualiflist(String qualiflist) { this.qualiflist = qualiflist; }

    public String getNolist() { return nolist; }
    public void setNolist(String nolist) { this.nolist = nolist; }

    public String getIssude() { return issude; }
    public void setIssude(String issude) { this.issude = issude; }

    public String getQualifnm() { return qualifnm; }
    public void setQualifnm(String qualifnm) { this.qualifnm = qualifnm; }

    public String getHonoreeno() { return honoreeno; }
    public void setHonoreeno(String honoreeno) { this.honoreeno = honoreeno; }

    public String getTarget() { return target; }
    public void setTarget(String target) { this.target = target; }

    public String getHonrelation() { return honrelation; }
    public void setHonrelation(String honrelation) { this.honrelation = honrelation; }

    public String getRegistde() { return registde; }
    public void setRegistde(String registde) { this.registde = registde; }

    public String getDisabledgrd() { return disabledgrd; }
    public void setDisabledgrd(String disabledgrd) { this.disabledgrd = disabledgrd; }

    public String getRegno() { return regno; }
    public void setRegno(String regno) { this.regno = regno; }
    
    public String getCi() { return ci; }
    public void setCi(String ci) { this.ci = ci; }
}
