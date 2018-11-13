package tech.pcloud.proxy.client.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.pcloud.framework.springboot.web.controller.BasicController;

@Slf4j
@RestController
public class IndexController extends BasicController {
    @RequestMapping("/")
    public String index() {
        return "welcome!";
    }
}
