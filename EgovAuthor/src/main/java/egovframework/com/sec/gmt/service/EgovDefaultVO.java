package egovframework.com.sec.gmt.service;

import lombok.Data;

import java.io.Serializable;

@Data
public class EgovDefaultVO implements Serializable {

    private static final long serialVersionUID = 6248022100050109324L;

    /** 검색Keyword */
    private String searchCondition = "";

    /** 검색Keyword */
    private String searchKeyword = "";

    /** 검색사용여부 */
    private String searchUseYn = "";

    /** 현재페이지 */
    private int pageIndex = 1;

    /** 페이지개수 */
    private int pageUnit = 10;

    /** 페이지사이즈 */
    private int pageSize = 10;

    /** firstIndex */
    private int firstIndex = 1;

    /** lastIndex */
    private int lastIndex = 1;

    /** recordCountPerPage */
    private int recordCountPerPage = 10;

}
