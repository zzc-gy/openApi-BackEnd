package com.zzc.model.dto.interfaceinfo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.zzc.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InterfaceInfoQueryRequest extends PageRequest implements Serializable {

    /**
     *
     */
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
}