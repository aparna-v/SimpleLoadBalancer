package com.simpleloadbalancer.strategy;

import com.simpleloadbalancer.server.IServer;

import java.util.List;
import java.util.Map;

public interface IBalancingStrategy {

    public IServer getNextServer(List<IServer> servers, Map<IServer,Boolean> statusMap, IServer prevChoice);

}
