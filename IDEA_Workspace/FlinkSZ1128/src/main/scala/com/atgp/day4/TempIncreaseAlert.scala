package com.atgp.day4

import com.atgp.day2.{SensorReading, SensorSource}
import org.apache.flink.api.common.state.ValueStateDescriptor
import org.apache.flink.api.scala.typeutils.Types
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.util.Collector


object TempIncreaseAlert {
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)

        val stream = env
                .addSource(new SensorSource)
                .keyBy(_.id)
                .process(new TempIncreaseAlertFunction)

        stream.print()
        env.execute()
    }

    class TempIncreaseAlertFunction extends KeyedProcessFunction[String,SensorReading,String]{
        lazy val lastTemp = getRuntimeContext.getState(
            new ValueStateDescriptor[Double](
                "last-temp",
                Types.of[Double]
            )
        )

        lazy val currentTimer = getRuntimeContext.getState(
            new ValueStateDescriptor[Long](
                "timer",
                Types.of[Long]
            )
        )

        override def onTimer(timestamp: Long, ctx: KeyedProcessFunction[String, SensorReading, String]#OnTimerContext, out: Collector[String]): Unit = {
            out.collect("传感器ID为"+ ctx.getCurrentKey+ "的传感器，温度连续1秒上升了！")
            currentTimer.clear()
        }

        override def processElement(i: SensorReading, context: KeyedProcessFunction[String, SensorReading, String]#Context, collector: Collector[String]): Unit = {
            //获取最近一次的温度
            val prevTemp = lastTemp.value()
            //将当前温度存入变量
            lastTemp.update(i.temperature)
            //获取定时器状态变量中的时间戳
            val curTimerTimeStamp = currentTimer.value()
            //初始化或者温度下降
            if(prevTemp == 0.0 || i.temperature<prevTemp){
                context.timerService().deleteProcessingTimeTimer(curTimerTimeStamp)
                println(i)
                currentTimer.clear()
            }else if(i.temperature> prevTemp && curTimerTimeStamp == 0L){
                val timerTs = context.timerService().currentProcessingTime() + 2000L
                context.timerService().registerProcessingTimeTimer(timerTs)

                currentTimer.update(timerTs)
            }

            // 如果温度上升

            // Timer回调

        }
    }

}
