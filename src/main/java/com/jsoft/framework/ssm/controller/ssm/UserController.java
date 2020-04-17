package com.jsoft.framework.ssm.controller.ssm;

import com.jsoft.framework.ssm.service.ssm.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

}
