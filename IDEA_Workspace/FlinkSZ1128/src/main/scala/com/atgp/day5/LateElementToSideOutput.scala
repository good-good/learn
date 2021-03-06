package com.atgp.day5

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

object LateElementToSideOutput {
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
        env.setParallelism(1)

        val readings = env
                .socketTextStream("hadoop102", 9999, '\n')
                .map(line => {
                    val arr = line.split(" ")
                    (arr(0), arr(1).toLong * 1000)
                })
                .assignAscendingTimestamps(_._2)


        val countPer10Secs = readings
                .keyBy(_._1)
                .timeWindow(Time.seconds(10))
                .sideOutputLateData(
                    new OutputTag[(String, Long)]("late-readings")
                )
                .process(new CountFunction())

        val lateStream = countPer10Secs
                .getSideOutput(
                    new OutputTag[(String, Long)]("late-readings")
                )
        countPer10Secs.print()
        lateStream.print()

        env.execute()
    }

    class CountFunction extends ProcessWindowFunction[(String,Long),String,String,TimeWindow]{
        override def process(key: String, context: Context, elements: Iterable[(String, Long)], out: Collector[String]): Unit = {
            out.collect(context.window.getStart + "到" + context.window.getEnd + "窗口闭合了")
        }
    }

}
