package com.atgp.day1

import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time

object WordCount {
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment

        //并行任务数量为1
        env.setParallelism(1)

        //数据源来自于Socket端
        val stream = env.socketTextStream("hadoop102", 9999)

        //对数据流进行转换算子
        val textStream = stream
                .flatMap(r => r.split("\\s"))
                .map(w => WordWithCount(w, 1))
                .keyBy("word")
                .timeWindow(Time.seconds(5))
                .sum("count")

        textStream.print

        env.execute()
    }

    case class WordWithCount(word: String, count: Int)

}
