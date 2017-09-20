package cn.ifengkou.uranus.config;

import cn.ifengkou.uranus.handler.ProtobuffChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Spring configuration component
 *
 * @author shenlongguang
 * @version 1.0
 * @date: 2017/2/21 9:58
 */
@Configuration
@ComponentScan("cn.ifengkou")
@PropertySource("classpath:application.properties")
public class SpringConfig {
    @Value("${boss.thread.count}")
    private int bossCount;
    @Value("${worker.thread.count}")
    private int workerCount;
    @Value("${tcp.port}")
    private int tcpPort;
    @Value("${so.keepalive}")
    private boolean keepAlive;
    @Value("${so.backlog}")
    private int backlog;

    /*@Autowired
    @Qualifier("tcpChannelInitializer")
    private TCPChannelInitializer tcpChannelInitializer;*/

    @Autowired
    @Qualifier("protobuffChannelInitializer")
    private ProtobuffChannelInitializer protobuffChannelInitializer;

    @Bean(name = "bossGroup", destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup bossGroup() {
        return new NioEventLoopGroup(bossCount);
    }

    @Bean(name = "workerGroup", destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup workerGroup() {
        return new NioEventLoopGroup(workerCount);
    }

    @Bean(name = "tcpSocketAddress")
    public InetSocketAddress tcpPort() {
        return new InetSocketAddress(tcpPort);
    }

    @Bean(name = "tcpChannelOptions")
    public Map<ChannelOption<?>, Object> tcpChannelOptions() {
        Map<ChannelOption<?>, Object> options = new HashMap<ChannelOption<?>, Object>();
        options.put(ChannelOption.SO_KEEPALIVE, keepAlive);
        options.put(ChannelOption.SO_BACKLOG, backlog);
        options.put(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
        options.put(ChannelOption.SO_TIMEOUT, 3000);
        //SO_RCVBUF，SO_SNDBUF需要根据推送消息的大小，合理设置
        options.put(ChannelOption.SO_RCVBUF, 10240);
        options.put(ChannelOption.SO_SNDBUF, 10240);
        options.put(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(32768, 65536));
        return options;
    }

    @Bean(name = "stringEncoder")
    public StringEncoder stringEncoder() {
        return new StringEncoder(CharsetUtil.UTF_8);
    }

    @Bean(name = "stringDecoder")
    public StringDecoder stringDecoder() {
        return new StringDecoder(CharsetUtil.UTF_8);
    }


    @Bean(name = "serverBootstrap")
    public ServerBootstrap bootstrap() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup(), workerGroup()).channel(NioServerSocketChannel.class).childHandler(protobuffChannelInitializer);
        Map<ChannelOption<?>, Object> tcpChannelOptions = tcpChannelOptions();
        Set<ChannelOption<?>> keySet = tcpChannelOptions.keySet();
        for (ChannelOption option : keySet) {
            bootstrap.option(option, tcpChannelOptions.get(option));
        }

        return bootstrap;
    }

    /**
     * Necessary to make the Value annotations work.
     *
     * @return
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
