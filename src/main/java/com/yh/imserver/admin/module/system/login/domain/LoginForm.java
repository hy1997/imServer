package com.yh.imserver.admin.module.system.login.domain;

import com.yh.imserver.base.common.swagger.SchemaEnum;
import com.yh.imserver.base.common.validator.enumeration.CheckEnum;
import com.yh.imserver.base.constant.LoginDeviceEnum;
import com.yh.imserver.base.module.support.captcha.domain.CaptchaForm;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 员工登录
 *
 * @Author 1024创新实验室: 开云
 * @Date 2021-12-19 11:49:45
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright <a href="https://1024lab.net">1024创新实验室</a>
 */
@Data
public class LoginForm extends CaptchaForm {

    @Schema(description = "登录账号")
    @NotBlank(message = "登录账号不能为空")
    private String loginName;

    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @SchemaEnum(desc = "登录终端", value = LoginDeviceEnum.class)
    @CheckEnum(value = LoginDeviceEnum.class, required = true, message = "此终端不允许登录")
    private Integer loginDevice;

    @Schema(description = "邮箱验证码")
    private String emailCode;
}
