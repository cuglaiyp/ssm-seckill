package com.lai.app.dto;

import com.lai.app.entity.SuccessKilled;
import com.lai.app.enums.SeckillStateEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 执行秒杀后的结果
 */
@Builder
@Setter
@Getter
public class SeckillExecution {
    private long seckillId;

    private SeckillStateEnum seckillStateEnum;
    // 秒杀成功对象
    private SuccessKilled successKilled;
}
