package tech.pcloud.proxy.core.model;

import lombok.Data;

import java.util.List;

/**
 * @ClassName Services
 * @Author pandong
 * @Date 2018/11/30 13:44
 **/
@Data
public class Services extends BaseObject {
    private List<Service> services;
}
