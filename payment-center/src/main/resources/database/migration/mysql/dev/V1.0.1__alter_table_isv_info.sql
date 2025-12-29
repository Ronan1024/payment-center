ALTER TABLE pay.pay_isv_info ADD enterprise_info_id BIGINT NOT NULL COMMENT '企业信息id';
ALTER TABLE pay.pay_way ADD paying_category TINYINT NOT NULL COMMENT '支付类别 1 支付 2 代付';

CREATE TABLE `pay_interface_type`
(
    `id`                  bigint NOT NULL,
    `interface_type_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '接口类型代码',
    `interface_type_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '接口类型名称',
    `disable`             bit(1)                                                DEFAULT b'0' COMMENT '是否禁用',
    `config_way`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '支持的配置方式',
    `remark`              varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
    `description`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '配置定义描述',
    `callback_ip`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '回调白名单',
    `is_callback`         bit(1)                                                DEFAULT b'0' COMMENT '是否开启回调',
    `create_time`         datetime                                              DEFAULT NULL,
    `update_time`         datetime                                              DEFAULT NULL,
    `create_by`           bigint                                                DEFAULT NULL COMMENT '创建人',
    `update_by`           bigint                                                DEFAULT NULL COMMENT '更新人',
    `create_by_name`      varchar(255)                                          DEFAULT NULL COMMENT '创建人姓名',
    `update_by_name`      varchar(255)                                          DEFAULT NULL COMMENT '更新人姓名',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='接口类型配置表';

ALTER TABLE pay.pay_interface_define MODIFY COLUMN code varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '接口代码';
ALTER TABLE pay.pay_interface_define ADD pay_interface_type_id BIGINT NOT NULL COMMENT '接口类型ID';
ALTER TABLE pay.pay_interface_define ADD pay_way_id BIGINT NOT NULL COMMENT '支付类型ID';
ALTER TABLE pay.pay_interface_define ADD scenario TINYINT NULL COMMENT '应用场景 1 移动app 2 移动网页 3 PC网页 4 微信公众平台 5 手机扫码';
