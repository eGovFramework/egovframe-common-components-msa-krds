/* 거래정보 */
CREATE TABLE tb_trx_info (
  trxcode varchar(50) NOT NULL,                          /* 거래코드 */
  svc_code varchar(50) NOT NULL,                         /* 서비스코드 */
  mode varchar(50) NOT NULL,                             /* 모드 */
  nonce varchar(100) DEFAULT NULL,                       /* nonce(presentType=1) */
  zkp_nonce varchar(100) DEFAULT NULL,                   /* zkpNonce(presentType=2) */
  vp_verify_result varchar(1) NOT NULL DEFAULT 'N',      /* VP 검증 결과 여부 */
  vp longtext DEFAULT NULL,                              /* VP Data */
  trx_sts_code varchar(4) NOT NULL DEFAULT '0001',       /* 거래상태코드(0001: 서비스요청, 0002: profile요청, 0003: VP 검증요청, 0004: VP 검증완료) */
  profile_send_dt timestamp NULL DEFAULT NULL,           /* profile 송신일시(M310) */
  img_send_dt timestamp NULL DEFAULT NULL,               /* 이미지 송신일시(M320) */
  vp_recept_dt timestamp NULL DEFAULT NULL,              /* VP 수신일시(M400) */
  error_cn varchar(4000) DEFAULT NULL,                   /* 오류 내용 */
  reg_dt timestamp NOT NULL DEFAULT current_timestamp(), /* 등록일시 */
  udt_dt timestamp NULL DEFAULT NULL,                    /* 수정일시 */
  PRIMARY KEY (trxcode)
) CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;