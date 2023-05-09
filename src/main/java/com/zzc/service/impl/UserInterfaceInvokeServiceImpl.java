package com.zzc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzc.mapper.UserInterfaceInvokeMapper;
import com.zzc.model.entity.UserInterfaceInvoke;
import com.zzc.service.UserInterfaceInvokeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zhangzhichao
 * @description 针对表【interface_info(接口信息表)】的数据库操作Service实现
 * @createDate 2023-04-29 12:59:38
 */
@Service("userInterfaceInvokeServiceImpl")
@Slf4j
public class UserInterfaceInvokeServiceImpl extends ServiceImpl<UserInterfaceInvokeMapper, UserInterfaceInvoke> implements UserInterfaceInvokeService {

}




