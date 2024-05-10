import com.simpleloadbalancer.loadbalancer.LBConfiguration;
import com.simpleloadbalancer.loadbalancer.LoadBalancer;
import com.simpleloadbalancer.server.BEServer;
import com.simpleloadbalancer.server.IServer;
import com.simpleloadbalancer.strategy.RRBalancingStrategy;


public class Driver {

    public static void main(String[] args){
        LoadBalancer lb = LoadBalancer.getInstance();
        LBConfiguration config = new LBConfiguration(60000,5000,"/health",80,5);
        lb.setConfig(config);
        IServer server1 = new BEServer("https://httpbin.org/anything/server1");
        IServer server2 = new BEServer("https://httpbin.org/anything/server2");
        IServer server3 = new BEServer("https://httpbin.org/anything/server3");
        IServer server4 = new BEServer("https://httpbin.org/status/500/");

        lb.addServer(server1);
        lb.addServer(server2);
        lb.addServer(server3);
        lb.addServer(server4);
        lb.setBalancingStrategy(new RRBalancingStrategy());

        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for(int i=0;i<5;i++) {
            String resp = lb.callServer();
            System.out.println(resp);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
