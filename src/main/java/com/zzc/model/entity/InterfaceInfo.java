package com.zzc.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

/**
 * 接口信息表
 * @TableName interface_info
 */
@TableName(value ="interface_info")
@Data
public class InterfaceInfo implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 创建人ID
     */
    private Long createUserId;

    /**
     * 接口名称
     */
    private String name;

    /**
     * 描述
     */
    @TableField(value = "`describe`")
    private String describe;

    /**
     * 状态 0 关闭 1开启
     */
    private Integer status;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 请求类型
     */
    private String method;

    /**
     * 请求头信息
     */
    private String requestHeader;

    /**
     * 响应头信息
     */
    private String responseHeader;

    /**
     * 
     */
    private Long createTime;

    /**
     * 
     */
    private Long updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDel;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 响应参数
     */
    private String responseParam;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}