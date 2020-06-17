package com.atgp.day2

import java.util.Calendar
import org.apache.flink.streaming.api.functions.source.{RichParallelSourceFunction, SourceFunction}
import scala.util.Random

class SensorSource extends RichParallelSourceFunction[SensorReading]{
    var running:Boolean = true
    override def run(sourceContext: SourceFunction.SourceContext[SensorReading]): Unit = {
        //产生随机的温度读数
        val rand = new Random
        var curTemp = (1 to 10).map(i =>
            ("sendor" + i, 65+ (rand.nextGaussian() * 20))
        )

        while (running){
            curTemp = curTemp.map(t=> (t._1,t._2 +(rand.nextGaussian() *0.5)))

            val curTime = Calendar.getInstance.getTimeInMillis
            curTemp.foreach(t=>sourceContext.collect(SensorReading(t._1,curTime,t._2)))

            Thread.sleep(100)
        }
    }



    override def cancel(): Unit = running = false
}
