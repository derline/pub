package sjj.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sjj.service.UserService;

import javax.annotation.Resource;

@RestController
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping("aa")
    public Object login() {
        return userService.login();
    }

}