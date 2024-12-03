package com.yh.imserver.base.module.support.dict.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 字典
 *
 * @Author 1024创新实验室: 罗伊
 * @Date 2022/5/26 19:40:55
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright <a href="https://1024lab.net">1024创新实验室</a>
 */
@Data
public class DictKeyAddForm {

    @Schema(description = "编码")
    @NotBlank(message = "编码不能为空")
    private String keyCode;

    @Schema(description = "名称")
    @NotBlank(message = "名称不能为空")
    private String keyName;

    @Schema(description = "备注")
    private String remark;
}