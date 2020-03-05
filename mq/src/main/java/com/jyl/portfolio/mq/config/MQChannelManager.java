package com.jyl.portfolio.mq.config;

import com.jyl.portfolio.commons.Util;
import com.rabbitmq.client.Channel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import com.rabbitmq.client.Connection;
import javax.annotation.Resource;
import java.io.IOException;

@Component
public class MQChannelManager {

    private static Logger logger = LogManager.getLogger(MQChannelManager.class.getSimpleName());

    @Resource(name = "mqConnectionSeckill")
    private Connection connection;

    private final MQConfigBean mqConfigBean;

    public MQChannelManager(MQConfigBean mqConfigBean) {
        this.mqConfigBean = mqConfigBean;
    }

    // Note: Using ThreadLocal object to allow multi-threaded
    private ThreadLocal<Channel> localSendChannel = new ThreadLocal<Channel>() {
        public Channel initialValue() {
            logger.info("Creating localSendChannel");
            try {
                Channel channelInst = connection.createChannel();
                channelInst.confirmSelect(); // require the broker to confirm the published msg, this feature could
                // be a bottleneck of performance
                channelInst.queueDeclare(Util.jianKuQueuename, true, false, false, null);
                logger.info("Complete Creating localSendChannel");

                return channelInst;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    };

    /**
     * 获取当前线程使用的Rabbitmq通道
     *
     * @return
     */
    public Channel getSendChannel() {
        logger.info("Getting local send channel");
        Channel channel = localSendChannel.get();
        if (channel == null) {
            logger.info("No localSendChannel created, recreating now...");
            // 重新创建队列
            try {
                channel = connection.createChannel();
                channel.queueDeclare(Util.jianKuQueuename, true, false, false, null);
                localSendChannel.set(channel);
                logger.info("Complete localSendChannel creation");

            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        return channel;
    }
}
