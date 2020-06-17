package com.atgp.day3.window

import com.atgp.day2.SensorSource
import com.atgp.day3.window.AvgTempPerWindow.AvgTempFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

object AvgTempPerWindosByProcess {
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)
        val stream = env.addSource(new SensorSource)

        stream
                .map(r=>(r.id,r.temperature))
                .keyBy(_._1)
                .timeWindow(Time.seconds(5))
                .process(new ProcessTempFunction)
                .print()
        env.execute()
    }

    class ProcessTempFunction extends ProcessWindowFunction[(String,Double),(String,Double),String,TimeWindow]{
        override def process(key: String, context: Context, elements: Iterable[(String, Double)], out: Collector[(String, Double)]): Unit = {
            val size = elements.size
            var sum:Double = 0.0
            for (elem <- elements) {
                sum+= elem._2
            }
            out.collect((key,sum/size))
        }
    }

}
