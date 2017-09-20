package cn.ifengkou.uranus.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Description: TCPChannel initializer
 *
 * @author shenlongguang
 * @version 1.0
 * @date: 2017/2/21 14:29
 */
@Component
@Qualifier("tcpChannelInitializer")
public class TCPChannelInitializer  extends ChannelInitializer<SocketChannel> {
    @Autowired
    StringDecoder stringDecoder;

    @Autowired
    StringEncoder stringEncoder;

    @Autowired
    StringMessageHandler stringMessageHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        ByteBuf delimiter = Unpooled.copiedBuffer("&".getBytes());
        pipeline.addLast(new DelimiterBasedFrameDecoder(1024,delimiter));
        pipeline.addLast("decoder", stringDecoder);
        pipeline.addLast("handler", stringMessageHandler);
        pipeline.addLast("encoder", stringEncoder);
    }

}
