package tech.pcloud.nnts.client.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.pcloud.framework.springboot.web.controller.BasicController;
import tech.pcloud.framework.utility.http.RestResult;
import tech.pcloud.nnts.core.model.Node;

@Slf4j
@RestController
@RequestMapping("/server")
public class ServerController extends BasicController {
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public RestResult<Node> add(@RequestBody Node server) {
        return fail(404, "fail", server);
    }
}
