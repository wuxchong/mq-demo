package com.chong.rabbitmq.RPC;

/**
 * 本例演示如何在 Rabbitmq里实现RPC(远程过程调用)。
 * 这个例子的工作流程如下所示。
 * ·当客户端启动时,它创建了匿名的单独的回调 queue
 * ·客户端实现RPC请求时将同时设置两个属性:设置回调 queue 的replyTo,以及为每个
 *      请求设置唯一值 correlationId
 * ·请求被发送到一个名为 rpc_queue的queue中
 * ·RPC worker 或 server一直在等待那个 queue的求。当请求到达时,通过在 的replyTo
 *      指定的 queue来回复一个消息给客户端。
 * ·客户端一直等待回调 queue 的数据。当消息到达时，检查correlationId属性,如果该属
 *      性和它请求的值一致,那么就返回响应结果给程序。

 */