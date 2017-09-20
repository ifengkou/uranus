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

import java.io.IOException;

/**
 * MessageCodec 接口
 *
 * @author shenlongguang<https://github.com/ifengkou>
 * @date 2017/2/22 14:26
 */
public interface MessageCodecUtil {

    int MESSAGE_LENGTH = 4;

    /**
     * MessageCodec encode
     * @param out
     * @param message
     * @throws IOException
     */
    void encode(final ByteBuf out, final Object message) throws IOException;

    /**
     * MessageCodec decode
     * @param body
     * @return
     * @throws IOException
     */
    Object decode(byte[] body) throws IOException;
}
