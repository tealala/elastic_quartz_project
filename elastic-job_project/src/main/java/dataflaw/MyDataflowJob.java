package dataflaw;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/4/30
 */
public class MyDataflowJob implements DataflowJob<String> {

    @Override
    public List<String> fetchData(ShardingContext shardingContext) {
        System.out.println(String.format("分片项 ShardingItem: %s | 运行时间: %s | 线程ID: %s | 分片参数: %s ",
            shardingContext.getShardingItem(), new SimpleDateFormat("HH:mm:ss").format(new Date()),
            Thread.currentThread().getId(), shardingContext.getShardingParameter()));
//        System.out.println("获取数据：" + shardingContext.toString());
        List<String> result = getValueString(shardingContext);
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;

    }

    @Override
    public void processData(ShardingContext shardingContext, List<String> list) {
//        System.out.println("开始处理数据：");
        for (String s : list) {
//            System.out.println("数据为：" + s);
        }

    }

    public String[] getFieldName(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        String[] name = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            name[i] = fields[i].getName();
        }
        return name;
    }

    public Object getVauleByFieldName(String name, Object object) {
        name = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
        Method method = null;
        Object value = null;
        try {
            method = object.getClass().getMethod(name, new Class[] {});
            value = method.invoke(object, new Object[] {});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return value;
    }

    public List<String> getValueString(ShardingContext shardingContext){
        List<String> result = new ArrayList<>();
        String[] fieldNames = getFieldName(shardingContext);
        for (String fieldName : fieldNames) {
            Object object = getVauleByFieldName(fieldName,shardingContext);
            String value = fieldName +"["+object+"]";
            result.add(value);
        }
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
}
