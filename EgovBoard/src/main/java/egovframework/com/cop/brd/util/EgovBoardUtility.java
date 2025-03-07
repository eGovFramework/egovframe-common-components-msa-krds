package egovframework.com.cop.brd.util;

import egovframework.com.cop.brd.entity.*;
import egovframework.com.cop.brd.service.*;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

public class EgovBoardUtility {

    public static BbsVO bbsEntityToVO(Bbs bbs) {
        BbsVO vo = new BbsVO();
        vo.setBbsId(bbs.getBbsId().getBbsId());
        vo.setNttId(bbs.getBbsId().getNttId());
        BeanUtils.copyProperties(bbs, vo);
        return vo;
    }

    public static Bbs bbsVOToEntity(BbsVO vo) {
        Bbs bbs = new Bbs();
        BbsId id = new BbsId();
        BeanUtils.copyProperties(vo, bbs);

        id.setBbsId(vo.getBbsId());
        id.setNttId(vo.getNttId());

        bbs.setBbsId(id);
        bbs.setNtcrId(vo.getFrstRegisterId());
        bbs.setFrstRegistPnttm(LocalDateTime.now());
        bbs.setLastUpdtPnttm(LocalDateTime.now());

        return bbs;
    }

    public static File fileVOToEntity(FileVO fileVO) {
        File file = new File();
        file.setAtchFileId(fileVO.getAtchFileId());
        file.setCreatDt(LocalDateTime.now());
        file.setUseAt(fileVO.getUseAt());
        return file;
    }

    public static FileDetail fileDetailVOToEntity(FileVO fileVO) {
        FileDetailId fileDetailId = new FileDetailId();
        fileDetailId.setAtchFileId(fileVO.getAtchFileId());
        fileDetailId.setFileSn(fileVO.getFileSn());

        FileDetail fileDetail = new FileDetail();
        fileDetail.setFileDetailId(fileDetailId);
        fileDetail.setFileStreCours(fileVO.getFileStreCours());
        fileDetail.setStreFileNm(fileVO.getStreFileNm());
        fileDetail.setOrignlFileNm(fileVO.getOrignlFileNm());
        fileDetail.setFileExtsn(fileVO.getFileExtsn().toUpperCase());
        fileDetail.setFileSize(fileVO.getFileSize());
        return fileDetail;
    }

    public static FileVO fileDeatailEntityToVO(FileDetail fileDetail) {
        FileVO fileVO = new FileVO();
        BeanUtils.copyProperties(fileDetail, fileVO);
        fileVO.setAtchFileId(fileDetail.getFileDetailId().getAtchFileId());
        fileVO.setFileSn(fileDetail.getFileDetailId().getFileSn());
        return fileVO;
    }

    public static CommentVO commentEntityToVO(Comment comment) {
        CommentVO commentVO = new CommentVO();
        BeanUtils.copyProperties(comment, commentVO);

        commentVO.setBbsId(comment.getCommentId().getBbsId());
        commentVO.setNttId(comment.getCommentId().getNttId());
        commentVO.setAnswerNo(comment.getCommentId().getAnswerNo());

        return commentVO;
    }

    public static Comment commentVOToEntity(CommentVO commentVO) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentVO, comment);

        CommentId commentId = new CommentId();
        commentId.setBbsId(commentVO.getBbsId());
        commentId.setNttId(commentVO.getNttId());
        commentId.setAnswerNo(commentVO.getAnswerNo());

        comment.setCommentId(commentId);
        return comment;
    }

    public static StsfdgVO StsfdgEntiyToVO(Stsfdg stsfdg) {
        StsfdgVO stsfdgVO = new StsfdgVO();
        BeanUtils.copyProperties(stsfdg, stsfdgVO);
        return stsfdgVO;
    }

    public static Stsfdg satisfationVOToEntity(StsfdgVO stsfdgVO) {
        Stsfdg stsfdg = new Stsfdg();
        BeanUtils.copyProperties(stsfdgVO, stsfdg);
        return stsfdg;
    }

    public static BbsMasterOptnVO bbsmasteroptnEntityToVO(BbsMasterOptn bbsmasteroptn) {
        BbsMasterOptnVO bbsMasterOptnVO = new BbsMasterOptnVO();
        BeanUtils.copyProperties(bbsmasteroptn, bbsMasterOptnVO);
        return bbsMasterOptnVO;
    }
}
