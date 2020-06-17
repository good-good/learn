package com.atgp.day2

import org.apache.flink.streaming.api.scala._

object SplitExample {
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)

        val inputStream: DataStream[(Int, String)] = env
                .fromElements(
                    (1001, "1001"),
                    (999, "999")
                )
        val splitV = inputStream.split(t => if (t._1 > 1000) Seq("large") else Seq("small"))
        val large = splitV.select("large")
        val small = splitV.select("small")
        val all = splitV.select("small", "large")
        large.print

        env.execute()
    }

}
