package com.zzc.providerservice;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zzc.common.ErrorCode;
import com.zzc.exception.BusinessException;
import com.zzc.model.entity.UserInterfaceInvoke;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author zhangzhichao
 * @description 针对表【user_interface_invoke(用户接口调用次数表)】的数据库操作Service实现
 * @createDate 2023-05-06 12:06:08
 */
@DubboService
public class UserInterfaceInvokeService implements com.zzc.provider.UserInterfaceInvokeService {

    @Resource
    private com.zzc.service.UserInterfaceInvokeService userInterfaceInvokeServiceImpl;

    @Override
    public void invokeAfter(long userId, long interfaceId) {
        if (userId <= 0 || interfaceId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<UserInterfaceInvoke> wrapper = new QueryWrapper<>();
        wrapper.eq("userId", userId);
        wrapper.eq("interfaceId", interfaceId);
        UserInterfaceInvoke one = userInterfaceInvokeServiceImpl.getOne(wrapper);
        if (one == null) {
            UserInterfaceInvoke userInterfaceInvoke = new UserInterfaceInvoke();
            userInterfaceInvoke.setInterfaceId(interfaceId);
            userInterfaceInvoke.setUserId(userId);
            userInterfaceInvoke.setCreateTime(System.currentTimeMillis() / 1000L);
            userInterfaceInvoke.setUpdateTime(0L);
            userInterfaceInvoke.setLeftNum(100);
            userInterfaceInvoke.setTotalNum(0);
            userInterfaceInvokeServiceImpl.save(userInterfaceInvoke);
        }
        UpdateWrapper<UserInterfaceInvoke> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("userId", userId);
        updateWrapper.eq("interfaceId", interfaceId);
        updateWrapper.setSql("leftNum = leftNum - 1, totalNum = totalNum + 1");
        userInterfaceInvokeServiceImpl.update(updateWrapper);
    }
}




