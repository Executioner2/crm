package com.YoRHa.crm.settings.domain;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-01-30 15:11
 * Versions:1.0.0
 * Description:
 */
public class DicType {
    private String code;
    private String name;
    private String description;

    @Override
    public String toString() {
        return "DicType{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
