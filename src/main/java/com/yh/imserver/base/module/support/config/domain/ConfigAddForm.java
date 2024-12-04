package com.yh.imserver.base.module.support.config.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 添加配置表单
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-03-14 20:46:27
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright <a href="https://1024lab.net">1024创新实验室</a>
 */
@Data
public class ConfigAddForm {

    @Schema(description = "参数key")
    @NotBlank(message = "参数key不能为空")
    private String configKey;

    @Schema(description = "参数的值")
    @NotBlank(message = "参数的值不能为空")
    private String configValue;

    @Schema(description = "参数名称")
    @NotBlank(message = "参数名称不能为空")
    private String configName;

    @Schema(description = "备注")
    private String remark;
}
