package tech.pcloud.proxy.client.mapper;

import org.apache.ibatis.annotations.*;
import tech.pcloud.proxy.core.model.Service;

import java.util.List;

public interface ServicesMapper {
    @Select("select id, name, local_ip, local_port, proxy_port, ssl, status, health_check_url from client.services")
    @Results(id = "servicesMap", value = {
            @Result(column = "local_ip", property = "localIp"),
            @Result(column = "local_port", property = "localPort"),
            @Result(column = "proxy_port", property = "proxyPort"),
            @Result(column = "health_check_url", property = "healthCheckUrl")
    })
    List<Service> selectAll();

    @Select("select id, name, local_ip, local_port, proxy_port, ssl, status, health_check_url from client.services where id=#{id}")
    @ResultMap("servicesMap")
    Service load(@Param("id") long id);

    @Select("select id, name, local_ip, local_port, proxy_port, ssl, status, health_check_url from client.services where name=#{name}")
    @ResultMap("servicesMap")
    Service loadByName(@Param("name") String name);

    @Insert("insert into client.services(id, name, local_ip, local_port, proxy_port, ssl, status, health_check_url) " +
            "values (#{id}, #{name}, #{localIp}, #{localPort}, #{proxyPort}, #{ssl}, #{status}, #{healthCheckUrl})")
    void insert(Service service);

    @Update("update client.services set name=#{name}, local_ip=#{localIp}, local_port=#{localPort}, proxy_port=#{proxyPort}, " +
            "ssl=#{ssl}, status=#{status}, health_check_url=#{healthCheckUrl} where id=#{id}")
    void update(Service service);

    @Update("update client.services set status=#{status} where id=#{id}")
    void updateStatus(Service service);
}
