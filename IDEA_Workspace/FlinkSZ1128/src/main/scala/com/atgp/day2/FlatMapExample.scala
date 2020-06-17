package com.atgp.day2

import org.apache.flink.api.common.functions.FlatMapFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.util.Collector

object FlatMapExample {
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)

        val stream = env.addSource(new SensorSource)

//        stream.flatMap(new FlatMapFunction[SensorReading,String] {
//            override def flatMap(t: SensorReading, collector: Collector[String]): Unit = {
//                collector.collect(t.id)
//            }
//        }).print


        stream.flatMap(new FlatMapFunction[SensorReading,SensorReading] {
            override def flatMap(t: SensorReading, collector: Collector[SensorReading]): Unit = {
                if(t.id.equals("sendor1")){
                    collector.collect(t)
                    collector.collect(t)
                }
            }
        }).print()

        env.execute
    }

}
