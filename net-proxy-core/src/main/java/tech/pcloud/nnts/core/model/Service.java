package tech.pcloud.nnts.core.model;

import lombok.Data;

@Data
public class Service extends BaseEntity {
    private Long id;
    private String localIp;
    private Integer localPort;
    private Integer proxyPort;
    private Integer status;
    private Integer ssl;
    private String healthCheckUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Service service = (Service) o;

        return getName().equals(service.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

}
