# payment-center

## 项目结构

payment-center
|--- pay // 提供给第三方stater 工具包
|--- payment-center-api // 多模块调用层
|--- payment-center-common 支付中心通用模块

## 项目结构

    controller 控制器进行请求接入以及处理
    service 具体逻辑处理
    manager 通用逻辑处理 及 中间件处理层 如 MQManager
    cache 通用缓存层
    dao 数据操作 如操作 orderMapper 操作redis orderRedismapper 操作 mongo  orderMongoMapper


## 系统模块说明
### payment-center-accounting 财务中心
```text
|--- 账务核心 
  ｜--- 记账
  ｜--- 对账
|--- 会记中心
|--- 清结算中心
```
### payment-center-channel 支付渠道网关
```text
|--- 支付渠道接入
```
### 收银支付系统
```text
```
### 收单结算系统
### 会员中心

### 商户中心