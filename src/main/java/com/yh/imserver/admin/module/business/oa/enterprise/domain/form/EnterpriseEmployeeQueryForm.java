package com.yh.imserver.admin.module.business.oa.enterprise.domain.form;

import com.yh.imserver.base.common.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 查询企业员工
 *
 * @Author 1024创新实验室: 开云
 * @Date 2021-12-20 21:06:49
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright <a href="https://1024lab.net">1024创新实验室</a>
 */
@Data
public class EnterpriseEmployeeQueryForm extends PageParam {

    @Schema(description = "搜索词")
    private String keyword;

    @Schema(description = "公司Id")
    @NotNull(message = "公司id 不能为空")
    private Long enterpriseId;

    @Schema(description = "删除标识", hidden = true)
    private Boolean deletedFlag;

}
