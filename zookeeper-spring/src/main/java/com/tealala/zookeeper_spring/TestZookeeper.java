package com.tealala.zookeeper_spring;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/4/30
 */
public class TestZookeeper {
    private String connectionString = "172.22.22.29:2181,172.22.22.31:2181,172.22.22.34:2181";

    private int sessionTimeout = 1000;

    private ZooKeeper client;

    @Before
    public void init() throws IOException {
        client = new ZooKeeper(connectionString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("path:" + watchedEvent.getPath());
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
                    System.out.println("data watch process " + s);
                }
            }
        });
    }

    /**
     * 创建节点
     */
    @Test
    public void createNode() {
        try {
            String path = client
                .create("/tealala", "keep".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
            System.out.println(path);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取子节点，并监控节点的变化
     */
    @Test
    public void getDataAndWatch() throws KeeperException, InterruptedException {
        List<String> childern = client.getChildren("/", true);
        for (String s : childern) {
            System.out.println("data watch " + s);
        }

        Thread.sleep(Long.MAX_VALUE);
    }

    @Test
    public void exist() throws KeeperException, InterruptedException {
        Stat stat = client.exists("/tealala", false);
        if (stat != null) {
            System.out.println("is exist:" + stat);
        }
        String path = client
            .create("/tealala", "tealala".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);

        System.out.println("create path :" + path);
    }

}
