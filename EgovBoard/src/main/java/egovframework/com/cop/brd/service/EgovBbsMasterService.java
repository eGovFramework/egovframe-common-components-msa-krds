package egovframework.com.cop.brd.service;

public interface EgovBbsMasterService {

    BbsMasterDTO detail(BbsMasterVO bbsMasterVO);

    BbsMasterOptnVO selectBBSMasterOptn(String bbsId);

}
