package tech.pcloud.proxy.client.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.pcloud.framework.springboot.web.controller.BasicController;
import tech.pcloud.framework.utility.http.RestResult;
import tech.pcloud.proxy.client.controller.request.ServiceRegisterRequest;
import tech.pcloud.proxy.client.service.ServicesService;
import tech.pcloud.proxy.core.model.Service;

@Slf4j
@RestController
@RequestMapping("/service")
public class ServicesController extends BasicController {
    @Autowired
    private ServicesService servicesService;

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public RestResult<Service> registerService(@RequestBody ServiceRegisterRequest register) {
        return success(servicesService.register(register.getService(), register.getServer()));
    }
}
