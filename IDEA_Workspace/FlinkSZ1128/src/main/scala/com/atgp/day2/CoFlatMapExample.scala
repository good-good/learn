package com.atgp.day2

import org.apache.flink.streaming.api.functions.co.CoFlatMapFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.util.Collector

object CoFlatMapExample {
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)

        val one = env.fromElements((1, 1L))
        val two = env.fromElements((1, "two"))

        val connected = one.keyBy(_._1)
                .connect(two.keyBy(_._1))
        val value = connected.flatMap(new MyFlatComap)
        value.print

        env.execute()
    }

    class MyFlatComap extends CoFlatMapFunction[(Int,Long),(Int,String),String]{
        override def flatMap1(value: (Int, Long), out: Collector[String]): Unit = {
            out.collect(value._2.toString + "来自第一条流")
            out.collect(value._2.toString + "来自第一条流")
        }

        override def flatMap2(value: (Int, String), out: Collector[String]): Unit = {
            out.collect(value._2 + "来自第二条流")
        }
    }


}
