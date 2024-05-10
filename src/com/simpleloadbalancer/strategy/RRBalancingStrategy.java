package com.simpleloadbalancer.strategy;

import com.simpleloadbalancer.server.IServer;

import java.util.List;
import java.util.Map;

public class RRBalancingStrategy implements IBalancingStrategy{

    @Override
    public IServer getNextServer(List<IServer> servers, Map<IServer, Boolean> statusMap, IServer prevChoice) {
        int index = prevChoice==null?-1:servers.indexOf(prevChoice);
        for(int i=(index+1);i<servers.size();i++){
            IServer server = servers.get(i);
            if(statusMap.get(server)){
                return server;
            }
        }
        for(int i=0;i<=index;i++){
            IServer server = servers.get(i);
            if(statusMap.get(server)){
                return server;
            }
        }
        return null;
    }
}
