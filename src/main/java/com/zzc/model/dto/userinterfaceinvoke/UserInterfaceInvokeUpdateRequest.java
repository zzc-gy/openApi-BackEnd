package com.zzc.model.dto.userinterfaceinvoke;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 更新请求
 *
 * @author zhangzhichao
 */
@Data
public class UserInterfaceInvokeUpdateRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 接口ID
     */
    private Long interfaceId;

    /**
     * 总调用次数
     */
    private Integer totalNum;

    /**
     * 剩余调用次数
     */
    private Integer leftNum;

    /**
     * 状态 0 关闭 1开启
     */
    private Integer status;


    /**
     * 更新时间
     */
    private Date updateTime;


}