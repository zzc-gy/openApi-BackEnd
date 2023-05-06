//package com.zzc.config;
//
//import com.zzc.web.UserParam;
//import javafx.fxml.Initializable;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.method.support.HandlerMethodArgumentResolver;
//import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
//import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
//
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.ResourceBundle;
//
///**
// * MyArgumentResolverAutoConfiguration
// *
// * @Author zzc
// * @Date 2023/5/2 15:36
// */
//@Configuration
//public class MyArgumentResolverAutoConfiguration implements Initializable {
//
//    @Autowired
//    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        List<HandlerMethodArgumentResolver> argumentResolvers = requestMappingHandlerAdapter.getArgumentResolvers();
//        List<HandlerMethodArgumentResolver> customArgumentResolvers = new ArrayList<>();
//
//        for (HandlerMethodArgumentResolver argumentResolver : argumentResolvers) {
//            if (argumentResolver instanceof RequestResponseBodyMethodProcessor) {
//                customArgumentResolvers.add(new UserParam());
//            }
//            customArgumentResolvers.add(argumentResolver);
//        }
//
//        requestMappingHandlerAdapter.setArgumentResolvers(customArgumentResolvers);
//    }
//}
