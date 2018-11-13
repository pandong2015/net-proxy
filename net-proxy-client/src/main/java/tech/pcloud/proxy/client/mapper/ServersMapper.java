package tech.pcloud.proxy.client.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tech.pcloud.proxy.core.model.Node;

import java.util.List;

public interface ServersMapper {
    @Select("select id, name, ip, port from client.servers")
    List<Node> selectAll();

    @Select("select id, name, ip, port from client.servers where id=#{id}")
    Node load(long id);

    @Update("update client.servers set name=#{name}, ip=#{ip}, port=#{port} where id=#{id}")
    void update(Node server);

    @Update("update client.servers set id=#{id}, ip=#{ip}, port=#{port} where name=#{name}")
    void updateByName(Node server);
}
