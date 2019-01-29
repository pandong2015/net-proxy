package tech.pcloud.proxy.configure.model;

import lombok.Data;

/**
 * @ClassName ProxyTarget
 * @Author pandong
 * @Date 2019/1/25 13:32
 **/
@Data
public class ProxyTarget extends BaseObject{
    private Service service;
    private Server proxyServer;
    private int status;
}
