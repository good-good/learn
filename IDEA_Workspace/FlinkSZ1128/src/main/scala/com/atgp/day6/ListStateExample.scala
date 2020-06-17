package com.atgp.day6

import com.atgp.day2.{SensorReading, SensorSource}
import org.apache.flink.api.common.state.{ListState, ListStateDescriptor, ValueState, ValueStateDescriptor}
import org.apache.flink.api.scala.typeutils.Types
import org.apache.flink.configuration.Configuration
import org.apache.flink.runtime.state.filesystem.FsStateBackend
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.util.Collector
import scala.collection.mutable.ListBuffer

object ListStateExample {
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)
        env.enableCheckpointing(10000L)
        env.setStateBackend(new FsStateBackend("file:///F:\\workspace\\IDEA_Workspace\\FlinkSZ1128\\checkpointFile"))

        val stream = env
                .addSource(new SensorSource)
                .filter(_.id.equals("sendor1"))
                .keyBy(_.id)
                .process(new MyKeyedProcess)
        stream.print()
        env.execute()
    }

    class MyKeyedProcess extends KeyedProcessFunction[String,SensorReading,String]{
        var listState:ListState[SensorReading] = _
        var timerTs:ValueState[Long] = _
        /*

         */
        override def open(parameters: Configuration): Unit = {

            listState = getRuntimeContext.getListState(
                new ListStateDescriptor[SensorReading]("list-state",Types.of[SensorReading]))

            timerTs = getRuntimeContext.getState(
                new ValueStateDescriptor[Long]("timer", Types.of[Long])
            )

        }

        override def onTimer(timestamp: Long, ctx: KeyedProcessFunction[String, SensorReading, String]#OnTimerContext, out: Collector[String]): Unit = {
            //初始化一个空列表
            val list:ListBuffer[SensorReading] = ListBuffer()
            import scala.collection.JavaConversions._
            for(r <- listState.get()){
                list += r
            }

            listState.clear()

            out.collect("列表状态变量中的元素数量有： " + list.size + " 个！")
            timerTs.clear()
        }

        override def processElement(value: SensorReading, ctx: KeyedProcessFunction[String, SensorReading, String]#Context, out: Collector[String]): Unit = {
            //将来的传感器数据添加到列表状态变量
            listState.add(value)
            if(timerTs.value() == 0L){//timer的开始
                val ts = ctx.timerService().currentProcessingTime() + 10 * 1000L
                ctx.timerService().registerProcessingTimeTimer(ts)
                timerTs.update(ts)
            }

        }
    }

}
