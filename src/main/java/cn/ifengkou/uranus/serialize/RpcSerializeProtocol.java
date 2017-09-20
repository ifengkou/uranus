package cn.ifengkou.uranus.serialize;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * RpcSerializeProtocol 可选序列化种类（只实现protostuff）
 *
 * @author shenlongguang<https://github.com/ifengkou>
 * @date 2017/2/22 14:24
 */
@Deprecated
public enum RpcSerializeProtocol {
    JDKSERIALIZE("jdknative"), KRYOSERIALIZE("kryo"), HESSIANSERIALIZE("hessian"), PROTOSTUFFSERIALIZE("protostuff");

    private String serializeProtocol;

    private RpcSerializeProtocol(String serializeProtocol) {
        this.serializeProtocol = serializeProtocol;
    }

    public String toString() {
        ReflectionToStringBuilder.setDefaultStyle(ToStringStyle.SHORT_PREFIX_STYLE);
        return ReflectionToStringBuilder.toString(this);
    }

    public String getProtocol() {
        return serializeProtocol;
    }


}
