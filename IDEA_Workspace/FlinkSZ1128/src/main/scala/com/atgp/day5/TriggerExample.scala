package com.atgp.day5

import com.atgp.day2.{SensorReading, SensorSource}
import com.atgp.day3.window.MinMaxTempByAggregateAndProcess.WindowResult
import java.sql.Timestamp
import org.apache.flink.api.common.state.ValueStateDescriptor
import org.apache.flink.api.scala.typeutils.Types
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.triggers.{Trigger, TriggerResult}
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

object TriggerExample {
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)
        val stream = env.addSource(new SensorSource)
                .keyBy(_.id)
                .timeWindow(Time.seconds(10))
                .trigger(new OneSecondIntervalTrigger)
                .process(new WindowResult)
        stream.print()
        env.execute()

    }

    class WindowResult extends  ProcessWindowFunction[SensorReading,String,String,TimeWindow]{
        override def process(key: String, context: Context, elements: Iterable[SensorReading], out: Collector[String]): Unit = {
        out.collect("传感器ID为"+key+ "的传感器窗口中元素的数量是： "+ elements.size)
        }
    }

    class OneSecondIntervalTrigger extends Trigger[SensorReading,TimeWindow]{
        override def onElement(element: SensorReading, timestamp: Long, window: TimeWindow, ctx: Trigger.TriggerContext): TriggerResult = {
            val firstSeen = ctx.getPartitionedState(
                new ValueStateDescriptor[Boolean]("first-seen", Types.of[Boolean])
            )
            if(!firstSeen.value()){
                val t = ctx.getCurrentProcessingTime+(1000- (ctx.getCurrentProcessingTime%1000))
                ctx.registerProcessingTimeTimer(t)
                ctx.registerProcessingTimeTimer(window.getEnd)
                firstSeen.update(true)
            }

            TriggerResult.CONTINUE

        }

        override def onProcessingTime(time: Long, window: TimeWindow, ctx: Trigger.TriggerContext): TriggerResult = {
            println("回调函数触发时间"+ new Timestamp(time))
            if(time == window.getEnd){
                TriggerResult.FIRE_AND_PURGE
            }else{
                val t = ctx.getCurrentProcessingTime+(1000-(ctx.getCurrentProcessingTime%1000))
                if(t<window.getEnd){
                    ctx.registerProcessingTimeTimer(t)
                }
                TriggerResult.FIRE
            }
        }

        override def onEventTime(time: Long, window: TimeWindow, ctx: Trigger.TriggerContext): TriggerResult = {
            TriggerResult.CONTINUE
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
