package cn.ifengkou.uranus.serialize;

import io.netty.channel.ChannelPipeline;

/**
 * RpcSerializeFrame 序列化组件 选择器;选择采用哪种序列化组件
 *
 * @author shenlongguang<https://github.com/ifengkou>
 * @date 2017/2/22 14:24
 */
@Deprecated
public interface RpcSerializeFrame {
    void select(RpcSerializeProtocol protocol, ChannelPipeline pipeline);
}
