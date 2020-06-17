package com.atgp.day2

import org.apache.flink.streaming.api.functions.co.CoMapFunction
import org.apache.flink.streaming.api.scala._

object CoMapExample {
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)

        val one = env.fromElements((1, 1L))
        val two = env.fromElements((2, "two"))

        val connected = one.keyBy(_._1)
                .connect(two.keyBy(_._1))
        val value = connected.map(new MyComap)
        value.print

        env.execute()
    }

    class MyComap extends CoMapFunction[(Int,Long),(Int,String),String]{
        override def map1(in1: (Int, Long)): String = {
            in1._2.toString+ "来自第一条流"
        }

        override def map2(in2: (Int, String)): String = in2._2+ "来自第二条流"
    }

}
