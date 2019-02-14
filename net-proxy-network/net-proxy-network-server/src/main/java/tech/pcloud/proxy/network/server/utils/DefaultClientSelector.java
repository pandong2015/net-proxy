package tech.pcloud.proxy.network.server.utils;

import com.google.common.collect.Lists;
import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.pcloud.proxy.configure.model.Client;
import tech.pcloud.proxy.configure.service.ClientSelector;

import java.util.List;

/**
 * @ClassName DefaultClientSelect
 * @Author pandong
 * @Date 2019/2/14 10:45
 **/
public class DefaultClientSelector implements ClientSelector {
    private int index = 0;
    private List<Client> clients;

    public DefaultClientSelector() {
        this(Lists.newArrayList());
    }

    public DefaultClientSelector(List<Client> clients) {
        this.clients = clients;
    }

    @Override
    public void addClient(Client client) {
        clients.add(client);
    }

    @Override
    public Client next() {
        if (clients.isEmpty()) {
            return null;
        }
        if (index >= clients.size()) {
            index = 0;
        }
        Client client = null;
        while (index >= 0 && (client = clients.get(index)) != null) {
            index = 0;
        }
        index++;
        return client;
    }

}
