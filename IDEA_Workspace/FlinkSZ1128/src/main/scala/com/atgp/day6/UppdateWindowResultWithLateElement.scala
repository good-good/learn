package com.atgp.day6

import org.apache.flink.api.common.state.ValueStateDescriptor
import org.apache.flink.api.scala.typeutils.Types
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

object UppdateWindowResultWithLateElement {
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)


        val stream = env
                .socketTextStream("hadoop102", 9999, '\n')
                .map(line => {
                    val arr = line.split(" ")
                    (arr(0), arr(1).toLong * 1000L)
                })
                .assignTimestampsAndWatermarks(
                    new BoundedOutOfOrdernessTimestampExtractor[(String, Long)](Time.seconds(5)) {
                        override def extractTimestamp(element: (String, Long)): Long = element._2
                    }
                )
                .keyBy(_._1)
                .timeWindow(Time.seconds(5))
                .allowedLateness(Time.seconds(5))
                .process(new UpdateWindowFunction)
        stream.print()
        env.execute()
    }

    class UpdateWindowFunction extends  ProcessWindowFunction[(String,Long),String,String,TimeWindow]{
        override def process(key: String, context: Context, elements: Iterable[(String, Long)], out: Collector[String]): Unit = {
            val count = elements.size
            //基于窗口的状态变量，仅当前窗口可见
            val isUpdate = context.windowState.getState(
                new ValueStateDescriptor[Boolean]("is-update",Types.of[Boolean])
            )

            if(!isUpdate.value()){
                out.collect("当水位线超过窗口结束时间的时候，窗口第一次触发计算，元素数量是： "+ count+" 个！")
                isUpdate.update(true)
            }else{
                out.collect("迟到元素来了！ 元素数量是： "+count+" 个！")
            }

        }
    }

}
