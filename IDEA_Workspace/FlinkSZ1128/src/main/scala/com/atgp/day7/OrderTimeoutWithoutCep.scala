package com.atgp.day7



    import java.util.Date
    import org.apache.flink.api.common.state.ValueStateDescriptor
    import org.apache.flink.cep.scala.CEP
    import org.apache.flink.cep.scala.pattern.Pattern
    import org.apache.flink.streaming.api.TimeCharacteristic
    import org.apache.flink.streaming.api.functions.{KeyedProcessFunction, ProcessFunction}
    import org.apache.flink.streaming.api.scala._
    import org.apache.flink.streaming.api.windowing.time.Time
    import org.apache.flink.util.Collector
    import scala.collection.Map
object OrderTimeoutWithoutCep {
        case class OrderEvent(orderId:String,eventType:String,eventTime:Long)


        def main(args: Array[String]): Unit = {
            val env = StreamExecutionEnvironment.getExecutionEnvironment
            env.setParallelism(1)
            env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

            val stream = env
                    .fromElements(
                        OrderEvent("order1", "create", 2000L),
                        OrderEvent("order2", "create", 3000L),
                        OrderEvent("order2", "pay", 4000L)
                    )
                    .assignAscendingTimestamps(_.eventTime)
                    .keyBy(_.orderId)
                    .process(new OrderTimeoutFunc)

            stream.print()
            stream.getSideOutput(new OutputTag[String]("timeout")).print()

            env.execute()


        }

    class OrderTimeoutFunc extends KeyedProcessFunction[String,OrderEvent,String]{
        lazy val orderState = getRuntimeContext.getState(
            new ValueStateDescriptor[OrderEvent]("save order",classOf[OrderEvent])
        )
        override def processElement(value: OrderEvent, ctx: KeyedProcessFunction[String, OrderEvent, String]#Context, out: Collector[String]): Unit = {
            //到来的事件是下订单事件
            val date = new Date()
            println(ctx.timerService().currentWatermark() +" processElement")
            println(value.eventTime+" eventTime processElement")
            if(value.eventType.equals("create")){
                if(orderState.value() == null){//要保证pay事件没有提前到
                    orderState.update(value)
                    ctx.timerService().registerEventTimeTimer(value.eventTime + 5000L)//5秒后的timer
                }
            }else{
                orderState.update(value)
                out.collect("已经支付的订单ID是: " + value.orderId)
            }
        }

        override def onTimer(timestamp: Long, ctx: KeyedProcessFunction[String, OrderEvent, String]#OnTimerContext, out: Collector[String]): Unit = {
            val order = orderState.value()
            val date = new Date()
            println(ctx.timerService().currentWatermark() +" onTimer")
            println(timestamp+" temistamp")
            if(order != null && order.eventType.equals("create")){
                ctx.output(new OutputTag[String]("timeout"),"超时订单的ID为： "+ order.orderId)
            }
            orderState.clear()
        }
    }

}