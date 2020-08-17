package com.boot.dubbo.mvc.controller;

import com.alibaba.fastjson.JSON;
import com.boot.dubbo.api.entity.BrandIncomeDailyStatistics;
import com.boot.dubbo.api.mapper.IBrandIncomeDailyStatisticsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author : yangqi
 * @email : lukewei@mockuai.com
 * @description :
 * @since : 2020-08-17 14:17
 */
@Slf4j
@RestController
public class BrandIncomeDailyStatisticsController {

    @Resource
    private IBrandIncomeDailyStatisticsMapper brandIncomeDailyStatisticsMapper;

    @GetMapping("/brand/income/daily")
    public BrandIncomeDailyStatistics getById(Long id) {

        BrandIncomeDailyStatistics brandIncomeDailyStatistics = this.brandIncomeDailyStatisticsMapper.selectById(id);
        log.info("brandIncomeDailyStatistics {}", JSON.toJSONString(brandIncomeDailyStatistics));
        return brandIncomeDailyStatistics;
    }

    @GetMapping("/brand/income/daily/add")
    public BrandIncomeDailyStatistics add() {

        BrandIncomeDailyStatistics brandIncomeDailyStatistics = this.brandIncomeDailyStatisticsMapper.selectById(19L);
        BrandIncomeDailyStatistics entity = new BrandIncomeDailyStatistics();
        BeanUtils.copyProperties(brandIncomeDailyStatistics, entity);
        Date date = new Date(1597593599712L);
        entity.setGmtCreated(date);
        entity.setGmtModified(date);
        entity.setId(null);
        this.brandIncomeDailyStatisticsMapper.insert(entity);

        entity.setGmtCreated(new Date());
        entity.setGmtModified(new Date());
        entity.setId(null);
        this.brandIncomeDailyStatisticsMapper.insert(entity);
        log.info("brandIncomeDailyStatistics {}", JSON.toJSONString(brandIncomeDailyStatistics));
        return entity;
    }
}
