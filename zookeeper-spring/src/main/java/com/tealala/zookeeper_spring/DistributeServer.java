package com.tealala.zookeeper_spring;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/4/30
 */
public class DistributeServer {

    private String connectionString = "172.22.22.29:2181,172.22.22.31:2181,172.22.22.34:2181";

    private int sessionTimeout = 15000;

    private ZooKeeper client;

    public static void main(String[] args) throws KeeperException, InterruptedException, IOException {
        DistributeServer server = new DistributeServer();
        //连接集群
        server.getConnection();

        //注册节点
        server.regist("127.0.0.1");

        //业务代码
        Thread.sleep(Long.MAX_VALUE);
    }

    private void regist(String hostname) throws KeeperException, InterruptedException {
        Stat stat = client.exists("/servers1/serverRegister",false);
        String path = null;
        if(stat == null){
            path = client.create("/servers1/serverRegister", hostname.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL);
        }
        System.out.println(hostname + " ;create redist：" + path);
    }

    private void getConnection() throws IOException {
        client = new ZooKeeper(connectionString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("==========start===========");
                List<String> childern = null;
                try {
                    childern = client.getChildren("/", true);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (String s : childern) {
                    System.out.println("data watch" + s);
                }
            }
        });
    }
}
