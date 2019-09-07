package com.boot.dubbo.gson;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

@SpringBootApplication
public class GsonApplication {

    public static void main(String[] args) {
        SpringApplication.run(GsonApplication.class, args);
    }

    @Bean
    public HttpMessageConverters messageConverters() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        Gson gson = gsonBuilder.create();
        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter(gson);
        return new HttpMessageConverters(gsonHttpMessageConverter);
    }
}
