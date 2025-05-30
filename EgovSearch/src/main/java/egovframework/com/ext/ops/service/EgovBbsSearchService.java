package egovframework.com.ext.ops.service;

import org.springframework.data.domain.Page;

public interface EgovBbsSearchService {

	Page<BoardVO> textSearch(BoardVO boardVO);

	Page<BoardVectorVO> vectorSearch(BoardVO boardVO);

}
