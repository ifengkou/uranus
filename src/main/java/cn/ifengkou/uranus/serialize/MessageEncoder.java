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
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * MessageDecoder 解码 适配器
 *
 * @author shenlongguang<https://github.com/ifengkou>
 * @date 2017/2/22 14:26
 */
public class MessageEncoder extends MessageToByteEncoder<Object> {

    private MessageCodecUtil messageCodecUtil = null;

    public MessageEncoder(final MessageCodecUtil util) {
        this.messageCodecUtil = util;
    }

    protected void encode(final ChannelHandlerContext ctx, final Object msg, final ByteBuf out) throws Exception {
        messageCodecUtil.encode(out, msg);
    }
}

