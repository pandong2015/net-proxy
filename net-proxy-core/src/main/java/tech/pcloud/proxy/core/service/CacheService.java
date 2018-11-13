package tech.pcloud.proxy.core.service;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.pcloud.framework.utility.common.Cache;

import java.util.Map;

@Slf4j
@Service
public class CacheService {
    @Autowired
    private Cache cache;

    public static final String CACHE_VALUE_NAME_CLIENT_CHANNEL_MAP = "client-channel-map";
    public static final String CACHE_VALUE_NAME_CHANNEL_PORTMAP = "channel-port-map";
    public static final String CACHE_VALUE_NAME_CHANNEL_REQUESTMAP = "channel-request-map";

    public static final String CACHE_VALUE_NAME_REQUEST_CHANNEL_MAP = "request-channel-map";

    public void cacheServiceRequestChannel(long requestId, Channel channel) {
        Map<Long, Channel> requestChannelMap = cache.map(CACHE_VALUE_NAME_REQUEST_CHANNEL_MAP);
        requestChannelMap.put(requestId, channel);
    }

    public void removeServiceRequestChannel(long requestId) {
        Map<Long, Channel> requestChannelMap = cache.map(CACHE_VALUE_NAME_REQUEST_CHANNEL_MAP);
        requestChannelMap.remove(requestId);
    }


    public Channel getRequestServiceChannel(long requestId) {
        Map<Long, Channel> requestChannelMap = cache.map(CACHE_VALUE_NAME_REQUEST_CHANNEL_MAP);
        return requestChannelMap.get(requestId);
    }

    public void cacheClientChannel(long clientId, Channel channel) {
        Map<Long, Channel> channelMap = cache.map(CACHE_VALUE_NAME_CLIENT_CHANNEL_MAP);
        channelMap.put(clientId, channel);
    }

    public Channel getClientChannel(long clientId){
        Map<Long, Channel> channelMap = cache.map(CACHE_VALUE_NAME_CLIENT_CHANNEL_MAP);
        return channelMap.get(clientId);
    }

    public void cacheServicePortChannel(int port, Channel channel){
        Map<Integer, Channel> channelMap = cache.map(CACHE_VALUE_NAME_CHANNEL_PORTMAP);
        channelMap.put(port, channel);
    }

    public boolean chechServicePortChannel(int port){
        Map<Integer, Channel> channelMap = cache.map(CACHE_VALUE_NAME_CHANNEL_PORTMAP);
        return channelMap.containsKey(port);
    }

    public Channel getServiceChannel(int port){
        Map<Integer, Channel> channelMap = cache.map(CACHE_VALUE_NAME_CHANNEL_PORTMAP);
        return channelMap.get(port);
    }

    public void cacheRequestChannel(long requestId, Channel channel){
        Map<Long, Channel> channelMap = cache.map(CACHE_VALUE_NAME_CHANNEL_REQUESTMAP);
        channelMap.put(requestId, channel);
    }

    public Channel getRequestChannel(long requestId){
        Map<Long, Channel> channelMap = cache.map(CACHE_VALUE_NAME_CHANNEL_REQUESTMAP);
        Channel channel = channelMap.get(requestId);
        return channel;
    }

    public void removeRequestChannel(long requestId){
        Map<Long, Channel> channelMap = cache.map(CACHE_VALUE_NAME_CHANNEL_REQUESTMAP);
        channelMap.remove(requestId);
    }
}
