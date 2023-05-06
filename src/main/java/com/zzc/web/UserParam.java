package com.zzc.web;

import com.zzc.model.entity.User;
import com.zzc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户信息解析
 *
 * @Author zzc
 * @Date 2023/5/2 15:10
 */
@Component
public class UserParam implements HandlerMethodArgumentResolver {

    @Autowired
    private UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(User.class);
    }


    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        if (parameter.getParameterType().equals(User.class)) {
            HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
            return userService.getLoginUser(request);
        } else {
            return null;
        }
    }
}
