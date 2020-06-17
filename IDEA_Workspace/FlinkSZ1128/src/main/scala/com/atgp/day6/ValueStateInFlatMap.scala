package com.atgp.day6

import com.atgp.day2.{SensorReading, SensorSource}
import org.apache.flink.api.common.functions.RichFlatMapFunction
import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.scala._
import org.apache.flink.util.Collector

object ValueStateInFlatMap {
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)

        val stream = env
                .addSource(new SensorSource)
                .flatMap(new TemperatureAlert)
        stream.print()

        env.execute()
    }

    class TemperatureAlert extends RichFlatMapFunction[SensorReading,String]{
        private var lastTmep:ValueState[SensorReading] = _


        override def open(parameters: Configuration): Unit = {
            lastTmep = getRuntimeContext.getState(
                new ValueStateDescriptor[SensorReading]("last-temp",classOf[SensorReading])
            )
        }

        override def flatMap(value: SensorReading, out: Collector[String]): Unit = {
            val last = lastTmep.value()

        }
    }

}
