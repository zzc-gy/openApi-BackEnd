package com.zzc.model.dto.interfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 调用
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class InterfaceInfoInvokeRequest implements Serializable {

    /**
     *
     */
    private Long id;

    /**
     * 请求参数
     */
    private String userRequestParam;

}