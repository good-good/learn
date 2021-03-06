package com.atgp.day3.window

import com.atgp.day2.SensorSource
import com.atgp.day3.window.MinMaxTempByAggregateAndProcess.WindowResult
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time

object MinMaxByReduceProcess {
    case class MinMaxTemp(id: String,
                          min: Double,
                          max: Double,
                          endTs: Long)
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)

        val stream = env.addSource(new SensorSource)

        stream
                .map(r=>(r.id,r.temperature,r.temperature))
                .keyBy(_._1)
                .timeWindow(Time.seconds(5))
                .reduce( (r1:(String,Double,Double),r2:(String,Double,Double))=>{
                    (r1._1,r1._2.min(r2._2),r1._3.max(r2._3))
                },new WindowResult)
                .print()
        env.execute()

    }

}
