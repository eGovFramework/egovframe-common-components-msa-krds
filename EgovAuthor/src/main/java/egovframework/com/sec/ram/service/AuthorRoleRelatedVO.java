package egovframework.com.sec.ram.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorRoleRelatedVO extends EgovDefaultVO implements Serializable {

    private static final long serialVersionUID = -2298301051215012776L;

    private String authorCode;

    private String roleCode;

    private LocalDateTime creatDt;

}
