package tech.pcloud.nnts.core.model;

import lombok.Data;

import java.util.List;

@Data
public class Client extends Node{
  private List<Service> services;

  public Client() {
  }

  public Client(Node node) {
    setId(node.getId());
    setIp(node.getIp());
    setPort(node.getPort());
    setName(node.getName());
  }

  @Override
  public String toString() {
    return "Client{" +
            "id=" + getId() +
            ", name='" + getName() + '\'' +
            ", ip='" + getIp() + '\'' +
            ", port=" + getPort() +
            '}';
  }

}
