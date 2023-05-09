package com.zzc.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.zzc.model.entity.User;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

import static com.zzc.utils.SignUtil.getSign;

/**
 * UserClient
 *
 * @Author zzc
 * @Date 2023/4/30 19:50
 */
@Data
public class UserClient {

    private String accessKey;

    private String secretKey;

    public static final String GATEWAY_HOST = "http://localhost:9621";

    public UserClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getUserByGet(String name) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        return HttpUtil.get(GATEWAY_HOST + "/api/user/get", paramMap);
    }

    public String getUserByPost(String name) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        return HttpUtil.post(GATEWAY_HOST + "/api/user/post", paramMap);
    }

    public String getUserByBody(User user) {
        String jsonStr = JSONUtil.toJsonStr(user);
        return HttpRequest.post(GATEWAY_HOST + "/api/user/userBody").addHeaders(getHeader(jsonStr)).body(jsonStr).execute().body();
    }

    private Map<String, String> getHeader(String body) {
        HashMap<String, String> header = new HashMap<>();
        header.put("accessKey", accessKey);
        header.put("nonce", RandomUtil.randomString(4));
        header.put("body", body);
        header.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        header.put("sign", getSign(body, secretKey));
        return header;
    }

}
