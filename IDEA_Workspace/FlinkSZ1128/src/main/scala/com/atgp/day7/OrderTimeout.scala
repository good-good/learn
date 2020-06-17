package com.atgp.day7

import org.apache.flink.cep.scala.CEP
import org.apache.flink.cep.scala.pattern.Pattern
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.util.Collector
import scala.collection.Map
object OrderTimeout {
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

        val pattern = Pattern.begin[OrderEvent]("create")
                .where(_.eventType.equals("create"))
                .next("pay").where(_.eventType.equals("pay" +
                ""))
                .within(Time.seconds(5))

        val patternedStream = CEP.pattern(stream, pattern)

        val orderTimeoutOutput = new OutputTag[String]("timeout")

        val timeoutFunc = (map:Map[String,Iterable[OrderEvent]],ts:Long,out: Collector[String])=>{
            val orderStart = map("create").head

            out.collect(orderStart.orderId +" 没有支付")
        }
        val selectFunc = (map:Map[String,Iterable[OrderEvent]],out:Collector[String])=>{
            val order = map("pay").head
            out.collect(order.orderId + " 已经支付！")
        }
        val outputStream = patternedStream
                .flatSelect(orderTimeoutOutput)(timeoutFunc)(selectFunc)
        outputStream.print()
        outputStream.getSideOutput(new OutputTag[String]("timeout")).print()

        env.execute()


    }

}
