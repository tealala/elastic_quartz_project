package com.tealala.zookeeper_spring;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/5/6
 */
public class DistributeClient {

    private String connectionString = "172.22.22.29:2181,172.22.22.31:2181,172.22.22.34:2181";

    private int sessionTimeout = 1000;

    private ZooKeeper client;

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        DistributeClient client = new DistributeClient();

        //建立连接
        client.getConnection();

        //注册监听
        client.getRegisters();

        //业务逻辑代码
        client.doSomeBusinessLogic();

    }

    private void doSomeBusinessLogic() throws KeeperException, InterruptedException {
        List<String> children = client.getChildren("/servers1",true);
        List<String> result = new ArrayList<>();
        for (String child : children) {
            System.out.println("get children : "+child);
            byte[] data = client.getData("/servers1/"+child,false,null);
            result.add(data.toString());
        }

        System.out.println("=============get register info============");
        for (String s : result) {
            System.out.println(s);
        }

    }

    private void getRegisters() {
    }

    private void getConnection() throws IOException {
        client = new ZooKeeper(connectionString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("=============client start=========");
                List<String> children = null;
                try {
                    children = client.getChildren("/servers1", false);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (String child : children) {
                    System.out.println("client children:" + child);
                }

            }
        });

    }
}
