package com.atgp.day5

import org.apache.flink.streaming.api.functions.co.ProcessJoinFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.util.Collector

object IntervalJoinExample {
    case class UserClickLog(userID: String,
                            eventTime: String,
                            eventType: String,
                            pageID: String)

    case class UserBrowseLog(userID: String,
                             eventTime: String,
                             eventType: String,
                             productID: String,
                             productPrice: String)

    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)

        val clickStream = env
                .fromElements(
                    UserClickLog("user_2", "1500", "click", "page_1"),
                    UserClickLog("user_2", "2000", "click", "page_1")
                )
                .assignAscendingTimestamps(_.eventTime.toLong*1000L)
                .keyBy(_.userID)

        val browseStream = env
                .fromElements(
                    UserBrowseLog("user_2", "1000", "browse", "product_1","10"),
                    UserBrowseLog("user_2", "1500", "browse", "product_1","10"),
                    UserBrowseLog("user_2", "1501", "browse", "product_1","10"),
                    UserBrowseLog("user_2", "1502", "browse", "product_1","10"))
                .assignAscendingTimestamps(_.eventTime.toLong * 1000L)
                .keyBy(_.userID)

        clickStream
                .intervalJoin(browseStream)
                .between(Time.minutes(-10),Time.seconds(0))
                .process(new MyIntervalJoin)
                .print()

        env.execute()
    }

    class MyIntervalJoin extends ProcessJoinFunction[UserClickLog,UserBrowseLog,String]{
        override def processElement(left: UserClickLog, right: UserBrowseLog, ctx: ProcessJoinFunction[UserClickLog, UserBrowseLog, String]#Context, out: Collector[String]): Unit = {
            out.collect(left +" ==> "+ right)
        }
    }

}
