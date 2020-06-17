package com.atgp.day5

import com.atgp.day2.{SensorReading, SensorSource}
import org.apache.flink.api.common.state.{ ValueStateDescriptor}
import org.apache.flink.api.scala.typeutils.Types
import org.apache.flink.streaming.api.functions.co.CoProcessFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.util.Collector

object CoprocessFunctionExample {
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)

        val stream = env.addSource(new SensorSource)
        val readings = stream
                .keyBy(_.id)

        val filterSwitches = env.fromElements(
            ("sendor2", 10 * 1000L),
            ("sendor7", 60 * 1000L))
                .keyBy(_._1)
        //两条流connect之前一定要先 keyby
        readings
                .connect(filterSwitches)
                .process(new ReadingFilter)
                .print

        env.execute()
    }

    class ReadingFilter extends CoProcessFunction[SensorReading,(String,Long),SensorReading]{
        lazy val forwardingEnable=
            getRuntimeContext.getState(
                new ValueStateDescriptor[Boolean]("filter-switch", Types.of[Boolean])
            )

        override def processElement1(value: SensorReading, ctx: CoProcessFunction[SensorReading, (String, Long),
                SensorReading]#Context, out: Collector[SensorReading]): Unit = {
            //处理第一条无限流
            if(forwardingEnable.value()){
                out.collect(value)
            }
        }

        override def processElement2(value: (String, Long), ctx: CoProcessFunction[SensorReading, (String, Long),
                SensorReading]#Context, out: Collector[SensorReading]): Unit = {
            forwardingEnable.update(true)//打开开关

            //开关时长
            val timerTs = ctx.timerService().currentProcessingTime()+value._2

            ctx.timerService().registerProcessingTimeTimer(timerTs)
        }

        override def onTimer(timestamp: Long, ctx: CoProcessFunction[SensorReading, (String, Long),
                SensorReading]#OnTimerContext, out: Collector[SensorReading]): Unit = {
            forwardingEnable.update(false)
        }
    }

}
