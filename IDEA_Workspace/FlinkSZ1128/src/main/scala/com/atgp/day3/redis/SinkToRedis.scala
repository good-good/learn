package com.atgp.day3.redis

import com.atgp.day2.{SensorReading, SensorSource}
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.redis.RedisSink
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisPoolConfig
import org.apache.flink.streaming.connectors.redis.common.mapper.{RedisCommand, RedisCommandDescription, RedisMapper}

object SinkToRedis {
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)

        val stream = env.addSource(new SensorSource)

        //配置redis的主机
        val conf = new FlinkJedisPoolConfig.Builder().setHost("hadoop102").build()
        stream.addSink(new RedisSink[SensorReading](conf,new MyRedisMapper))


        env.execute()
    }

    class MyRedisMapper extends RedisMapper[SensorReading]{
        override def getCommandDescription: RedisCommandDescription = {
            new RedisCommandDescription(RedisCommand.HSET,"sensor")
        }

        override def getKeyFromData(t: SensorReading): String = t.id

        override def getValueFromData(t: SensorReading): String = t.temperature.toString
    }

}
