CREATE TABLE TB_TRX_INFO (
	TRXCODE VARCHAR(50) NOT NULL,                        /* 거래코드 */
	SVC_CODE VARCHAR(50) NOT NULL,                       /* 서비스코드 */
	MODE VARCHAR(50) NOT NULL,                           /* 모드 */
	NONCE VARCHAR(100) DEFAULT NULL,                     /* nonce(presentType=1) */
	ZKP_NONCE VARCHAR(100) DEFAULT NULL,                 /* zkpNonce(presentType=2) */
	VP_VERIFY_RESULT VARCHAR(1) DEFAULT 'N' NOT NULL,    /* VP 검증 결과 여부 */
	VP CLOB DEFAULT NULL,                                /* VP Data */
	TRX_STS_CODE VARCHAR(4) DEFAULT '0001' NOT NULL,     /* 거래상태코드(0001: 서비스요청, 0002: profile요청, 0003: VP 검증요청, 0004: VP 검증완료) */
	PROFILE_SEND_DT TIMESTAMP DEFAULT NULL,              /* profile 송신일시(M310) */
	IMG_SEND_DT TIMESTAMP DEFAULT NULL,                  /* 이미지 송신일시(M320) */
	VP_RECEPT_DT TIMESTAMP DEFAULT NULL,                 /* VP 수신일시(M400) */
	ERROR_CN VARCHAR(4000) DEFAULT NULL,                 /* 오류 내용 */
	REG_DT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, /* 등록일시 */
	UDT_DT TIMESTAMP DEFAULT NULL,                       /* 수정일시 */
	PRIMARY KEY (TRXCODE)
);