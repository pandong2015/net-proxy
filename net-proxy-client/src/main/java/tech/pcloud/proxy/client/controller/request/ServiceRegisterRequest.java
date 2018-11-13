package tech.pcloud.proxy.client.controller.request;

import lombok.Data;
import tech.pcloud.proxy.core.model.Node;
import tech.pcloud.proxy.core.model.Service;

@Data
public class ServiceRegisterRequest {
    private Service service;
    private Node server;
}
