package com.lai.app.entity;

import lombok.Data;

import java.util.Date;

/**
 * 为什么没有开启驼峰转换成下划线的功能还不是很了解
 */
@Data
public class Seckill {
    private long seckillId;
    private String name;
    private int number;
    private Date startTime;
    private Date endTime;
    private Date createTime;
}
