package cn.ifengkou.uranus.handler;

import cn.ifengkou.uranus.model.MessageRequest;
import cn.ifengkou.uranus.model.MessageResponse;
import io.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MessageRequest处理类；业务处理类
 *
 * @author shenlongguang<https://github.com/ifengkou>
 * @date 2017/2/22 18:02
 */

public class MessageRecvHandler extends ChannelInboundHandlerAdapter {
    private final static Logger LOGGER = LoggerFactory.getLogger(MessageRecvHandler.class);
    private String messageId;

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MessageRequest request = (MessageRequest) msg;
        messageId = request.getMessageId();
        MessageResponse response = new MessageResponse();
        response.setMessageId(messageId);

        response.setError("0");
        response.setResult("success");
        try {
            //TODO 消息处理，不要阻塞NIO 线程，复杂的业务逻辑放到kafka consumer 中来处理
            //对收到的数据包进行过滤，解密,校验
            //合法消息存入kafka
            LOGGER.info("receive a message = {}", request.getMessage());

        } catch (Throwable t) {
            response.setError(t.toString());
            LOGGER.error("Message Parse error! {}", t.getMessage());
        }
        //回复消息
        ctx.writeAndFlush(response).addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                LOGGER.info("The Server Send message-id respone:{}", messageId);
            }
        });
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
