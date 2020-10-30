package com.intenthq.action_processor.integrationsV2.feeds

import cats.effect.IO
import com.intenthq.action_processor.integrations.{JavaLegacyTimeMeta, TimeMeta}
import doobie.util.transactor.{Strategy, Transactor}

import scala.util.Properties

trait HiveFeed[I, O] extends SQLFeed[I, O] with TimeMeta with JavaLegacyTimeMeta {
  override protected val driver: String = "org.apache.hive.jdbc.HiveDriver"
  override protected lazy val transactor: Transactor[IO] = Transactor.strategy.set(createTransactor, Strategy.void)
  override protected val jdbcUrl: String = Properties.envOrElse("HIVE_JDBC_URL", "jdbc:hive2://localhost:10000")
}