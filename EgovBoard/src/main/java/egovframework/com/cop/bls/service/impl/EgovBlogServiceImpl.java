package egovframework.com.cop.bls.service.impl;

import egovframework.com.cop.bls.entity.Blog;
import egovframework.com.cop.bls.repository.EgovBlogRepository;
import egovframework.com.cop.bls.repository.EgovBlogUserRepository;
import egovframework.com.cop.bls.service.BlogDTO;
import egovframework.com.cop.bls.service.BlogUserVO;
import egovframework.com.cop.bls.service.BlogVO;
import egovframework.com.cop.bls.service.EgovBlogService;
import egovframework.com.cop.bls.util.EgovBlogUtility;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Service("blsEgovBlogServiceImpl")
public class EgovBlogServiceImpl extends EgovAbstractServiceImpl implements EgovBlogService {

    private final EgovBlogRepository repository;
    private final EgovBlogUserRepository userRepository;
    private final EgovIdGnrService idgenService;

    public EgovBlogServiceImpl(
            EgovBlogRepository repository,
            EgovBlogUserRepository userRepository,
            @Qualifier("egovBlogIdGnrService") EgovIdGnrService idgenService
    ) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.idgenService = idgenService;
    }

    @Override
    public Page<BlogDTO> list(BlogVO blogVO) {
        Pageable pageable = PageRequest.of(blogVO.getFirstIndex(), blogVO.getRecordCountPerPage());
        String searchCondition = blogVO.getSearchCondition();
        String searchKeyword = blogVO.getSearchKeyword();
        return repository.blodList(searchCondition, searchKeyword, pageable);
    }

    @Override
    public BlogDTO detail(BlogVO blogVO) {
        return repository.blogDetail(blogVO.getBlogId());
    }

    @Transactional
    @Override
    public BlogVO insert(BlogVO blogVO, Map<String, String> userInfo) throws FdlException {
        String blogId = idgenService.getNextStringId();

        blogVO.setBlogId(blogId);
        blogVO.setRegistSeCode("REGC02");
        blogVO.setFrstRegistPnttm(LocalDateTime.now());
        blogVO.setFrstRegisterId(userInfo.get("uniqId"));
        blogVO.setLastUpdtPnttm(LocalDateTime.now());
        blogVO.setLastUpdusrId(userInfo.get("uniqId"));
        Blog blog = repository.save(EgovBlogUtility.blogVOToEntity(blogVO));

        BlogUserVO blogUserVO = getBlogUserVO(blogId, userInfo.get("uniqId"));
        userRepository.save(EgovBlogUtility.blogUserVOToEntity(blogUserVO));

        return EgovBlogUtility.blogEntityToVO(blog);
    }

    @Transactional
    @Override
    public BlogVO update(BlogVO blogVO, Map<String, String> userInfo) {
        return repository.findById(blogVO.getBlogId())
                .map(result -> {
                    result.setBlogNm(blogVO.getBlogNm());
                    result.setBlogIntrcn(blogVO.getBlogIntrcn());
                    result.setLastUpdtPnttm(LocalDateTime.now());
                    result.setLastUpdusrId(userInfo.get("uniqId"));
                    return repository.save(result);
                })
                .map(EgovBlogUtility::blogEntityToVO).orElse(null);
    }

    private BlogUserVO getBlogUserVO(String blogId, String uniqId) {
        BlogUserVO blogUserVO = new BlogUserVO();
        blogUserVO.setBlogId(blogId);
        blogUserVO.setEmplyrId(uniqId);
        blogUserVO.setMngrAt("Y");
        blogUserVO.setMberSttus("P");
        blogUserVO.setSbscrbDe(LocalDateTime.now());
        blogUserVO.setUseAt("Y");
        blogUserVO.setFrstRegistPnttm(LocalDateTime.now());
        blogUserVO.setFrstRegisterId(uniqId);
        blogUserVO.setLastUpdtPnttm(LocalDateTime.now());
        blogUserVO.setLastUpdusrId(uniqId);
        return blogUserVO;
    }

}
