package com.zhihao.util;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;


@Data
public class JWTToken implements AuthenticationToken {

    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    public JWTToken() {
    }

    @Override
    public Object getPrincipal() {
        return this.token;
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }

}
