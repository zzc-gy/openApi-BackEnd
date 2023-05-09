package com.zzc.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzc.client.UserClient;
import com.zzc.common.ErrorCode;
import com.zzc.exception.BusinessException;
import com.zzc.mapper.InterfaceInfoMapper;
import com.zzc.model.dto.interfaceinfo.InterfaceInfoInvokeRequest;
import com.zzc.model.entity.InterfaceInfo;
import com.zzc.model.entity.User;
import com.zzc.model.enums.InterfaceStatus;
import com.zzc.service.InterfaceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author zhangzhichao
 * @description 针对表【interface_info(接口信息表)】的数据库操作Service实现
 * @createDate 2023-04-29 12:59:38
 */
@Service("interfaceInfoServiceImpl")
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo> implements InterfaceInfoService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String name = interfaceInfo.getName();
        if (add) {
            if (StringUtils.isBlank(name)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        if (StringUtils.isNotBlank(name) && name.length() > 100) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口名过长");
        }
    }

    @Override
    public Object invoke(InterfaceInfoInvokeRequest request, User user) {
        InterfaceInfo interfaceInfo = this.getById(request.getId());
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        if (interfaceInfo.getStatus() == InterfaceStatus.OFFLINE.getValue()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口已关闭");
        }

        String key = user.getUserAccount() + interfaceInfo.getId();

        //Object o = redisTemplate.boundValueOps(key).get();
        //if (o != null && (int) o != 0){
        //    throw new BusinessException(ErrorCode.SYSTEM_ERROR, "限定时间内调用次数超限");
        //}

        UserClient userClient = new UserClient(user.getUserAccount(), user.getSecretKey());

        redisTemplate.boundValueOps(key).set(1, 10000);
        return userClient.getUserByBody(user);
    }
}




