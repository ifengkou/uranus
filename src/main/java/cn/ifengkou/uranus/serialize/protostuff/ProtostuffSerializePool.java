package cn.ifengkou.uranus.serialize.protostuff;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ProtostuffSerialize 对象池。多线程 单例（单对象，单池）
 * 利用commons.pool2的对象池 对 序列化对象 进行池化
 *
 * @author shenlongguang<https://github.com/ifengkou>
 * @date 2017/2/22 15:20
 */
public class ProtostuffSerializePool {
    private final static Logger LOGGER = LoggerFactory.getLogger(ProtostuffSerializePool.class);
    //序列化池
    private GenericObjectPool<ProtostuffSerialize> serializerPool;
    //当前对象实例
    volatile private static ProtostuffSerializePool factoryInstance = null;

    private ProtostuffSerializePool() {
        serializerPool = new GenericObjectPool<ProtostuffSerialize>(new ProtostuffSerializeFactory());
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();

        config.setMaxTotal(1000);
        config.setMinIdle(2);
        config.setMaxWaitMillis(5000);
        //config.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        serializerPool.setConfig(config);
    }

    /**
     * 实例化 protostuff 序列化池；多线程 单例
     *
     * @return
     */
    public static ProtostuffSerializePool getProtostuffPoolInstance() {
        if (factoryInstance == null) {
            synchronized (ProtostuffSerializePool.class) {
                if (factoryInstance == null) {

                    factoryInstance = new ProtostuffSerializePool();
                }
            }
        }
        return factoryInstance;
    }

    /**
     * 获取 protostuff 序列化池对象
     *
     * @return 池对象
     */
    public GenericObjectPool<ProtostuffSerialize> getSerializerPool() {
        return serializerPool;
    }

    /*public ProtostuffSerializePool(final int maxTotal, final int minIdle, final long maxWaitMillis, final long minEvictableIdleTimeMillis) {
        serializerPool = new GenericObjectPool<ProtostuffSerialize>(new ProtostuffSerializeFactory());

        GenericObjectPoolConfig config = new GenericObjectPoolConfig();

        config.setMaxTotal(maxTotal);
        config.setMinIdle(minIdle);
        config.setMaxWaitMillis(maxWaitMillis);
        config.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);

        serializerPool.setConfig(config);
    }*/

    public ProtostuffSerialize borrow() {
        try {
            return getSerializerPool().borrowObject();
        } catch (final Exception ex) {
            LOGGER.error("pool borrow serializer failed:{}", ex.getMessage());
            return null;
        }
    }

    public void restore(final ProtostuffSerialize object) {
        getSerializerPool().returnObject(object);
    }


}
