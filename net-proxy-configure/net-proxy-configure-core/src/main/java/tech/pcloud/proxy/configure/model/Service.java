package tech.pcloud.proxy.configure.model;

import lombok.Data;
import tech.pcloud.proxy.configure.service.ClientSelector;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName Service
 * @Author pandong
 * @Date 2019/1/25 13:31
 **/
@Data
public class Service extends Node {
    private String name;
    private Status status;
    private Class<? extends ClientSelector> clientSelector;
    private int proxyPort;
    private List<ProxyTarget> targets;

    public Service() {
        super(NodeType.SERVICE);
    }

    public boolean isDeploy() {
        if (targets == null || targets.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Service)) return false;
        if (!super.equals(o)) return false;
        Service service = (Service) o;
        return getProxyPort() == service.getProxyPort() &&
                getName().equals(service.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName(), getProxyPort());
    }
}
