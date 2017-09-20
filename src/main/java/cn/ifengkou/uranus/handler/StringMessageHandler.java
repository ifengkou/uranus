package cn.ifengkou.uranus.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Description: business handler
 *
 * @author shenlongguang
 * @version 1.0
 * @date: 2017/2/21 14:31
 */

@Component
@Qualifier("stringMessageHandler")
@ChannelHandler.Sharable
public class StringMessageHandler extends SimpleChannelInboundHandler<String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StringMessageHandler.class);

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        LOGGER.debug(msg);
        ctx.channel().writeAndFlush(msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.debug("Channel is active");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.debug("Channel is disconnected");
        super.channelInactive(ctx);
    }

}
