package tech.pcloud.proxy.server.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface NodeServiceRefMapper {

    @Insert("insert into server.node_service_ref(node_id, service_id) values (#{nodeId}, #{serviceId})")
    void insert(@Param("nodeId") long nodeId, @Param("serviceId") long serviceId);

    @Select("select node_id from server.node_service_ref a, server.services b where a,service_id=b.id and b.proxy_port=#{port}")
    Long selectNodeId(int port);
}
