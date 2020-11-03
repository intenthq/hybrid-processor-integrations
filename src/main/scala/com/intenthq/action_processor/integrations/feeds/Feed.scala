package com.intenthq.action_processor.integrations.feeds

import cats.effect.IO

trait Feed[I, O] {

  def inputStream(feedContext: FeedContext[IO]): fs2.Stream[IO, I]
  def transform(feedContext: FeedContext[IO]): fs2.Pipe[IO, I, (O, Long)]
  def serialize(o: O, counter: Long): Array[Byte]

  final def stream(feedContext: FeedContext[IO]): fs2.Stream[IO, Array[Byte]] =
    inputStream(feedContext)
      .through(transform(feedContext))
      .map((serialize _).tupled)
}