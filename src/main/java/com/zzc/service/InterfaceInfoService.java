package com.zzc.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zzc.model.entity.InterfaceInfo;

/**
* @author zhangzhichao
* @description 针对表【interface_info(接口信息表)】的数据库操作Service
* @createDate 2023-04-29 12:59:38
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);

}
