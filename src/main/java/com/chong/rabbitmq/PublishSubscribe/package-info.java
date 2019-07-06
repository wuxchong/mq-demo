package com.chong.rabbitmq.PublishSubscribe;

/**
 * Publish/Subscribe(发布/订阅)
 * 内容节点也叫主题（Topic），是为发布者（Publisher）和订阅者（Subscriber）提供传输的中介
 * 在该模式下，producer（生产者）并不直接发送消息到queue（队列）中，而是发到exchange（交换器）中
 * exchange一边接收来自producer的消息，一边将消息插入queue中
 * exchange必须清楚接收到消息的下一步用途，比如：是将消息插入一个queue中，还是多个queue中
 * exchange type（交换器类型）标识的消息的用途，主要有direct，topic，headers，fanout
 *
 * fanout：把所有他接收的消息广播到所有它知道的队列
 *
 * direct：
 *        Direct交换机根据消息的路由键把消息传递给队列。
 *        路由键是生产者加到消息头部的一个消息属性。
 *        路由键可以被视作交换机用来决定怎么路由这个消息的一个地址。
 *        消息会进入到binding key完全符合消息的路由键的队列中
 *
 *      Routing(路由)意味着在消息订阅中可选择性的订阅部分消息
 *      使用direct类型的exchange type，消息会被推送至binding key(绑定键）和
 *              消息发布附带的routing key（路由键）完全匹配的队列，
 *      比如：channel.queueBind(queueName, EXCHANGE_NAME, "error")
 *      第三个参数blank就是routing key
 *
 * topic：
 *      topic类型的exchange比direct类型拥有更多的灵活性。
 *      topic exchange 的routing key 可以是长度不超过225 bytes的字符，以点号“.”进行分割
 *      比如：stock.usd.nyse、 nyse.vmw或quick.orange.rabbit。
 *      需要注意的是，所绑定的key必须是相同的格式。其中，有两个特殊的字符：
 *          * 代表任意一个单词
 *          # 代表0个或多个单词
 *      例如：某个routing key 的格式为<speed>.<colour>.<species>。
 *      其中speed代表速度，colour代表颜色，species代表种类
 *      那么*.orange.*代表了颜色为orange的所有动物
 *          lazy.#代表了所有懒惰的动物
 *
 * headers:
 *      Headers交换机基于包括headers和可选值的参数来做路由。
 *      Headers交换机和Topic交换机非常像，但是它是基于header的值而不是路由键来做路由的。
 *      一条消息被认为是匹配的，如果它header的值与绑定中指定的值相同的话。
 *
 *      一个叫做”x-match“的特殊的参数说明是否所有的header都必须匹配或者只需要有一个，
 *      它有两种值， 默认为"all"，表示所有的header键值对都必须匹配；
 *      而"any"表示至少一个header键值对需要匹配。Headers可以被int或者string组成。
 *
 */