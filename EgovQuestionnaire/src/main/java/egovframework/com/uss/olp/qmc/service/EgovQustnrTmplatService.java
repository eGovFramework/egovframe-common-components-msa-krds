package egovframework.com.uss.olp.qmc.service;

import java.util.List;

public interface EgovQustnrTmplatService {

    List<QustnrTmplatVO> list();

    byte[] getImage(String qustnrTmplatId);

}
