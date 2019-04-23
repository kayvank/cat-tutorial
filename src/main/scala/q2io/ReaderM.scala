package q2io

import cats.implicits._
import cats.data._

object ReaderM {
  trait Context
  trait Topic
  trait Result
  val lambdaF : Int ⇒ Int = lambda ⇒ lambda + 1

  class Hole{}

  case class DeployResult(
    id: String,
    status: String
  ) extends Result

  case class SongContext(
    id: String,
    uri: String,
    topic: String
  ) extends Context

  case class ArtContext(
    id: String,
    uri: String,
    topic: String
  ) extends Context

  case class SongTopic(
    songName: String,
    id: String
  ) extends Topic

  type Job[A] = Reader[Context, A]

  def deploy[A](id: A): DeployResult = 
    DeployResult(s"$id", "Success")
  
  def processTopic(topic: SongTopic): Job[Result] =
    Reader(SongContext ⇒ deploy(topic.id))

  def processTopics(topics: List[SongTopic]): Job[List[Result]] =
    topics.traverse(processTopic)
  
}
