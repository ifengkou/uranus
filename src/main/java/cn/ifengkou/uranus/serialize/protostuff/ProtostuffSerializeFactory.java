package cn.ifengkou.uranus.serialize.protostuff;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;


/**
 * ProtostuffSerializeFactory 工厂类
 *
 * @author shenlongguang<https://github.com/ifengkou>
 * @date 2017/2/22 15:20
 */
public class ProtostuffSerializeFactory extends BasePooledObjectFactory<ProtostuffSerialize> {
    private ProtostuffSerialize createProtostuff() {
        return new ProtostuffSerialize();
    }

    @Override
    public ProtostuffSerialize create() throws Exception {
        return createProtostuff();
    }
    @Override
    public  PooledObject<ProtostuffSerialize> wrap(ProtostuffSerialize serialize) {
        return new DefaultPooledObject<ProtostuffSerialize>(serialize);
    }
}
