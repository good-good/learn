package com.atgp.day2

import org.apache.flink.streaming.api.scala._

object SourceFromFile {
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)

        val Stream = env
                        .readTextFile("F:\\workspace\\IDEA_Workspace\\FlinkSZ1128\\src\\main\\resources\\sensor.txt")
                        .map(r=>{
                            val strings = r.split(",")
                            SensorReading(strings(0),strings(1).toLong,strings(2).toDouble)
                        })
        Stream.print

        env.execute()
    }

}
