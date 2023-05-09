package com.zzc.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * @Author zzc
 * @Date 2023/4/30 20:28
 */
public class SignUtil {

    public static String getSign(String body, String secretKey){
        Digester digester = new Digester(DigestAlgorithm.SHA256);
        String content = body + "./" + secretKey;
        return digester.digestHex(content);
    }

}
