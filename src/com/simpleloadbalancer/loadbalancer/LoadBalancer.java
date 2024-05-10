package com.simpleloadbalancer.loadbalancer;

import com.simpleloadbalancer.server.IServer;
import com.simpleloadbalancer.strategy.IBalancingStrategy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class LoadBalancer {

    Map<IServer,Boolean> serverStatus = new HashMap<>();
    //Map<String, IServer> serverMap = new HashMap<>();

    //TODO: check hostname uniqueness
    List<IServer> servers = new ArrayList<>();

    private IServer prevChoice = null;

    private Integer nextServer =0;

    private LBConfiguration config;
    private IBalancingStrategy balancingStrategy;

    private static LoadBalancer loadBalancer=null;

    private Timer healthCheckTask=null;


    private LoadBalancer(){

    }
    public static LoadBalancer getInstance(){

        if(loadBalancer==null) {
            loadBalancer = new LoadBalancer();
        }
        return loadBalancer;
    }

    //TODO: dont restart timer
    private void startHealthCheck(){
        //TODO: start only when servers are present
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                for (IServer server : servers) {
                    try {
                        //TODO: use healthcheck port
                        URI hostURI = new URI(server.getHostname()+"/health");
                        HttpURLConnection con = (HttpURLConnection) hostURI.toURL().openConnection();
                        con.setRequestMethod("GET");
                        int respCode = con.getResponseCode();
                        if (respCode < 200 || respCode >= 400) {
                            System.out.println("Health check failed for " + hostURI.toURL());
                            serverStatus.put(server,false);
                        } else {
                            System.out.println("Health check succeeded for " + hostURI.toURL());
                            serverStatus.put(server,true);
                        }

                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        };

        healthCheckTask = new Timer();
        healthCheckTask.schedule(task, 1000, config.getHealthcheck_interval_in_seconds());
    }

    public LBConfiguration getConfig() {
        return config;
    }

    public void setConfig(LBConfiguration config) {
        if(this.healthCheckTask!=null){
            this.healthCheckTask.cancel();
        }
        this.config = config;
        this.startHealthCheck();
    }


    public IBalancingStrategy getBalancingStrategy() {
        return balancingStrategy;
    }

    public void setBalancingStrategy(IBalancingStrategy balancingStrategy) {
        this.balancingStrategy = balancingStrategy;
    }

    public Integer getNextServer() {
        return nextServer;
    }

    public void setNextServer(Integer nextServer) {
        this.nextServer = nextServer;
    }

    public void addServer(IServer server){
        servers.add(server);
        serverStatus.put(server,false);
    }

    public void removeServer(IServer server){
        servers.remove(server);
    }

    public String callServer(){
        IServer server = this.balancingStrategy.getNextServer(servers,serverStatus,prevChoice);
        if(server==null){
            return "Service Unavailable";
        }
        this.prevChoice = server;
        System.out.println("Calling server " + server.getHostname());
        try{
            URI hostURI = new URI(server.getHostname());
            HttpURLConnection con = (HttpURLConnection) hostURI.toURL().openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(config.getService_timeout_in_ms());
            int respCode = con.getResponseCode();
            if (respCode < 200 || respCode >= 400) {
                return "Call Failed with status code " + respCode;
            } else {
                BufferedReader br = new BufferedReader( new InputStreamReader(con.getInputStream()));
                String line;
                StringBuffer buf = new StringBuffer();
                while((line=br.readLine())!=null){
                    buf.append(line);
                }
                return buf.toString();
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
