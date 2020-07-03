package com.boot.dubbo.gson;

import lombok.Data;

@Data
public class PersonConfig {

    @ApolloValue(key = "apollo.value")
    private String ApolloValue;
}
