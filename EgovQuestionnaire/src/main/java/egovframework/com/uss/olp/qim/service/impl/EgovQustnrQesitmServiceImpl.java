package egovframework.com.uss.olp.qim.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import egovframework.com.uss.olp.qim.entity.QQustnrQesitm;
import egovframework.com.uss.olp.qim.entity.QUserMaster;
import egovframework.com.uss.olp.qim.entity.QustnrQesitm;
import egovframework.com.uss.olp.qim.entity.UserMaster;
import egovframework.com.uss.olp.qim.repository.EgovQustnrQesitmRepository;
import egovframework.com.uss.olp.qim.service.EgovQustnrQesitmService;
import egovframework.com.uss.olp.qim.service.QustnrQesitmDTO;
import egovframework.com.uss.olp.qim.service.QustnrQesitmVO;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("qimEgovQustnrQesitmService")
@RequiredArgsConstructor
public class EgovQustnrQesitmServiceImpl extends EgovAbstractServiceImpl implements EgovQustnrQesitmService {

    private final EgovQustnrQesitmRepository repository;
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<QustnrQesitmDTO> list(QustnrQesitmVO qustnrQesitmVO) {
        Pageable pageable = PageRequest.of(qustnrQesitmVO.getFirstIndex(), qustnrQesitmVO.getRecordCountPerPage());
        String searchCondition = qustnrQesitmVO.getSearchCondition();
        String searchKeyword = qustnrQesitmVO.getSearchKeyword();
        String qustnrTmplatId = qustnrQesitmVO.getQustnrTmplatId();
        String qestnrId = qustnrQesitmVO.getQestnrId();

        QQustnrQesitm qustnrQesitm = QQustnrQesitm.qustnrQesitm;
        QUserMaster userMaster = QUserMaster.userMaster;

        BooleanBuilder where = new BooleanBuilder();
        if ("1".equals(searchCondition) && searchKeyword != null && !searchKeyword.isEmpty()) {
            where.and(qustnrQesitm.qestnCn.contains(searchKeyword));
        } else if ("2".equals(searchCondition) && searchKeyword != null && !searchKeyword.isEmpty()) {
            where.and(userMaster.userNm.contains(searchKeyword));
        }

        JPAQuery<Tuple> query = queryFactory
                .select(qustnrQesitm, userMaster)
                .from(qustnrQesitm)
                .leftJoin(userMaster)
                .on(qustnrQesitm.frstRegisterId.eq(userMaster.esntlId))
                .where(where
                        .and(qustnrQesitm.qustnrQesitmId.qustnrTmplatId.eq(qustnrTmplatId))
                        .and(qustnrQesitm.qustnrQesitmId.qestnrId.eq(qestnrId)))
                .orderBy(qustnrQesitm.qustnrQesitmId.qestnrId.desc(), qustnrQesitm.qustnrQesitmId.qustnrQesitmId.desc());

        List<Tuple> results = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = Optional.ofNullable(
                queryFactory
                        .select(qustnrQesitm.count())
                        .from(qustnrQesitm)
                        .leftJoin(userMaster)
                        .on(qustnrQesitm.frstRegisterId.eq(userMaster.esntlId))
                        .where(where
                                .and(qustnrQesitm.qustnrQesitmId.qustnrTmplatId.eq(qustnrTmplatId))
                                .and(qustnrQesitm.qustnrQesitmId.qestnrId.eq(qestnrId)))
                        .fetchOne()
        ).orElse(0L);

        List<QustnrQesitmDTO> content = results.stream().map(tuple -> {

            QustnrQesitm qim = tuple.get(qustnrQesitm);
            UserMaster user = tuple.get(userMaster);
            String userNm = user != null && user.getUserNm() != null ? user.getUserNm() : "";

            return new QustnrQesitmDTO(
                    Objects.requireNonNull(qim).getQustnrQesitmId().getQestnrId(),
                    qim.getQustnrQesitmId().getQustnrQesitmId(),
                    qim.getQustnrQesitmId().getQustnrTmplatId(),
                    qim.getQestnSn(),
                    qim.getQestnTyCode(),
                    qim.getQestnCn(),
                    qim.getMxmmChoiseCo(),
                    qim.getFrstRegistPnttm().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    qim.getFrstRegisterId(),
                    qim.getLastUpdtPnttm().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    qim.getLastUpdusrId(),
                    userNm
            );
        }).collect(Collectors.toList());

        return new PageImpl<>(content,pageable,total);
    }

}
