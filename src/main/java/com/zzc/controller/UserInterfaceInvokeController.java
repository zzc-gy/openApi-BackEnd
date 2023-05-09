package com.zzc.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzc.annotation.AuthCheck;
import com.zzc.client.UserClient;
import com.zzc.common.BaseResponse;
import com.zzc.common.DeleteRequest;
import com.zzc.common.ErrorCode;
import com.zzc.common.ResultUtils;
import com.zzc.constant.CommonConstant;
import com.zzc.constant.UserConstant;
import com.zzc.exception.BusinessException;
import com.zzc.exception.ThrowUtils;
import com.zzc.model.dto.interfaceinfo.InterfaceInfoAddRequest;
import com.zzc.model.dto.interfaceinfo.InterfaceInfoQueryRequest;
import com.zzc.model.dto.interfaceinfo.InterfaceInfoUpdateRequest;
import com.zzc.model.entity.User;
import com.zzc.model.entity.UserInterfaceInvoke;
import com.zzc.service.UserInterfaceInvokeService;
import com.zzc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户接口调用
 *
 * @author zhangzhichao
 */
@RestController
@RequestMapping("/userInterfaceInvoke")
@Slf4j
public class UserInterfaceInvokeController {

    @Resource
    private UserInterfaceInvokeService userInterfaceInvokeServiceImpl;

    @Resource
    private UserService userServiceImpl;

    @Resource
    private UserClient userClient;

    /**
     * 创建
     *
     * @param interfaceInfoAddRequest 参数
     * @param request                 请求
     * @return 返回
     */
    @PostMapping("/add")
    public BaseResponse<Long> addInterfaceInfo(@RequestBody InterfaceInfoAddRequest interfaceInfoAddRequest, HttpServletRequest request, @ApiIgnore User user) throws Exception {
        if (interfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        UserInterfaceInvoke userInterfaceInvoke = new UserInterfaceInvoke();
        BeanUtils.copyProperties(interfaceInfoAddRequest, userInterfaceInvoke);
        boolean result = userInterfaceInvokeServiceImpl.save(userInterfaceInvoke);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newInterfaceInfoId = userInterfaceInvoke.getId();
        return ResultUtils.success(newInterfaceInfoId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request, @ApiIgnore User user) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = deleteRequest.getId();
        // 判断是否存在
        UserInterfaceInvoke oldInterfaceInfo = userInterfaceInvokeServiceImpl.getById(id);
        ThrowUtils.throwIf(oldInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        boolean b = userInterfaceInvokeServiceImpl.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 分页获取列表
     *
     * @param interfaceInfoQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<UserInterfaceInvoke>> listInterfaceInfoByPage(InterfaceInfoQueryRequest interfaceInfoQueryRequest, HttpServletRequest request, @ApiIgnore User user) {
        if (interfaceInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInvoke interfaceInfoQuery = new UserInterfaceInvoke();
        BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfoQuery);
        long current = interfaceInfoQueryRequest.getCurrent();
        long size = interfaceInfoQueryRequest.getPageSize();
        String sortField = interfaceInfoQueryRequest.getSortField();
        String sortOrder = interfaceInfoQueryRequest.getSortOrder();
        // description 需支持模糊搜索
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<UserInterfaceInvoke> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<UserInterfaceInvoke> interfaceInfoPage = userInterfaceInvokeServiceImpl.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(interfaceInfoPage);
    }


    /**
     * 编辑（用户）
     *
     * @param interfaceInfoUpdateRequest interfaceInfoUpdateRequest
     * @param request                    request
     * @return return
     */
    @PostMapping("/edit")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> editInterfaceInfo(@RequestBody InterfaceInfoUpdateRequest interfaceInfoUpdateRequest, HttpServletRequest request, @ApiIgnore User user) {
        if (interfaceInfoUpdateRequest == null || interfaceInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInvoke UserInterfaceInvoke = new UserInterfaceInvoke();
        BeanUtils.copyProperties(interfaceInfoUpdateRequest, UserInterfaceInvoke);
        // 参数校验
        User loginUser = userServiceImpl.getLoginUser(request);
        long id = interfaceInfoUpdateRequest.getId();
        // 判断是否存在
        UserInterfaceInvoke oldInterfaceInfo = userInterfaceInvokeServiceImpl.getById(id);
        ThrowUtils.throwIf(oldInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = userInterfaceInvokeServiceImpl.updateById(UserInterfaceInvoke);
        return ResultUtils.success(result);
    }


    /**
     * 根据 id 获取
     *
     * @param id id
     * @return  返回
     */
    @GetMapping("/get")
    public BaseResponse<UserInterfaceInvoke> getInterfaceById(long id, @ApiIgnore User user) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInvoke UserInterfaceInvoke = userInterfaceInvokeServiceImpl.getById(id);
        if (UserInterfaceInvoke == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(UserInterfaceInvoke);
    }


}
