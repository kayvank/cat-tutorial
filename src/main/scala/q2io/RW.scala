package q2io

import cats.syntax.applicative._
import cats.syntax.writer._
import cats.data._

trait Repo {
  def deploy[A](a: A): Either[Throwable, String]
  def propose[A](): Either[Throwable, A]
}
object RW {
  case class Asset(name: String, url: String, metadata: String)
  type AssetReader[A] = Reader[Asset, A]

}
