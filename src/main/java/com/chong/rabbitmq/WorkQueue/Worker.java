package com.chong.rabbitmq.WorkQueue;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

//接收消息
public class Worker {
    private static final Logger LOGGER = LoggerFactory.getLogger(NewTask.class);
    private static final String TASK_QUEUE_NAME = "test_queue";

    public static void main(String[] args) throws Exception {
        // 初始化连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置连接的地址
        factory.setHost("localhost");
        // 获得连接
        Connection connection = factory.newConnection();
        // 创建 Channel
        final Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        channel.basicQos(1);
        LOGGER.info(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                LOGGER.info(" [x] Received '" + message + "'");
                try {
                    doWork(message);
                } finally {
                    LOGGER.info(" [x] Done");

                    // 确认消息
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        // 取消 autoAck
        channel.basicConsume(TASK_QUEUE_NAME, false, consumer);
    }

    private static void doWork(String task) {
        for (char ch : task.toCharArray()) {
            if (ch == '.') {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException _ignored) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

}
