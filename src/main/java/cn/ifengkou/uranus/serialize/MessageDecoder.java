/**
 * Copyright (C) 2016 Newland Group Holding Limited
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.ifengkou.uranus.serialize;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * MessageDecoder 解码 适配器
 *
 * @author shenlongguang<https://github.com/ifengkou>
 * @date 2017/2/22 14:20
 */
public class MessageDecoder extends ByteToMessageDecoder {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageDecoder.class);
    final public static int MESSAGE_LENGTH = MessageCodecUtil.MESSAGE_LENGTH;
    private MessageCodecUtil messageCodecUtil = null;

    public MessageDecoder(final MessageCodecUtil util) {
        this.messageCodecUtil = util;
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if (in.readableBytes() < MessageDecoder.MESSAGE_LENGTH) {
            return;
        }

        in.markReaderIndex();
        int messageLength = in.readInt();

        if (messageLength < 0) {
            ctx.close();
        }

        if (in.readableBytes() < messageLength) {
            in.resetReaderIndex();
            return;
        } else {
            byte[] messageBody = new byte[messageLength];
            in.readBytes(messageBody);

            try {
                Object obj = messageCodecUtil.decode(messageBody);
                out.add(obj);
            } catch (IOException ex) {
                LOGGER.error("messageCodeUtil decode failed!", ex);
            }
        }
    }
}

