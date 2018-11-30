package tech.pcloud.proxy.server.mapper;

import org.apache.ibatis.annotations.*;
import tech.pcloud.proxy.core.model.Service;

import java.util.List;

public interface ServicesMapper {
    @Insert("insert into server.services(id, name, proxy_port, ssl, status) values(#{id}, #{name}, #{proxyPort}, #{ssl}, #{status})")
    void insert(Service service);

    @Update("update server.services set proxy_port=#{proxyPort}, ssl=#{ssl}, status=#{status} where id=#{id}")
    void update(Service service);

    @Select("select a.id, a.name, a.proxy_port, a.ssl, a.status from server.services a, server.node_service_ref b where a.status=0 and a.id=b.service_id and b.node_id=#{nodeId}")
    @Results(id = "serverResult", value={
            @Result(column = "proxy_port", property = "proxyPort")
    })
    List<Service> selectByNode(long nodeId);

    @Select("select id, name, proxy_port, ssl, status from server.services where id=#{id}")
    @ResultMap(value = "serverResult")
    Service load(long id);

    @Select("select id, name, proxy_port, ssl, status from server.services where name=#{name}")
    @ResultMap(value = "serverResult")
    Service loadByName(String name);

    @Update("update client.services set status=#{status} where id=#{id}")
    void updateStatus(Service service);
}
