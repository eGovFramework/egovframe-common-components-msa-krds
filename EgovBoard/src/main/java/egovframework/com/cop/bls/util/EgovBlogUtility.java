package egovframework.com.cop.bls.util;

import egovframework.com.cop.bls.entity.*;
import egovframework.com.cop.bls.service.BbsMasterOptnVO;
import egovframework.com.cop.bls.service.BbsMasterVO;
import egovframework.com.cop.bls.service.BlogUserVO;
import egovframework.com.cop.bls.service.BlogVO;
import org.springframework.beans.BeanUtils;

public class EgovBlogUtility {

    public static BlogVO blogEntityToVO(Blog blog) {
        BlogVO blogVO = new BlogVO();
        BeanUtils.copyProperties(blog, blogVO);
        return blogVO;
    }

    public static Blog blogVOToEntity(BlogVO blogVO) {
        Blog blog = new Blog();
        BeanUtils.copyProperties(blogVO, blog);
        return blog;
    }

    public static BlogUser blogUserVOToEntity(BlogUserVO blogUserVO) {
        BlogUserId blogUserId = new BlogUserId();
        blogUserId.setBlogId(blogUserVO.getBlogId());
        blogUserId.setEmplyrId(blogUserVO.getEmplyrId());

        BlogUser blogUser = new BlogUser();
        blogUser.setBlogUserId(blogUserId);
        blogUser.setMngrAt(blogUserVO.getMngrAt());
        blogUser.setMberSttus(blogUserVO.getMberSttus());
        blogUser.setSbscrbDe(blogUserVO.getSbscrbDe());
        blogUser.setSecsnDe(blogUserVO.getSecsnDe());
        blogUser.setUseAt(blogUserVO.getUseAt());
        blogUser.setFrstRegistPnttm(blogUserVO.getFrstRegistPnttm());
        blogUser.setFrstRegisterId(blogUserVO.getFrstRegisterId());
        blogUser.setLastUpdtPnttm(blogUserVO.getLastUpdtPnttm());
        blogUser.setLastUpdusrId(blogUserVO.getLastUpdusrId());
        return blogUser;
    }

    public static BbsMasterVO bbsMasterEntityTOVO(BbsMaster bbsMaster) {
        BbsMasterVO bbsMasterVO = new BbsMasterVO();
        BeanUtils.copyProperties(bbsMaster, bbsMasterVO);
        return bbsMasterVO;
    }

    public static BbsMaster bbsMasterVOTOEntity(BbsMasterVO bbsMasterVO) {
        BbsMaster bbsMaster = new BbsMaster();
        BeanUtils.copyProperties(bbsMasterVO, bbsMaster);
        return bbsMaster;
    }

    public static BbsMasterOptn bbsMasterOptnVOEntity(BbsMasterOptnVO bbsMasterOptnVO) {
        BbsMasterOptn bbsMaster = new BbsMasterOptn();
        BeanUtils.copyProperties(bbsMasterOptnVO, bbsMaster);
        return bbsMaster;
    }

}
