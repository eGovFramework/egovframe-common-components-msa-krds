ALTER TABLE MSATNAUTHORGROUPINFO COMMENT = '권한그룹정보';
ALTER TABLE `MSATNAUTHORGROUPINFO` CHANGE `GROUP_CREAT_DE` `GROUP_CREAT_DE` char(20) NOT NULL  COMMENT '그룹생성일';
ALTER TABLE `MSATNAUTHORGROUPINFO` CHANGE `GROUP_DC` `GROUP_DC` varchar(100) DEFAULT NULL  COMMENT '그룹설명';
ALTER TABLE `MSATNAUTHORGROUPINFO` CHANGE `GROUP_ID` `GROUP_ID` char(20) NOT NULL DEFAULT ''  COMMENT '그룹ID';
ALTER TABLE `MSATNAUTHORGROUPINFO` CHANGE `GROUP_NM` `GROUP_NM` varchar(60) NOT NULL  COMMENT '그룹명';

ALTER TABLE MSATNAUTHORINFO COMMENT = '권한정보';
ALTER TABLE `MSATNAUTHORINFO` CHANGE `AUTHOR_CODE` `AUTHOR_CODE` varchar(30) NOT NULL DEFAULT ''  COMMENT '권한코드';
ALTER TABLE `MSATNAUTHORINFO` CHANGE `AUTHOR_CREAT_DE` `AUTHOR_CREAT_DE` char(20) NOT NULL  COMMENT '권한생성일';
ALTER TABLE `MSATNAUTHORINFO` CHANGE `AUTHOR_DC` `AUTHOR_DC` varchar(200) DEFAULT NULL  COMMENT '권한설명';
ALTER TABLE `MSATNAUTHORINFO` CHANGE `AUTHOR_NM` `AUTHOR_NM` varchar(60) NOT NULL  COMMENT '권한명';

ALTER TABLE MSATNAUTHORROLERELATE COMMENT = '권한롤관계';
ALTER TABLE `MSATNAUTHORROLERELATE` CHANGE `AUTHOR_CODE` `AUTHOR_CODE` varchar(30) NOT NULL  COMMENT '권한코드';
ALTER TABLE `MSATNAUTHORROLERELATE` CHANGE `CREAT_DT` `CREAT_DT` datetime DEFAULT NULL  COMMENT '생성일시';
ALTER TABLE `MSATNAUTHORROLERELATE` CHANGE `ROLE_CODE` `ROLE_CODE` varchar(50) NOT NULL  COMMENT '롤코드';

ALTER TABLE MSATNROLEINFO COMMENT = '롤정보';
ALTER TABLE `MSATNROLEINFO` CHANGE `ROLE_CODE` `ROLE_CODE` varchar(50) NOT NULL DEFAULT ''  COMMENT '롤코드';
ALTER TABLE `MSATNROLEINFO` CHANGE `ROLE_CREAT_DE` `ROLE_CREAT_DE` char(20) NOT NULL  COMMENT '롤생성일';
ALTER TABLE `MSATNROLEINFO` CHANGE `ROLE_DC` `ROLE_DC` varchar(200) DEFAULT NULL  COMMENT '롤설명';
ALTER TABLE `MSATNROLEINFO` CHANGE `ROLE_NM` `ROLE_NM` varchar(60) NOT NULL  COMMENT '롤명';
ALTER TABLE `MSATNROLEINFO` CHANGE `ROLE_PTTRN` `ROLE_PTTRN` varchar(300) DEFAULT NULL  COMMENT '롤패턴';
ALTER TABLE `MSATNROLEINFO` CHANGE `ROLE_SORT` `ROLE_SORT` varchar(10) DEFAULT NULL  COMMENT '롤정렬';
ALTER TABLE `MSATNROLEINFO` CHANGE `ROLE_TY` `ROLE_TY` varchar(80) DEFAULT NULL  COMMENT '롤유형';

ALTER TABLE MSATNROLES_HIERARCHY COMMENT = '롤 계층구조';
ALTER TABLE `MSATNROLES_HIERARCHY` CHANGE `CHLDRN_ROLE` `CHLDRN_ROLE` varchar(30) NOT NULL  COMMENT '자식롤';
ALTER TABLE `MSATNROLES_HIERARCHY` CHANGE `PARNTS_ROLE` `PARNTS_ROLE` varchar(30) NOT NULL  COMMENT '부모롤';

ALTER TABLE MSATNEMPLYRSCRTYESTBS COMMENT = '사용자보안설정';
ALTER TABLE `MSATNEMPLYRSCRTYESTBS` CHANGE `AUTHOR_CODE` `AUTHOR_CODE` varchar(30) NOT NULL  COMMENT '권한코드';
ALTER TABLE `MSATNEMPLYRSCRTYESTBS` CHANGE `MBER_TY_CODE` `MBER_TY_CODE` char(5) DEFAULT NULL  COMMENT '회원유형코드';
ALTER TABLE `MSATNEMPLYRSCRTYESTBS` CHANGE `SCRTY_DTRMN_TRGET_ID` `SCRTY_DTRMN_TRGET_ID` varchar(20) NOT NULL  COMMENT '보안설정대상ID';

ALTER TABLE MSATNMENUCREATDTLS COMMENT = '메뉴생성내역';
ALTER TABLE `MSATNMENUCREATDTLS` CHANGE `AUTHOR_CODE` `AUTHOR_CODE` varchar(30) NOT NULL  COMMENT '권한코드';
ALTER TABLE `MSATNMENUCREATDTLS` CHANGE `MAPNG_CREAT_ID` `MAPNG_CREAT_ID` varchar(30)  COMMENT '매핑생성ID';
ALTER TABLE `MSATNMENUCREATDTLS` CHANGE `MENU_NO` `MENU_NO` decimal(20,0) NOT NULL  COMMENT '메뉴번호';
