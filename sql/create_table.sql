# 建表脚本
# @author <a href="https://github.com/liyupi">程序员zzc</a>
# @from <a href="https://yupi.icu">编程导航知识星球</a>

-- 切换库
use openApi;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    unionId      varchar(256)                           null comment '微信开放平台id',
    mpOpenId     varchar(256)                           null comment '公众号openId',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    index idx_unionId (unionId)
) comment '用户' collate = utf8mb4_unicode_ci;

-- 接口信息表
CREATE TABLE `interface_info`
(
    `id`             bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `createUserId`   bigint(20)          NOT NULL COMMENT '创建人ID',
    `name`           varchar(100)        NOT NULL COMMENT '接口名称',
    `describe`       varchar(255)        NOT NULL COMMENT '描述',
    `status`         tinyint(4)          NOT NULL DEFAULT '0' COMMENT '状态 0 关闭 1开启',
    `url`            varchar(255)        NOT NULL COMMENT '请求地址',
    `method`         varchar(10)         NOT NULL COMMENT '请求类型',
    `requestHeader`  varchar(100)        NOT NULL COMMENT '请求头信息',
    `responseHeader` varchar(100)        NOT NULL COMMENT '响应头信息',
    `createTime`     bigint(20) unsigned NOT NULL,
    `updateTime`     bigint(20) unsigned NOT NULL,
    `isDel`          tinyint(4)          NOT NULL DEFAULT '0' COMMENT '是否删除 0未删除 1删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='接口信息表';
