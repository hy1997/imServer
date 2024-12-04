package com.yh.imserver.base.module.support.job.api.domain;

import com.yh.imserver.base.common.swagger.SchemaEnum;
import com.yh.imserver.base.common.validator.enumeration.CheckEnum;
import com.yh.imserver.base.module.support.job.constant.SmartJobTriggerTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 定时任务 更新
 *
 * @author huke
 * @date 2024/6/17 21:30
 */
@Data
public class SmartJobUpdateForm {

    @Schema(description = "任务id")
    @NotNull(message = "任务id不能为空")
    private Integer jobId;

    @Schema(description = "任务名称")
    @NotBlank(message = "任务名称不能为空")
    private String jobName;

    @Schema(description = "任务执行类")
    @NotBlank(message = "任务执行类不能为空")
    private String jobClass;

    @SchemaEnum(desc = "触发类型", value = SmartJobTriggerTypeEnum.class)
    @CheckEnum(value = SmartJobTriggerTypeEnum.class, required = true, message = "触发类型错误")
    private String triggerType;

    @Schema(description = "触发配置")
    @NotBlank(message = "触发配置不能为空")
    private String triggerValue;

    @Schema(description = "定时任务参数|可选")
    private String param;

    @Schema(description = "是否开启")
    @NotNull(message = "是否开启不能为空")
    private Boolean enabledFlag;

    @Schema(description = "备注")
    private String remark;

    @NotNull(message = "排序不能为空")
    @Schema(description = "排序")
    private Integer sort;

    @Schema(hidden = true)
    private String updateName;
}
