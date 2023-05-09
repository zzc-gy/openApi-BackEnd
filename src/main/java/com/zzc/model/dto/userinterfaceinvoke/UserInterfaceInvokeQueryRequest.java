package com.zzc.model.dto.userinterfaceinvoke;

import com.zzc.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询请求
 *
 * @author zhangzhichao
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserInterfaceInvokeQueryRequest extends PageRequest implements Serializable {

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
     * 创建时间
     */
    private Date createTime;

}