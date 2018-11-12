package tech.pcloud.nnts.core.model;

import lombok.Data;

@Data
public class NodeServiceRef {
    private Long id;
    private Long nodeId;
    private Long serviceId;
}
