package com.yh.imserver.base.module.support.job.api.domain;

import com.yh.imserver.base.common.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

/**
 * 定时任务-执行记录 分页查询
 *
 * @author huke
 * @date 2024/6/17 20:50
 */
@Data
public class SmartJobLogQueryForm extends PageParam {

    @Schema(description = "搜索词|可选")
     private String searchWord;

    @Schema(description = "任务id|可选")
    private Integer jobId;

    @Schema(description = "是否成功|可选")
    private Boolean successFlag;

    @Schema(description = "开始时间|可选", example = "2024-06-06")
    private LocalDate startTime;

    @Schema(description = "截止时间|可选", example = "2025-10-15")
    private LocalDate endTime;
}
