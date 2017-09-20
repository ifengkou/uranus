package cn.ifengkou.uranus.handler;

import cn.ifengkou.uranus.serialize.protostuff.ProtostuffCodecUtil;
import cn.ifengkou.uranus.serialize.protostuff.ProtostuffDecoder;
import cn.ifengkou.uranus.serialize.protostuff.ProtostuffEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 使用 protostuff 序列化bean 的handler
 *
 * @author shenlongguang<https://github.com/ifengkou>
 * @date 2017/2/22 19:42
 */
@Component
@Qualifier("protobuffChannelInitializer")
public class ProtobuffChannelInitializer extends ChannelInitializer<SocketChannel> {
    final public static int LENGTHFIELD_LENGTH = 4;
    final public static int MAX_FRAME_LENGTH = 10240;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast("frameDecode", new LengthFieldBasedFrameDecoder(MAX_FRAME_LENGTH, 0, LENGTHFIELD_LENGTH, 0, LENGTHFIELD_LENGTH));
        pipeline.addLast("frameEncode", new LengthFieldPrepender(LENGTHFIELD_LENGTH));

        ProtostuffCodecUtil util = new ProtostuffCodecUtil();
        util.setRpcDirect(true);

        pipeline.addLast("decoder", new ProtostuffDecoder(util));
        pipeline.addLast("encoder", new ProtostuffEncoder(util));
        pipeline.addLast("handler", new MessageRecvHandler());
        //空闲读超时设置
        //pipeline.addLast(new IdleStateHandler(40, 0, 0));
    }
}
