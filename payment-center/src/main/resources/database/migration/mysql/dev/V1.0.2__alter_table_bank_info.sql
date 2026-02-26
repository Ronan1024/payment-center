ALTER TABLE pay.pay_bank_account_info ADD area_id BIGINT NULL COMMENT '区域id';
ALTER TABLE pay.pay_bank_account_info ADD area varchar(20) NULL COMMENT '区';
ALTER TABLE pay.pay_way MODIFY COLUMN paying_client tinyint(1) NULL COMMENT '支付可用客户端(同支付接口的应用场景)';
ALTER TABLE pay.pay_interface_define DROP COLUMN pay_way_id;


