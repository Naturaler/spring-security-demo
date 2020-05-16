package com.yrx.springsecurity.ssoserver.model;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by r.x on 2020/5/16.
 */
@Getter
@AllArgsConstructor
public enum UserEnum {
    ZhangSan("张三", "10086", "admin"),
    LaoWang("老王", "1008611", "admin"),
    ;

    private String name;
    private String idCard;
    private String role;

    public static UserEnum authenticate(String name, String idCard) {
        for (UserEnum userEnum : UserEnum.values()) {
            if (userEnum.getName().equals(name) && userEnum.getIdCard().equals(idCard)) {
                return userEnum;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("name", getName());
        json.put("idCard", getIdCard());
        json.put("role", getRole());
        return json.toJSONString();
    }
}
