package tech.pcloud.nnts.server.mapper;

import org.apache.ibatis.annotations.*;
import tech.pcloud.nnts.core.model.Node;

import java.util.List;

public interface NodesMapper {
    @Insert("insert into server.nodes(id, name, ip, port, type) values(#{id}, #{name}, #{ip}, #{port}, #{type})")
    void insert(Node node);

    @Select("select id, name, ip, port, type from server.nodes where id=${id}")
    @Results(id = "nodeMap", value = {
            @Result(column = "type", property = "type", javaType = int.class)
    })
    Node load(@Param("id") long id);

    @Select("select id, name, ip, port, type from server.nodes where name=${name}")
    Node loadByName(String name);

    @Select("select id, name, ip, port, type from server.nodes")
    List<Node> selectAll();
}
