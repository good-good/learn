package com.atgp.day2

import org.apache.flink.streaming.api.scala._

object KeybyExample {
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)

        val stream = env
                .addSource(new SensorSource)
                .keyBy(value=>value.id)
                .min(2).print()
        env.execute()

    }

}
