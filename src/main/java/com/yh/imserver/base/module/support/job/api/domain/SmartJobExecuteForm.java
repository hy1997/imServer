package com.yh.imserver.base.module.support.job.api.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 定时任务-手动执行
 *
 * @author huke
 * @date 2024/6/18 20:30
 */
@Data
public class SmartJobExecuteForm {

    @Schema(description = "任务id")
    @NotNull(message = "任务id不能为空")
    private Integer jobId;

    @Schema(description = "定时任务参数|可选")
    private String param;

    @Schema(hidden = true)
    private String updateName;
}
