package com.boot.dubbo.gson;

import lombok.Data;

/**
 * @author : yangqi
 * @email : lukewei@mockuai.com
 * @description :
 * @since : 2020/7/4 9:44 上午
 */
@Data
public class PersonConfig {

    @ApolloValue(key = "apollo.value")
    private String apolloValue;
}
