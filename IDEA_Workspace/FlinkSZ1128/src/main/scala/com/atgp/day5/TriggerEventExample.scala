package com.atgp.day5

import com.atgp.day2.{SensorReading, SensorSource}
import java.sql.Timestamp
import org.apache.flink.api.common.state.ValueStateDescriptor
import org.apache.flink.api.scala.typeutils.Types
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.triggers.{Trigger, TriggerResult}
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

object TriggerEventExample {
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

        val stream = env
                .socketTextStream("hadoop102", 9999, '\n')
                .map(line => {
                    val arr = line.split(" ")
                    (arr(0), arr(1).toLong)
                })
                .assignAscendingTimestamps(_._2)
                .keyBy(_._1)
                .timeWindow(Time.seconds(10))
                .trigger(new OneSecondIntervalTrigger)
                .process(new WindowResult)
        stream.print()
        env.execute()

    }

    class WindowResult extends  ProcessWindowFunction[(String,Long),String,String,TimeWindow]{
        override def process(key: String, context: Context, elements: Iterable[(String,Long)], out: Collector[String]): Unit = {
            println("当前时间戳 process： "+context.currentProcessingTime)
        out.collect("窗口内"+key+ "的传感器窗口中元素的数量是： "+ elements.size)
        }
    }

    class OneSecondIntervalTrigger extends Trigger[(String, Long),TimeWindow]{
        override def onElement(element: (String, Long), timestamp: Long, window: TimeWindow, ctx: Trigger.TriggerContext): TriggerResult = {
            val firstSeen = ctx.getPartitionedState(
                new ValueStateDescriptor[Boolean]("first-seen", Types.of[Boolean])
            )
            println("当前时间戳 OneSecondIntervalTrigger： "+ ctx.getCurrentProcessingTime)
            if(!firstSeen.value()){
//                val t = ctx.getCurrentWatermark+(1000- (ctx.getCurrentWatermark%1000)) //如果使用getCurrentWatermark的话程序刚开始运行时最小流水线是 long.minvalue 导致定时器注册的时间特别小，第二个元素直接触发
//                // 定时器现在的值很小，水位线也很小，比定时器还小，但是200ms后水位线会更新为刚才第一次输入的时间戳，这样水位线马上就超过了事件定时器的事件，被触发
                val t = element._2 + (1000 - (element._2%1000))
                println("水位线"+ctx.getCurrentWatermark)
                ctx.registerEventTimeTimer(t)
                println("windwogetEnd:" +window.getEnd)
                ctx.registerEventTimeTimer(window.getEnd)
                firstSeen.update(true)
            }

            TriggerResult.CONTINUE

        }

        override def onProcessingTime(time: Long, window: TimeWindow, ctx: Trigger.TriggerContext): TriggerResult = {
            TriggerResult.CONTINUE
        }

        override def onEventTime(time: Long, window: TimeWindow, ctx: Trigger.TriggerContext): TriggerResult = {
            println("回调函数触发时间"+ time)
            if(time == window.getEnd){
                TriggerResult.FIRE_AND_PURGE
            }else{
                val t = ctx.getCurrentWatermark+(1000-(ctx.getCurrentWatermark%1000))
                if(t<window.getEnd){
                    println("registerTimer:" +t)
                    ctx.registerEventTimeTimer(t)
                }
                TriggerResult.FIRE
            }
        }

        override def clear(window: TimeWindow, ctx: Trigger.TriggerContext): Unit = {
            val firstSeen = ctx.getPartitionedState(
                new ValueStateDescriptor[Boolean](
                    "first-seen",Types.of[Boolean]
                )
            )
            firstSeen.clear()
        }
    }

}
