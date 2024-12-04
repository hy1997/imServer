package com.yh.imserver.admin.module.system.employee.domain.form;

import com.yh.imserver.base.common.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * 员工列表
 *
 * @Author 1024创新实验室: 开云
 * @Date 2021-12-20 21:06:49
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright <a href="https://1024lab.net">1024创新实验室</a>
 */
@Data
public class EmployeeQueryForm extends PageParam {

    @Schema(description = "搜索词")
    private String keyword;

    @Schema(description = "部门id")
    private Long departmentId;

    @Schema(description = "是否禁用")
    private Boolean disabledFlag;

    @Schema(description = "员工id集合")
    @Size(max = 99, message = "最多查询99个员工")
    private List<Long> employeeIdList;

    @Schema(description = "删除标识", hidden = true)
    private Boolean deletedFlag;

}
