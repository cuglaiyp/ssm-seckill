package com.lai.app.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 暴露秒杀接口地址dto
 */

@Builder
@Getter
@Setter
public class Exposer {

    private boolean exposed;

    private String mds;

    private long seckillId;

    // 系统当前时间（毫秒）
    private long now;

    private long start;

    private long end;


}
