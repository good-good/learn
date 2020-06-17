package com.atgp.day5

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time

object WindowJoin {
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

        val orangeStream = env
                .fromElements((1, 1999L), (1, 2001L))
                .assignAscendingTimestamps(_._2)

        val greenStream = env
                .fromElements((1, 1001L), (1, 2002L))
                .assignAscendingTimestamps(_._2)

        orangeStream.join(greenStream)
                .where(_._1)
                .equalTo(_._1)
                .window(TumblingEventTimeWindows.of(Time.seconds(2)))
                .apply((e1,e2) => e1 +" --- " +e2)
                .print()

        env.execute()
    }

}
