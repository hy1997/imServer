package com.yh.imserver.admin.module.business.oa.enterprise.domain.form;

import com.yh.imserver.base.common.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

/**
 * OA企业模块分页查询
 *
 * @Author 1024创新实验室: 开云
 * @Date 2022/7/28 20:37:15
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright <a href="https://1024lab.net">1024创新实验室</a>
 */
@Data
public class EnterpriseQueryForm extends PageParam {

    @Schema(description = "关键字")
    private String keywords;

    @Schema(description = "开始时间")
    private LocalDate startTime;

    @Schema(description = "结束时间")
    private LocalDate endTime;

    @Schema(description = "禁用状态")
    private Boolean disabledFlag;

    @Schema(description = "删除状态", hidden = true)
    private Boolean deletedFlag;

}
