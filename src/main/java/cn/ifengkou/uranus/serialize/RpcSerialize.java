package cn.ifengkou.uranus.serialize;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * RpcSerialize interface
 *
 * @author shenlongguang<https://github.com/ifengkou>
 * @date 2017/2/22 14:26
 */
public interface RpcSerialize {
    void serialize(OutputStream output, Object object) throws IOException;

    Object deserialize(InputStream input) throws IOException;
}
