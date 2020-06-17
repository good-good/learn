package com.atgp.day2

import org.apache.flink.api.common.functions.FilterFunction
import org.apache.flink.streaming.api.scala._

object FilterExample {
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)

        val stream = env.addSource(new SensorSource)

        stream.filter(new FilterFunction[SensorReading] {
            override def filter(t: SensorReading): Boolean = t.id.equals("sendor9")
        })
                .print

        env.execute()
    }

}
