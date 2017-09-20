package cn.ifengkou.uranus;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

/**
 * Description: TCPServer
 *
 * @author shenlongguang
 * @version 1.0
 * @date: 2017/2/21 11:34
 */
@Component
public class TCPServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TCPServer.class);

    @Autowired
    @Qualifier("serverBootstrap")
    private ServerBootstrap serverBootstrap;

    @Autowired
    @Qualifier("tcpSocketAddress")
    private InetSocketAddress tcpPort;

    private ChannelFuture serverChannelFuture;

    @PostConstruct
    public void start() throws InterruptedException {
        LOGGER.info("Starting server at {}", tcpPort.toString());
        serverChannelFuture = serverBootstrap.bind(tcpPort).sync();
    }

    @PreDestroy
    public void stop() throws Exception {
        LOGGER.info("Closing server at {}", tcpPort.toString());
        serverChannelFuture.channel().closeFuture().sync();
    }

}
