create table account_flow
(
    id                 bigint       not null
        primary key,
    request_id         bigint       null comment '结算受理单',
    trading_type       tinyint(1)   null comment '交易类型',
    amount             decimal      null comment '交易金额',
    channel_id         bigint       null comment '渠道id',
    channel_code       varchar(200) null comment '渠道编号',
    state              tinyint(1)   null comment '状态',
    trading_time       datetime     null comment '交易时间',
    channel_trading_id bigint       null comment '渠道交易id'
)
    comment '账单流水';

create table bill_flow
(
    id               bigint       not null
        primary key,
    create_time      datetime     null,
    update_time      datetime     null,
    amount           bigint       null comment '交易金额',
    trading_type     varchar(20)  null comment '交易类型',
    state            tinyint(1)   null comment '账单状态',
    trading_mode     tinyint      null comment '交易模式',
    order_id         bigint       null comment '交易订单id',
    bill_date        date         null comment '账单日期',
    channel_order_id varchar(255) null comment '渠道订单号',
    channel_code     varchar(255) null comment '渠道编号',
    channel_id       bigint       null comment '渠道id',
    trading_time     datetime     null comment '交易时间',
    mch_id           bigint       null comment '商户id'
)
    comment '账单流水';

create table callback_handler_log
(
    id               bigint           not null
        primary key,
    has_handler      bit default b'0' null,
    handler_error    text             null comment '处理异常信息',
    paying_agency    tinyint          null comment '支付机构',
    pay_type         tinyint          null comment '支付类型',
    callback_context text             null comment '回调内容',
    create_time      datetime         null,
    update_time      datetime         null,
    mch_no           varchar(255)     null comment '商户号',
    interface_code   varchar(50)      null comment '支付接口编号',
    trx_id           varchar(255)     null comment '上游机构流水号'
)
    comment '回调处理记录表';

create table channel_bill
(
    id                          bigint       not null
        primary key,
    bill_file_id                bigint       not null comment '账单文件id',
    bill_file_code              varchar(255) null comment '账单文件编号',
    bill_date                   varchar(255) null comment '账单日期',
    channel_id                  bigint       not null comment '渠道id',
    channel_code                varchar(255) null comment '渠道编号',
    sub_channel_id              bigint       null comment '二级渠道id 如接入通联 -> 通联接入 微信',
    sub_channel_code            varchar(255) null comment '二级渠道编号',
    order_id                    bigint       not null comment '平台订单id',
    trade_type                  varchar(20)  null comment '交易类型',
    channel_order_id            varchar(255) null comment '渠道订单号',
    trading_time                datetime     null comment '交易时间',
    channel_mch_no              varchar(255) null comment '渠道商户号',
    channel_origin_order        varchar(255) null comment '渠道原始订单号， 预留退款时',
    user_no                     varchar(255) null comment '渠道用户标识',
    channel_trading_origin_type tinyint(1)   null comment '渠道交易原始类型',
    mete_date                   text         null comment '交易原始数据',
    create_time                 datetime     null,
    update_time                 datetime     null,
    trading_state               tinyint      null comment '交易状态',
    trading_amount              bigint       null comment '交易金额',
    currency                    varchar(50)  null comment '交易币种',
    trading_fee                 bigint       null comment '交易手续费',
    trading_fee_detail          varchar(500) null comment '交易手续费详情',
    trading_fee_rule            varchar(255) null comment '交易规则',
    settlement_amount           bigint       null comment '结算金额',
    pay_way                     varchar(50)  null comment '支付方式',
    source_date                 text         null comment '原始数据',
    bill_state                  tinyint(1)   null comment '账单状态'
)
    comment '渠道账单' charset = utf8mb4;

create table channel_bill_file
(
    id                bigint       not null
        primary key,
    create_time       datetime     null,
    update_time       datetime     null,
    bill_date         varchar(20)  null comment '账单日期',
    parse_state       bit          null comment '解析状态',
    channel_bill_code varchar(255) null comment '渠道账单code 渠道-日期组成',
    parse_error       text         null comment '解析失败异常信息',
    channel_id        bigint       null comment '渠道id',
    channel_code      varchar(255) null comment '渠道编号'
)
    comment '渠道账单文件';

create table check_batch_record
(
    id                          bigint       not null
        primary key,
    check_batch_code            varchar(255) null comment '对账批次号由支付接口-商户号-时间组成',
    interface_code              varchar(255) null comment '支付接口id',
    interface_id                bigint       null comment '支付接口code',
    channel_mch_no              varchar(255) null comment '渠道商户号',
    create_time                 datetime     null,
    update_time                 datetime     null,
    state                       tinyint      null comment '批次状态',
    release_state               bigint       null comment '解析状态',
    release_err                 text         null comment '解析异常',
    channel_total_amount        bigint       null comment '渠道总金额',
    channel_total_count         bigint       null comment '渠道交易总数',
    channel_total_fee           bigint       null comment '渠道总手续费',
    channel_total_refund_amount bigint       null comment '渠道总退款金额',
    channel_total_refund_count  bigint       null comment '渠道退款总数量',
    diff_count                  bigint       null comment '差错总单数',
    org_bill_file_path          varchar(500) null comment '渠道对账文件存放地址',
    total_amount                bigint       null comment '平台交易总金额',
    total_count                 bigint       null comment '平台交易总数量',
    total_fee                   bigint       null comment '平台总手续费',
    total_refund_amount         bigint       null comment '平台退款总金额',
    total_refund_count          bigint       null comment '平台退款总书',
    un_handle_diff_count        bigint       null comment '待处差错订单数量',
    bill_date                   varchar(255) null comment '账单时间'
)
    comment '对账批次记录' charset = utf8mb4;

create table check_record
(
    id               bigint       null,
    order_id         bigint       null,
    amount           bigint       null comment '金额',
    trade_type       varchar(20)  null comment '交易类型',
    channel_order_id varchar(255) null comment '渠道订单号',
    channel_amount   bigint       null comment '渠道交易金额',
    check_state      tinyint(1)   null comment '对账状态',
    create_time      datetime     null,
    update_time      datetime     null,
    check_batch_id   bigint       null comment '对账记录id',
    check_batch_code varchar(255) null comment '对账记录code'
)
    comment '对账记录';


create table mch_account
(
    id             bigint                      not null
        primary key,
    mch_id         bigint                      null comment '商家id',
    balance        decimal(10, 2) default 0.00 null comment '余额',
    account_frozen decimal(10, 2)              null comment '冻结金额',
    isv_id         bigint                      null comment '服务商id'
)
    comment '商户账户';

create table mch_account_record
(
    id             bigint         null,
    amount         decimal(10, 2) null,
    create_time    datetime       null,
    type           tinyint(1)     null comment '钱包流水类型',
    mch_id         bigint         null comment '商家id',
    apply_order_id varchar(255)   null comment '申请订单号',
    apply_state    tinyint(1)     null comment '申请状态',
    apply_msg      text           null comment '申请异常信息',
    notify_url     varchar(255)   null comment '通知地址',
    mch_order_id   varchar(255)   null comment '商户订单id'
)
    comment '商家账户记录';

create table order_division_batch
(
    id             bigint       not null
        primary key,
    date           varchar(255) null comment '分账批次时间',
    create_time    datetime     null,
    update_time    datetime     null,
    division_state tinyint(1)   null comment '分账状态',
    fail_msg       text         null comment '失败原因',
    client_id      bigint       null comment '客户端id'
)
    comment '订单分账批次';

create table order_division_record
(
    id          bigint       not null
        primary key,
    batch_id    bigint       null,
    order_id    bigint       null,
    state       tinyint      null,
    err_msg     text         null,
    date        varchar(200) null,
    create_time datetime     null,
    update_time datetime     null comment '更新时间'
)
    comment '订单分账记录';

create table pay_interface_config
(
    id                 bigint           not null
        primary key,
    client_type        tinyint(1)       null comment '客户端类型',
    client_id          bigint           null comment '客户端id 如服务商、商家等id',
    interface_id       bigint           null comment '支付接口id',
    interface_params   json             null comment '支付接口参数',
    interface_rate     bigint           null comment '支付接口费率  * 100',
    enable             bit default b'0' null comment '是否启用',
    remark             varchar(500)     null comment '备注信息',
    create_time        datetime         null,
    create_by          bigint           null,
    update_time        datetime         null,
    update_by          bigint           null,
    paying_agency      varchar(255)     null comment '支付机构',
    name               varchar(255)     null comment '支付名称',
    pay_way            varchar(255)     null comment '支付接口',
    mch_no             varchar(255)     null comment '商户号',
    mch_channel_user   varchar(255)     null comment '支付渠道用户信息',
    interface_code     varchar(255)     null comment '接口编码',
    parent_client_id   bigint           null comment '上级客户端id',
    parent_client_code varchar(255)     null comment '上级客户端code'
)
    comment '支付接口配置';

create table pay_interface_define
(
    id                   bigint           not null
        primary key,
    name                 varchar(255)     null comment '接口名称',
    has_mch              bit              null comment '是否支持普通商户',
    enable               bit              null comment '是否开启',
    has_isv_mch          bit default b'0' null comment '是否支持服务商模式',
    isv_params           json             null comment '服务商支付参数配置',
    isv_sub_mch_params   json             null comment '特约商户配置',
    normal_mch_params    json             null comment '普通商户支付参数配置',
    remark               text             null comment '备注',
    create_time          datetime         null,
    create_by            bigint           null,
    update_time          datetime         null,
    update_by            bigint           null,
    pay_way              varchar(500)     null comment '支付方式列表使用, 分割',
    paying_agency        varchar(255)     null comment '支付机构',
    code                 varchar(255)     null,
    mch_channel_user_key varchar(255)     null comment '商户支付渠道用户key'
)
    comment '支付接口定义表';

create table pay_isv_info
(
    id             bigint            not null comment '服务商号'
        primary key,
    name           varchar(64)       not null comment '服务商名称',
    short_name     varchar(32)       not null comment '服务商简称',
    contact_name   varchar(32)       null comment '联系人姓名',
    contact_tel    varchar(32)       null comment '联系人手机号',
    contact_email  varchar(32)       null comment '联系人邮箱',
    state          tinyint default 1 not null comment '状态',
    remark         varchar(128)      null comment '备注',
    create_by      bigint            null comment '创建者用户ID',
    create_by_name varchar(64)       null comment '创建者姓名',
    create_time    datetime          not null comment '创建时间',
    update_time    datetime          not null on update CURRENT_TIMESTAMP comment '更新时间',
    update_by      bigint            null comment '更新人',
    update_by_name varchar(255)      null comment '更新人名称'
)
    comment '服务商信息表' charset = utf8mb4;

create table pay_mch_app
(
    id              bigint                 not null comment '应用ID'
        primary key,
    app_name        varchar(64) default '' not null comment '应用名称',
    mch_id          bigint                 not null comment '商户号',
    state           tinyint     default 1  not null comment '应用状态',
    app_secret      varchar(128)           not null comment '应用私钥',
    remark          varchar(128)           null comment '备注',
    create_by       bigint                 null comment '创建者用户ID',
    update_by       bigint                 null,
    update_by_name  varchar(255)           null,
    created_by_name varchar(64)            null comment '创建者姓名',
    create_time     datetime               not null comment '创建时间',
    update_time     datetime               null comment '更新时间',
    app_code        varchar(255)           null comment '应用code'
)
    comment '商户应用表' charset = utf8mb4;

create table pay_mch_info
(
    id             bigint            not null
        primary key,
    mch_name       varchar(255)      null comment '商户名称',
    mch_short_name varchar(255)      null comment '商户简称',
    contact_name   varchar(255)      null comment '联系人名称',
    contact_tel    varchar(15)       null comment '联系人电话',
    contact_email  varchar(32)       null comment '联系人邮箱',
    state          tinyint default 1 null comment '状态',
    remark         varchar(255)      null comment '备注',
    create_by      bigint            null comment '创建人',
    create_time    datetime          null comment '创建时间',
    update_by      bigint            null comment '更新人',
    update_time    datetime          null comment '更新时间',
    create_by_name varchar(255)      null comment '创建人名称',
    isv_id         bigint            null comment '服务商id',
    type           tinyint           null comment '商户类型',
    update_by_name varchar(255)      null comment '更新人名称',
    mch_no         varchar(255)      null comment '商户号'
)
    comment '支付服务商信息表';

create table pay_mch_notify_config
(
    id          bigint       not null
        primary key,
    notify_url  varchar(255) null comment '回调地址',
    mch_id      bigint       null comment '商户id',
    create_time datetime     null,
    update_time datetime     null
);

create table pay_mch_notify_record
(
    id                 bigint            not null comment '商户通知记录ID'
        primary key,
    order_id           bigint            null comment '订单id',
    order_type         tinyint           not null comment '订单类型:1-支付,2-退款',
    mch_order_no       varchar(64)       null comment '商户订单号',
    mch_id             bigint            null comment '商户id',
    isv_id             bigint            null comment '服务商id',
    mch_no             varchar(64)       null comment '商户号',
    isv_no             varchar(64)       null comment '服务商号',
    app_id             bigint            null comment '应用ID',
    notify_url         text              null comment '通知地址',
    res_result         json              null comment '通知响应结果',
    notify_count       int     default 0 not null comment '通知次数',
    notify_count_limit int     default 6 not null comment '最大通知次数, 默认6次',
    state              tinyint default 1 not null comment '通知状态,1-通知中,2-通知成功,3-通知失败',
    last_notify_time   datetime          null comment '最后一次通知时间',
    create_time        datetime          not null comment '创建时间',
    update_time        datetime          null comment '更新时间',
    product_type       varchar(10)       null comment '产品类型',
    notify_type        tinyint(1)        null comment '通知类型',
    next_notify_time   datetime          null comment '下次通知的时间'
)
    comment '商户通知记录表' charset = utf8mb4;

create table pay_mch_passage
(
    id             bigint auto_increment comment 'ID'
        primary key,
    mch_id         bigint       not null comment '商户号',
    app_id         bigint       not null comment '应用ID',
    interface_id   bigint       not null comment '支付接口',
    pay_way_code   varchar(255) not null comment '支付方式',
    rate           bigint       null comment '支付方式费率',
    risk_config    json         null comment '风控数据',
    state          tinyint      not null comment '状态',
    create_time    datetime     null comment '创建时间',
    update_by      bigint       null comment '更新人',
    update_by_name varchar(255) null comment '更新人名称',
    create_by      bigint       null comment '创建人',
    create_by_name varchar(255) null comment '创建人名称',
    update_time    datetime     null comment '更新时间',
    interface_code varchar(255) null comment '支付接口编号',
    pay_way_id     bigint       null comment '支付方式id'
)
    comment '商户支付通道表' charset = utf8mb4;

create table pay_order
(
    id                       bigint                     not null comment '支付订单号'
        primary key,
    mch_no                   varchar(64)                not null comment '商户号',
    isv_no                   varchar(64)                null comment '服务商号',
    app_no                   varchar(64)                null comment '应用编号',
    mch_name                 varchar(30)                not null comment '商户名称',
    mch_type                 tinyint(1)                 not null comment '类型',
    mch_order_no             varchar(64)                null comment '商户订单号',
    if_code                  varchar(20)                null comment '支付接口代码',
    way_code                 varchar(200)               null comment '支付方式代码',
    mch_fee_rate             bigint                     not null comment '商户手续费费率快照',
    mch_fee_amount           bigint                     null comment '商户手续费,单位分',
    currency                 varchar(3)   default 'cny' not null comment '三位货币代码,人民币:cny',
    state                    tinyint      default 0     not null comment '支付订单状态',
    trade_type               varchar(10)                null comment '交易类型',
    type                     varchar(10)                null comment '订单类型',
    sub_type                 varchar(10)                null comment '子订单类型',
    trade_mode               varchar(10)                null comment '交易模式',
    notify_state             tinyint      default 0     not null comment '向下游回调状态',
    client_ip                varchar(32)                null comment '客户端IP',
    channel_extra            varchar(512)               null comment '特定渠道发起额外参数',
    channel_user             varchar(64)                null comment '渠道用户标识,如微信openId,支付宝账号',
    channel_order_no         varchar(64)                null comment '渠道订单号',
    refund_state             tinyint      default 0     not null comment '退款状态: 0-未发生实际退款, 1-部分退款, 2-全额退款',
    refund_times             int          default 0     not null comment '退款次数',
    refund_amount            bigint       default 0     not null comment '退款总金额,单位分',
    division_mode            tinyint      default 0     null comment '订单分账模式',
    division_state           tinyint      default 0     null comment '订单分账状态',
    division_last_time       datetime                   null comment '最新分账时间',
    err_code                 varchar(128)               null comment '渠道支付错误码',
    err_msg                  varchar(256)               null comment '渠道支付错误描述',
    ext_param                varchar(128)               null comment '商户扩展参数',
    notify_url               varchar(128) default ''    not null comment '异步通知地址',
    return_url               varchar(128) default ''    null comment '页面跳转地址',
    expired_time             datetime                   null comment '订单失效时间',
    success_time             datetime                   null comment '订单支付成功时间',
    create_time              datetime                   null comment '创建时间',
    update_time              datetime                   null,
    mch_id                   bigint                     null comment '商户id',
    isv_id                   bigint                     null comment '服务商id',
    has_division             bit          default b'0'  not null comment '是否可进行分账',
    app_id                   bigint                     null comment '应用id',
    order_no                 varchar(255)               null comment '订单编号',
    total_amount             bigint                     null comment '订单金额',
    pay_amount               bigint                     null comment '支付金额',
    promotion_amount         bigint                     null comment '营销金额',
    sign_user                varchar(255)               null comment '下游用户',
    channel_result           text                       null comment '支付渠道返回信息',
    pay_agency_channel_order varchar(255)               null comment '支付机构的上游渠道订单',
    wait_settled_amount      bigint                     null comment '待结算金额',
    channel_mch_no           varchar(255)               null comment '渠道商户号',
    division_valid_time      datetime                   null comment '可分账时间',
    channel_origin_id        varchar(255)               null comment '渠道原始订单',
    product_type             varchar(10)                null comment '产品类型',
    subject                  varchar(255)               null comment '商品标题',
    body                     varchar(255)               null comment '商品描述信息',
    constraint Uni_MchNo_MchOrderNo
        unique (mch_no, mch_order_no)
)
    comment '支付订单表' charset = utf8mb4;

create index created_at
    on pay_order (create_time);

create table pay_refund_order
(
    id                      bigint                               not null comment '退款订单号（支付系统生成订单号）',
    pay_order_id            bigint                               not null comment '支付订单号（与pay_order对应）',
    channel_pay_order_no    varchar(64)                          null comment '渠道支付单号（与t_pay_order channel_order_no对应）',
    mch_no                  varchar(64)                          not null comment '商户号',
    isv_no                  varchar(64)                          null comment '服务商号',
    app_no                  varchar(64)                          null comment '应用编号',
    mch_name                varchar(30)                          not null comment '商户名称',
    mch_type                tinyint                              not null comment '类型: 1-普通商户, 2-特约商户(服务商模式)',
    mch_refund_no           varchar(64)                          not null comment '商户退款单号（商户系统的订单号）',
    pay_way_code            varchar(255)                         not null comment '支付方式代码',
    interface_code          varchar(20)                          not null comment '支付接口代码',
    pay_amount              bigint                               not null comment '支付金额,单位分',
    refund_amount           bigint                               not null comment '退款金额,单位分',
    currency                varchar(3) default 'cny'             not null comment '三位货币代码,人民币:cny',
    state                   tinyint    default 0                 not null comment '退款状态:0-订单生成,1-退款中,2-退款成功,3-退款失败,4-退款任务关闭',
    client_ip               varchar(32)                          null comment '客户端IP',
    refund_reason           varchar(256)                         null comment '退款原因',
    channel_order_no        varchar(32)                          null comment '渠道订单号',
    err_code                varchar(128)                         null comment '渠道错误码',
    err_msg                 varchar(2048)                        null comment '渠道错误描述',
    channel_extra           varchar(512)                         null comment '特定渠道发起时额外参数',
    notify_url              varchar(128)                         null comment '通知地址',
    ext_param               varchar(64)                          null comment '扩展参数',
    success_time            datetime                             null comment '订单退款成功时间',
    expired_time            datetime                             null comment '退款失效时间（失效后系统更改为退款任务关闭状态）',
    create_time             datetime   default CURRENT_TIMESTAMP null comment '创建时间',
    update_time             datetime   default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    pay_order_no            varchar(255)                         null comment '支付订单编号',
    mch_id                  bigint                               null comment '商户id',
    isv_id                  bigint                               null comment '服务商id',
    app_id                  bigint                               null comment '应用id',
    interface_id            bigint                               null comment '接口id',
    pay_way_id              bigint                               null comment '支付方式id',
    refund_no               varchar(255)                         null comment '退款流水编号',
    pay_mch_org_order_no    varchar(255)                         null comment '商户支付原始订单编号',
    chanel_result           text                                 null comment '渠道返回信息',
    origin_mch_pay_order_no varchar(255)                         null comment '原始商户支付订单号',
    primary key (id, pay_order_id),
    constraint Uni_MchNo_MchRefundNo
        unique (mch_no, mch_refund_no)
)
    comment '退款订单表' charset = utf8mb4;

create table pay_tong_lian_relevance
(
    id                bigint           not null
        primary key,
    syb_merchant_code varchar(255)     null comment '收银宝账户',
    has_bind_phone    bit default b'0' null comment '是否绑定手机号',
    phone             varchar(15)      null comment '绑定的手机号',
    has_bind_syb      bit default b'0' null comment '是否绑定收银宝',
    mch_id            bigint           null comment '商户id',
    has_contract_sign bit default b'0' null comment '合约是否签订'
)
    comment '通联支付扩展关联信息';

create table pay_way
(
    id             bigint           not null
        primary key,
    pay_code       varchar(30)      null comment '支付方式代码 例如：WX_PAY ALI_PAY',
    pay_name       varchar(50)      null comment '支付方式名称',
    create_time    datetime         null,
    update_time    datetime         null,
    disable        bit default b'0' null comment '是否禁用',
    create_by      bigint           null comment '创建人',
    update_by      bigint           null comment '更新人',
    create_by_name varchar(255)     null comment '创建用户名',
    update_by_name varchar(255)     null comment '更新人',
    paying_agency  varchar(50)      null comment '支付机构代码',
    paying_client  tinyint(1)       null comment '支付可用客户端'
)
    comment '支付方式表';

create table request_interface_record
(
    id           bigint       not null
        primary key,
    create_time  datetime     null,
    update_time  datetime     null,
    biz_type     int          null comment '业务类型',
    interface_id int          null comment '接口号',
    pay_agency   varchar(100) null comment '支付机构',
    request      text         null comment '请求参数',
    response     text         null comment '返回结果',
    success      bit          null comment '是否成功'
);

create table saas_app_pay_relevance
(
    id     bigint not null
        primary key,
    app_id int    null comment 'saas 应用id',
    mch_id bigint null comment '商户id'
)
    comment 'saas应用关联支付中心应用配置';

create table settlement_flow
(
    id          bigint     not null
        primary key,
    request_id  bigint     null comment '结算受理单',
    period_id   bigint     null comment '结算账期ID(当归集后进行填充)',
    amount      decimal    null comment '结算金额',
    fee         decimal    null comment '手续费',
    type        tinyint(1) null comment '结算流水类型',
    state       tinyint(1) null comment '结算流水状态',
    settle_time datetime   null comment '结算时间',
    create_time datetime   null comment '创建时间',
    update_time datetime   null
)
    comment '结算流水单表';

create table settlement_period
(
    id           bigint         not null
        primary key,
    mch_id       bigint         null comment '商户di',
    start_time   datetime       null comment '账期开始时间',
    end_time     datetime       null comment '账期结束时间',
    state        tinyint(1)     null comment '账期状态',
    total_amount decimal(10, 2) null comment '账期总金额'
)
    comment '结算账期';

create table settlement_request
(
    id            bigint         not null
        primary key,
    state         tinyint(1)     null comment '结算状态',
    amount        decimal(10, 2) null comment '交易金额',
    create_time   datetime       null comment '交易确认时间',
    settle_time   datetime       null comment '结算时间',
    fee_snapshot  bigint         null comment '手续费快照',
    order_id      bigint         null comment '交易订单id',
    type          varchar(255)   null comment '结算类型',
    settle_amount decimal(10, 2) null comment '结算金额'
)
    comment '结算受理单';

create table tonglian_return
(
    id          bigint   null,
    result      text     null,
    update_time datetime null,
    create_time datetime null
);

create table trading_flow
(
    id                bigint         not null
        primary key,
    create_time       datetime       null,
    update_time       datetime       null,
    amount            decimal(10, 2) null comment '交易金额',
    channel_cost      bigint         null comment '渠道成本',
    can_settle_amount decimal(10, 2) null comment '可结算金额(根据渠道成本进行计算)',
    trading_type      varchar(20)    null comment '交易类型',
    state             tinyint(1)     null comment '交易流水状态',
    trading_mode      tinyint        null comment '交易模式',
    order_id          bigint         null comment '交易订单id',
    date              date           null comment '交易日期',
    channel_order_id  varchar(255)   null comment '渠道订单号',
    channel_code      varchar(255)   null comment '渠道编号',
    channel_id        bigint         null comment '渠道id',
    trading_time      datetime       null comment '交易时间(完成时间)',
    mch_id            bigint         null comment '商户id',
    origin_order_id   bigint         null comment '原始订单id(退款时才会(存在值)',
    trading_state     tinyint(1)     null comment '交易状态',
    channel_mch_no    varchar(255)   null comment '渠道商户号'
)
    comment '交易流水 注册至对账中心';

create table trading_record
(
    id                  bigint       not null
        primary key,
    create_time         datetime     null,
    update_time         datetime     null,
    trading_amount      bigint       null comment '交易金额',
    trading_type        varchar(20)  null comment '交易类型',
    trading_mode        tinyint      null comment '交易模式',
    order_id            bigint       null comment '交易订单id',
    channel_order_id    varchar(255) null comment '渠道订单号',
    channel_code        varchar(255) null comment '渠道编号',
    channel_id          bigint       null comment '渠道id',
    trading_state       tinyint(1)   null comment '交易状态',
    trading_time        datetime     null comment '交易时间',
    origin_order_id     bigint       null comment '原始订单号',
    channel_orgin_order varchar(255) null comment '渠道原始订单',
    mch_id              bigint       null comment '商户id',
    mch_no              varchar(255) null comment '商户编号'
)
    comment '系统交易记录 主要用于 与渠道对账';

