package tech.pcloud.proxy.configure.service;

import tech.pcloud.proxy.configure.model.Client;

import java.util.List;

/**
 * @ClassName ClientSelect
 * @Author pandong
 * @Date 2019/2/14 10:44
 **/
public interface ClientSelector {
    Client next();

    void addClient(Client client);

    void delClient(Client client);

    int size();

    List<Client> clients();
}
