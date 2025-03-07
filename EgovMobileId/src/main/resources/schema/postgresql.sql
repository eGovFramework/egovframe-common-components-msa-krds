/* 거래정보 */
CREATE TABLE tb_trx_info (
	trxcode varchar(50) NOT NULL,                        /* 거래코드 */
	svc_code varchar(50) NOT NULL,                       /* 서비스코드 */
	"mode" varchar(50) NOT NULL,                         /* 모드 */
	nonce varchar(100) DEFAULT NULL NULL,                /* nonce(presentType=1) */
	zkp_nonce varchar(100) DEFAULT NULL NULL,            /* zkpNonce(presentType=2) */
	vp_verify_result varchar(1) DEFAULT 'N' NOT NULL,    /* VP 검증 결과 여부 */
	vp text NULL,                                        /* VP Data */
	trx_sts_code varchar(4) DEFAULT '0001' NOT NULL,     /* 거래상태코드(0001: 서비스요청, 0002: profile요청, 0003: VP 검증요청, 0004: VP 검증완료) */
	profile_send_dt timestamp NULL,                      /* profile 송신일시(M310) */
	img_send_dt timestamp NULL,                          /* 이미지 송신일시(M320) */
	vp_recept_dt timestamp NULL,                         /* VP 수신일시(M400) */
	error_cn varchar(4000) DEFAULT NULL NULL,            /* 오류 내용 */
	reg_dt timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, /* 등록일시 */
	udt_dt timestamp NULL,                               /* 수정일시 */
	PRIMARY KEY (trxcode)
);