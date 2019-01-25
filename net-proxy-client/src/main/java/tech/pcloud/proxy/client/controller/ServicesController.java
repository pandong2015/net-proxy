package tech.pcloud.proxy.client.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tech.pcloud.framework.springboot.web.controller.BasicController;
import tech.pcloud.framework.utility.http.RestResult;
import tech.pcloud.proxy.client.controller.request.ServiceRegisterRequest;
import tech.pcloud.proxy.client.service.ServicesService;
import tech.pcloud.proxy.core.model.Service;

import java.util.List;

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

    @PostMapping(value = "/stop", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public RestResult stop(@RequestBody Service service) {
        servicesService.delete(service);
        return success();
    }

    @GetMapping(value = "/client/services", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult<List<Service>> listRegisterServices() {
        return success(servicesService.selectAll());
    }

    @GetMapping(value = "/server/services", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult<List<Service>> listServerServices() {
//        return success(servicesService.selectAll());
        return null;
    }
}
