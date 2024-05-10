package com.simpleloadbalancer.server;

import com.simpleloadbalancer.loadbalancer.ILoadBalancer;

import java.util.Objects;

public class BEServer implements IServer{

    private String hostname;

    public BEServer(String hostname){
        this.hostname = hostname;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BEServer beServer = (BEServer) o;
        return Objects.equals(hostname, beServer.hostname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hostname);
    }
}
