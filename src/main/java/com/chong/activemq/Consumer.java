package com.chong.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

public class Consumer implements MessageListener{
    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);
    private static final String BROKER_URL = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final String SUBJECT = "test_queue";

    public static void main(String[] args) throws JMSException {
        // 初始化连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        // 获得连接
        Connection connection = connectionFactory.createConnection();
        // 启动连接
        connection.start();
        // 创建Session，第一个参数表示会话是否在事务中执行，第二个参数设定会话的应答模式
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 创建队列
        Destination dest = session.createQueue(SUBJECT);
        //创建 MQ 消息的消费者
        MessageConsumer consumer = session.createConsumer(dest);

        //初始化 MessageListener
        Consumer me = new Consumer();
        //给消费者设定监听对象
        consumer.setMessageListener(me);
    }

    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            LOGGER.info("get message" + textMessage.getText());
        } catch (JMSException e) {
            LOGGER.error("error { }", e);
        }
    }
}
