package com.atgp.day4

import com.atgp.day2.{SensorReading, SensorSource}
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.watermark.Watermark
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

object PeriodicInsertWatermarks {
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
        env.setParallelism(1)

        val stream = env
                .addSource(new SensorSource)
                .assignTimestampsAndWatermarks(new MyAssigner)
                .keyBy(_.id)
                .timeWindow(Time.seconds(5))
                .process(new MyProcess)
        stream.print()
        env.execute()
    }

    class MyAssigner extends AssignerWithPeriodicWatermarks[SensorReading]{
        val bound:Long = 1000L
        var maxTs:Long = Long.MinValue + bound

        override def getCurrentWatermark: Watermark = {
            new Watermark(maxTs-bound)
        }

        override def extractTimestamp(t: SensorReading, l: Long): Long = {
            maxTs = maxTs.max(t.timestamp)
            t.timestamp
        }
    }

    class MyProcess extends ProcessWindowFunction[SensorReading,String,String,TimeWindow]{
        override def process(key: String, context: Context, elements: Iterable[SensorReading], out: Collector[String]): Unit = {
            out.collect(elements.size.toString)
        }
    }

}
