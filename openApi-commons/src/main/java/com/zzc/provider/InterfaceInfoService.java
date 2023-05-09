package com.zzc.provider;

import com.zzc.model.entity.InterfaceInfo;

/**
 * InterfaceInfoService
 *
 * @Author zzc
 * @Date 2023/5/8 20:09
 */
public interface InterfaceInfoService {

    InterfaceInfo getByUrlAndMethod(String url, String method);

}
