package com.zzc.providerservice;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzc.model.entity.User;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * UserService
 *
 * @Author zzc
 * @Date 2023/5/8 18:27
 */
@DubboService
public class UserService implements com.zzc.provider.UserService {

    @Resource
    private com.zzc.service.UserService userServiceImpl;

    @Override
    public User getUserInvokeInfo(String userAccount) {
        QueryWrapper<User> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("userAccount", userAccount);
        return userServiceImpl.getOne(objectQueryWrapper);
    }

}
