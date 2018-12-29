package com.third.enterprise.service.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:operation-token.properties")
@ConfigurationProperties(prefix = "operation.token")
public class OperationToken {

    private String clientId;

    private String base64Secret;

    private String header;

    private String tokenHead;

    private String name;

    private Integer expiresSecond;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getBase64Secret() {
        return base64Secret;
    }

    public void setBase64Secret(String base64Secret) {
        this.base64Secret = base64Secret;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getTokenHead() {
        return tokenHead;
    }

    public void setTokenHead(String tokenHead) {
        this.tokenHead = tokenHead;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getExpiresSecond() {
        return expiresSecond;
    }

    public void setExpiresSecond(Integer expiresSecond) {
        this.expiresSecond = expiresSecond;
    }
}
