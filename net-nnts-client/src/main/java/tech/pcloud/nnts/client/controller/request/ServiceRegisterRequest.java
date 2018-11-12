package tech.pcloud.nnts.client.controller.request;

import lombok.Data;
import tech.pcloud.nnts.core.model.Node;
import tech.pcloud.nnts.core.model.Service;

@Data
public class ServiceRegisterRequest {
    private Service service;
    private Node server;
}
