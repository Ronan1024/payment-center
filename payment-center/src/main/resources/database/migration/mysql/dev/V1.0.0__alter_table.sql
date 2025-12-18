ALTER TABLE pay.pay_mch_info MODIFY COLUMN type tinyint NOT NULL COMMENT '商户类型 (2.普通商户 3.特约商户）';
ALTER TABLE pay.pay_mch_info MODIFY COLUMN state TINYINT NOT NULL COMMENT '商户状态 0：停用 1：正常';
ALTER TABLE pay.pay_mch_info ADD isv_name varchar(64) NULL COMMENT '服务商名称';
ALTER TABLE pay.pay_mch_info ADD tenant_id BIGINT NULL COMMENT '租户id';



ALTER TABLE pay.pay_mch_info ADD enterprise_info_id BIGINT NOT NULL COMMENT '企业信息id';
ALTER TABLE pay.pay_mch_info ADD bank_account_info_id BIGINT NOT NULL COMMENT '商户银行信息id';
ALTER TABLE pay.pay_mch_info ADD enterprise_name varchar(50) NULL COMMENT '企业名称（冗余字段，用于页面查询）';


CREATE TABLE pay.pay_enterprise_info (
    id BIGINT NOT NULL COMMENT '主键',
    enterprise_name varchar(255) NULL COMMENT '企业名称',
    enterprise_dominant_type TINYINT(1) NULL COMMENT '企业性质',
    unified_social_credit_code varchar(255) NULL COMMENT '社会统一信用代码',
    enterprise_status TINYINT NULL COMMENT '企业状态 0：停用 1：正常',
    certificate_type TINYINT NULL COMMENT '证件类型',
    enterprise_address varchar(100) NULL COMMENT '企业地址',
    enterprise_industry int NULL COMMENT '所属行业',
    business_scope varchar(500) NOT NULL COMMENT '经营范围',
    representative_name varchar(20) NULL COMMENT '法人姓名',
    representative_sex TINYINT NULL COMMENT '法人性别(1.男 2.女）',
    representative_tel varchar(50) NULL COMMENT '法人手机号',
    representative_certificate_type TINYINT NULL COMMENT '证件类型 1.身份证 2.护照',
    representative_certificate_id varchar(20) NOT NULL COMMENT '证件号码',
    representative_certificate_effect_start_time datetime NOT NULL COMMENT '证件有效起始日期',
    representative_certificate_effect_end_time datetime DEFAULT NULL COMMENT '证件有效终止日期',
    create_time datetime NULL COMMENT '创建时间',
    update_time datetime NULL COMMENT '更新时间',
    CONSTRAINT pay_enterprise_info PRIMARY KEY (id)
) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_bin
COMMENT='企业信息';


CREATE TABLE pay.pay_bank_account_info
(
    id           BIGINT NOT NULL COMMENT '主键',
    bank_name    varchar(20)  NOT NULL COMMENT '开户行名称',
    branch_name  varchar(100) NOT NULL COMMENT '开户网点名称',
    account_name varchar(100) NOT NULL COMMENT '账户名',
    account_id   BIGINT NULL COMMENT '账户号',
    province_id  bigint NULL COMMENT '省ID',
    province     varchar(50) NULL COMMENT '省',
    city_id      bigint NULL COMMENT '城市ID',
    city         varchar(50) NULL COMMENT '城市',
    create_time datetime NULL COMMENT '创建时间',
    update_time datetime NULL COMMENT '更新时间',
    CONSTRAINT pay_bank_account_info PRIMARY KEY (id)
) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_bin
COMMENT='支付商户银行信息';



ALTER TABLE pay.pay_isv_info ADD tenant_id BIGINT NULL COMMENT '租户id';