package com.atgp.day2

import org.apache.flink.streaming.api.scala._

object UnionExample {
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)

        val stream1 = env.addSource(new SensorSource)
                .filter(r=> r.id.equals("sendor1"))
        val stream2 = env.addSource(new SensorSource)
                .filter(r=> r.id.equals("sendor2"))
        val stream3 = env.addSource(new SensorSource)
                .filter(r=> r.id.equals("sendor3"))

        val unions = stream1.union(stream2, stream3)
        unions.print

        env.execute()
    }

}
