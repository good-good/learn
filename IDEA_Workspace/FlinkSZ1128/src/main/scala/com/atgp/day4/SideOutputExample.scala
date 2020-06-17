package com.atgp.day4

import com.atgp.day2.{SensorReading, SensorSource}
import org.apache.flink.streaming.api.functions.ProcessFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.util.Collector

object SideOutputExample {
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)

        val stream = env
                .addSource(new SensorSource)
                .process(new FreeingMonitor)

        stream.getSideOutput(new OutputTag[String]("freezing-alarms"))
                .print()

        env.execute()
    }

    class FreeingMonitor extends  ProcessFunction[SensorReading,SensorReading]{
        //定义侧输出标签
        lazy val freezingAlarmOutput = new OutputTag[String]("freezing-alarms")

        //来一条数据，调用一次
        override def processElement(i: SensorReading, context: ProcessFunction[SensorReading, SensorReading]#Context, collector: Collector[SensorReading]): Unit = {
            if(i.temperature < 32.0){
                println(i.temperature)
                context.output(freezingAlarmOutput,s"传感器ID为${i.id}的传感器发出低于32华氏度的低温报警")
            }

            collector.collect(i)

        }
    }

}
