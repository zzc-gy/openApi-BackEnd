package com.zzc.providerservice;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzc.model.entity.InterfaceInfo;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * InterfaceInfoService
 *
 * @Author zzc
 * @Date 2023/5/8 20:12
 */
@DubboService
public class InterfaceInfoService implements com.zzc.provider.InterfaceInfoService {

    @Resource
    private com.zzc.service.InterfaceInfoService interfaceInfoServiceImpl;

    @Override
    public InterfaceInfo getByUrlAndMethod(String url, String method) {
        QueryWrapper<InterfaceInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("url", url);
        wrapper.eq("method", method);
        return interfaceInfoServiceImpl.getOne(wrapper);
    }
}
