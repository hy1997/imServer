package com.yh.imserver.base.module.support.config;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yh.imserver.base.common.controller.SupportBaseController;
import com.yh.imserver.base.common.domain.ResponseDTO;
import com.yh.imserver.base.constant.SwaggerTagConst;
import com.yh.imserver.base.module.support.config.domain.ConfigVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;

/**
 * 配置
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-03-14 20:46:27
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Tag(name = SwaggerTagConst.Support.CONFIG)
@RestController
public class ConfigController extends SupportBaseController {

    @Resource
    private ConfigService configService;

    @Operation(summary = "查询配置详情 @author 卓大")
    @GetMapping("/config/queryByKey")
    public ResponseDTO<ConfigVO> queryByKey(@RequestParam String configKey) throws InvocationTargetException, IllegalAccessException {
        return ResponseDTO.ok(configService.getConfig(configKey));
    }

}