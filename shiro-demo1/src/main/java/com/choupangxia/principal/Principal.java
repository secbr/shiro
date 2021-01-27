package com.choupangxia.principal;

import java.io.Serializable;

/**
 * @author sec
 * @version 1.0
 * @date 2021/1/27
 **/
public class Principal implements Serializable {

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String mobile;

    public Principal(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
