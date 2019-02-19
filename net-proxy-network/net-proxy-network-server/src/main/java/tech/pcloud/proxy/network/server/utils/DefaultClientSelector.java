package tech.pcloud.proxy.network.server.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import tech.pcloud.proxy.configure.model.Client;
import tech.pcloud.proxy.configure.service.ClientSelector;

import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * @ClassName DefaultClientSelect
 * @Author pandong
 * @Date 2019/2/14 10:45
 **/
public class DefaultClientSelector implements ClientSelector {
    private Map<Long, Client> clients = Maps.newConcurrentMap();
    private Queue<Long> clientIds = Queues.newConcurrentLinkedQueue();

    public DefaultClientSelector() {
        this(Lists.newArrayList());
    }

    public DefaultClientSelector(List<Client> clients) {
        clients.forEach(c -> {
            addClient(c);
        });
    }

    @Override
    public void addClient(Client client) {
        if (clientIds.offer(client.getId())) {
            clients.put(client.getId(), client);
        }
    }

    @Override
    public void delClient(Client client) {
        clients.remove(client.getId());
//        clientIds.remove(client.getId());
    }

    @Override
    public int size() {
        return clients.size();
    }

    @Override
    public Client next() {
        if (clientIds.isEmpty() || clients.isEmpty()) {
            return null;
        }
        Client client = null;
        Long clientId = clientIds.poll();
        if (clients.containsKey(clientId)) {
            client = clients.get(clientId);
            clientIds.offer(clientId);
        } else {
            client = next();
        }
        return client;
    }

}
