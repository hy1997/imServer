package com.yh.imserver.server;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.yh.imserver.dto.LoginDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@CrossOrigin
public class LoginController {

    @PostMapping("login")
    public SaResult doLogin(@RequestBody LoginDto loginDto) {
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
        if("admin".equals(loginDto.getUsername()) && "admin".equals(loginDto.getPassword())) {
            StpUtil.login(111);

            return SaResult.ok("登录成功").setData(StpUtil.getTokenInfo());
        }
        return SaResult.error("登录失败");
    }


}
