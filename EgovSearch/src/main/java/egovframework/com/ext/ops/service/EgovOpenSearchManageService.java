package egovframework.com.ext.ops.service;

import java.io.IOException;

public interface EgovOpenSearchManageService {

	void createTextIndex() throws IOException;

	void createVectorIndex() throws IOException;

	void insertTotalData();

	void insertTotalVectorData();

	void deleteIndex(String indexName) throws IOException;

	void reprocessFailedSync(String syncSttusCode);

}
