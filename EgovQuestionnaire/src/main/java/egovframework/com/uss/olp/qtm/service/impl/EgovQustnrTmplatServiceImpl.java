package egovframework.com.uss.olp.qtm.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import egovframework.com.uss.olp.qtm.entity.QQustnrTmplat;
import egovframework.com.uss.olp.qtm.entity.QUserMaster;
import egovframework.com.uss.olp.qtm.entity.QustnrTmplat;
import egovframework.com.uss.olp.qtm.entity.UserMaster;
import egovframework.com.uss.olp.qtm.repository.*;
import egovframework.com.uss.olp.qtm.service.EgovQustnrTmplatService;
import egovframework.com.uss.olp.qtm.service.QustnrTmplatDTO;
import egovframework.com.uss.olp.qtm.service.QustnrTmplatVO;
import egovframework.com.uss.olp.qtm.util.EgovQustnrTmplatUtility;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("qtmEgovQustnrTmplatService")
public class EgovQustnrTmplatServiceImpl extends EgovAbstractServiceImpl implements EgovQustnrTmplatService {

    private final EgovQustnrTmplatRepository repository;
    private final EgovQestnrInfoRepository egovQestnrInfoRepository;
    private final EgovQustnrQesitmRepository egovQustnrQesitmRepository;
    private final EgovQustnrIemRepository egovQustnrIemRepository;
    private final EgovQustnrRespondInfoRepository egovQustnrRespondInfoRepository;
    private final EgovQustnrRspnsResultRepository egovQustnrRspnsResultRepository;
    private final EgovIdGnrService idgenService;
    private final JPAQueryFactory queryFactory;

    public EgovQustnrTmplatServiceImpl(
            EgovQustnrTmplatRepository repository,
            EgovQestnrInfoRepository egovQestnrInfoRepository,
            EgovQustnrQesitmRepository egovQustnrQesitmRepository,
            EgovQustnrIemRepository egovQustnrIemRepository,
            EgovQustnrRespondInfoRepository egovQustnrRespondInfoRepository,
            EgovQustnrRspnsResultRepository egovQustnrRspnsResultRepository,
            @Qualifier("egovQustnrTmplatManageIdGnrService") EgovIdGnrService idgenService,
            JPAQueryFactory queryFactory) {
        this.repository = repository;
        this.egovQestnrInfoRepository = egovQestnrInfoRepository;
        this.egovQustnrQesitmRepository = egovQustnrQesitmRepository;
        this.egovQustnrIemRepository = egovQustnrIemRepository;
        this.egovQustnrRespondInfoRepository = egovQustnrRespondInfoRepository;
        this.egovQustnrRspnsResultRepository = egovQustnrRspnsResultRepository;
        this.idgenService = idgenService;
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<QustnrTmplatDTO> list(QustnrTmplatVO qustnrTmplatVO) {
        Pageable pageable = PageRequest.of(qustnrTmplatVO.getFirstIndex(), qustnrTmplatVO.getRecordCountPerPage());
        String searchCondition = qustnrTmplatVO.getSearchCondition();
        String searchKeyword = qustnrTmplatVO.getSearchKeyword();

        QQustnrTmplat qustnrTmplat = QQustnrTmplat.qustnrTmplat;
        QUserMaster userMaster = QUserMaster.userMaster;

        BooleanBuilder where = new BooleanBuilder();
        if ("1".equals(searchCondition) && searchKeyword != null && !searchKeyword.isEmpty()) {
            where.and(qustnrTmplat.qustnrTmplatDc.contains(searchKeyword));
        } else if ("2".equals(searchCondition) && searchKeyword != null && !searchKeyword.isEmpty()) {
            where.and(qustnrTmplat.qustnrTmplatTy.contains(searchKeyword));
        }

        List<Tuple> results = qustnrTmplatQuery()
                .where(where)
                .orderBy(qustnrTmplat.frstRegistPnttm.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = Optional.ofNullable(
                queryFactory
                        .select(qustnrTmplat.count())
                        .from(qustnrTmplat)
                        .leftJoin(userMaster)
                        .on(qustnrTmplat.frstRegisterId.eq(userMaster.esntlId))
                        .where(where)
                        .fetchOne()
        ).orElse(0L);

        List<QustnrTmplatDTO> content = results.stream().map(tuple -> {
            QustnrTmplat qtm = tuple.get(qustnrTmplat);
            UserMaster user = tuple.get(userMaster);
            String userNm = user != null && user.getUserNm() != null ? user.getUserNm() : "";

            return  new QustnrTmplatDTO(
                Objects.requireNonNull(qtm).getQustnrTmplatId(),
                qtm.getQustnrTmplatTy(),
                qtm.getQustnrTmplatDc(),
                qtm.getQustnrTmplatPathNm(),
                qtm.getFrstRegistPnttm().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                qtm.getFrstRegisterId(),
                qtm.getLastUpdtPnttm().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                qtm.getLastUpdusrId(),
                qtm.getQustnrTmplatImageInfo(),
                userNm
            );
        }).collect(Collectors.toList());
        return new PageImpl<>(content,pageable,total);
    }

    @Override
    public QustnrTmplatDTO detail(QustnrTmplatVO qustnrTmplatVO) {

        QQustnrTmplat qustnrTmplat = QQustnrTmplat.qustnrTmplat;
        QUserMaster userMaster = QUserMaster.userMaster;

        Tuple tuple = qustnrTmplatQuery().where(qustnrTmplat.qustnrTmplatId.eq(qustnrTmplatVO.getQustnrTmplatId())).fetchOne();

        QustnrTmplat qtm = tuple.get(qustnrTmplat);
        UserMaster user = tuple.get(userMaster);
        String userNm = user != null && user.getUserNm() != null ? user.getUserNm() : "";

        return  new QustnrTmplatDTO(
                Objects.requireNonNull(qtm).getQustnrTmplatId(),
                qtm.getQustnrTmplatTy(),
                qtm.getQustnrTmplatDc(),
                qtm.getQustnrTmplatPathNm(),
                qtm.getFrstRegistPnttm().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                qtm.getFrstRegisterId(),
                qtm.getLastUpdtPnttm().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                qtm.getLastUpdusrId(),
                qtm.getQustnrTmplatImageInfo(),
                userNm
        );
    }

    @Transactional
    @Override
    public QustnrTmplatVO insert(QustnrTmplatVO qustnrTmplatVO, Map<String, String> userInfo) {
        try {
            String qustnrTmplatId = idgenService.getNextStringId();
            qustnrTmplatVO.setQustnrTmplatId(qustnrTmplatId);

            QustnrTmplat qustnrTmplat = EgovQustnrTmplatUtility.qustnrTmplatVOToEntity(qustnrTmplatVO);
            qustnrTmplat.setQustnrTmplatImageInfo(qustnrTmplatVO.getQustnrTmplatImageInfo().getBytes());
            qustnrTmplat.setFrstRegistPnttm(LocalDateTime.now());
            qustnrTmplat.setFrstRegisterId(userInfo.get("uniqId"));
            qustnrTmplat.setLastUpdtPnttm(LocalDateTime.now());
            qustnrTmplat.setLastUpdusrId(userInfo.get("uniqId"));
            return EgovQustnrTmplatUtility.qustnrTmplatEntityToVO(repository.save(qustnrTmplat));
        } catch (Exception ex) {
            leaveaTrace("fail.common.insert");
            return null;
        }
    }

    @Transactional
    @Override
    public QustnrTmplatVO update(QustnrTmplatVO qustnrTmplatVO, Map<String, String> userInfo) {
        String qustnrTmplatId = qustnrTmplatVO.getQustnrTmplatId();
        return repository.findById(qustnrTmplatId)
                .map(item -> {
                    try {
                        return updateItem(item, qustnrTmplatVO, userInfo.get("uniqId"));
                    } catch (IOException e) {
                        return null;
                    }
                })
                .map(EgovQustnrTmplatUtility::qustnrTmplatEntityToVO)
                .orElse(null);
    }

    @Transactional
    @Override
    public boolean delete(QustnrTmplatVO qustnrTmplatVO) {
        String qustnrTmplatId = qustnrTmplatVO.getQustnrTmplatId();
        return repository.findById(qustnrTmplatId)
                .map(result -> {
                    egovQustnrRspnsResultRepository.deleteByQustnrRspnsResultIdQustnrTmplatId(qustnrTmplatId);
                    egovQustnrRespondInfoRepository.deleteByQustnrRespondInfoIdQustnrTmplatId(qustnrTmplatId);
                    egovQustnrIemRepository.deleteByQustnrIemIdQustnrTmplatId(qustnrTmplatId);
                    egovQustnrQesitmRepository.deleteByQustnrQesitmIdQustnrTmplatId(qustnrTmplatId);
                    egovQestnrInfoRepository.deleteByQestnrInfoIdQustnrTmplatId(qustnrTmplatId);
                    repository.deleteById(qustnrTmplatId);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public byte[] getImage(String qustnrTmplatId) {
        return repository.findById(qustnrTmplatId).get().getQustnrTmplatImageInfo();
    }

    private QustnrTmplat updateItem(QustnrTmplat qustnrTmplat, QustnrTmplatVO qustnrTmplatVO, String uniqId) throws IOException {
        if (!"update".equals(qustnrTmplatVO.getQustnrTmplatImageState())) {
            qustnrTmplat.setQustnrTmplatImageInfo(qustnrTmplatVO.getQustnrTmplatImageInfo().getBytes());
        }
        qustnrTmplat.setQustnrTmplatTy(qustnrTmplatVO.getQustnrTmplatTy());
        qustnrTmplat.setQustnrTmplatDc(qustnrTmplatVO.getQustnrTmplatDc());
        qustnrTmplat.setQustnrTmplatPathNm(qustnrTmplatVO.getQustnrTmplatPathNm());
        qustnrTmplat.setLastUpdtPnttm(LocalDateTime.now());
        qustnrTmplat.setLastUpdusrId(uniqId);
        return qustnrTmplat;
    }

    private JPAQuery<Tuple> qustnrTmplatQuery(){

        QQustnrTmplat qustnrTmplat = QQustnrTmplat.qustnrTmplat;
        QUserMaster userMaster = QUserMaster.userMaster;

        return queryFactory
                .select(qustnrTmplat,userMaster)
                .from(qustnrTmplat)
                .leftJoin(userMaster)
                .on(qustnrTmplat.frstRegisterId.eq(userMaster.esntlId));
    }

}
