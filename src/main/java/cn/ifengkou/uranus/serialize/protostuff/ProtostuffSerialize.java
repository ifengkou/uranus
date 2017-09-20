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
package cn.ifengkou.uranus.serialize.protostuff;


import cn.ifengkou.uranus.model.MessageRequest;
import cn.ifengkou.uranus.model.MessageResponse;
import cn.ifengkou.uranus.serialize.RpcSerialize;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * ProtostuffSerialize 对象
 *
 * @author shenlongguang<https://github.com/ifengkou>
 * @date 2017/2/22 15:20
 */
public class ProtostuffSerialize implements RpcSerialize {
    private static SchemaCache cachedSchema = SchemaCache.getInstance();
    private static Objenesis objenesis = new ObjenesisStd(true);
    private boolean rpcDirect = false;

    public boolean isRpcDirect() {
        return rpcDirect;
    }

    public void setRpcDirect(boolean rpcDirect) {
        this.rpcDirect = rpcDirect;
    }

    private static <T> Schema<T> getSchema(Class<T> cls) {
        return (Schema<T>) cachedSchema.get(cls);
    }

    public Object deserialize(InputStream input) {
        try {
            Class cls = isRpcDirect() ? MessageRequest.class : MessageResponse.class;
            Object message = (Object) objenesis.newInstance(cls);
            Schema<Object> schema = getSchema(cls);
            ProtostuffIOUtil.mergeFrom(input, message, schema);
            return message;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public void serialize(OutputStream output, Object object) {
        Class cls = (Class) object.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema schema = getSchema(cls);
            ProtostuffIOUtil.writeTo(output, object, schema, buffer);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
    }
}

