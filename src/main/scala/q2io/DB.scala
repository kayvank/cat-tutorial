package q2io

import cats.data.{Reader, Writer}
import cats.instances.vector._

object ReaderToRescue {
  type Key = String
  trait Transaction
  type Work[A] = Reader[Transaction, A]

  object DB {

    object MyTransaction extends Transaction

    def run[T](work: Work[T]): T =
      try {
        startTransaction()
        val result = work.run(MyTransaction)
        commit()
        result
      } catch {
        case whatever: Throwable ⇒ rollback(); throw whatever
      }
    def startTransaction() = {}
    def commit() = {}
    def rollback() = {}
    def put[A](key: Key, a: A): Work[Unit] =
      Reader(Transaction ⇒ {})
    def find[A](key: Key): Work[Option[A]] =
      Reader(Transaction ⇒ None)
  }
  val work: Work[Option[String]] =
    for {
      _ ← DB.put("foo", "Bar")
      found ← DB.find[String]("foo")
    } yield (found)

  val result: Option[String] = DB.run(work)

  val x = Writer(Vector("some intermediate computation"), 3)
  val y = Writer(Vector("another computation"), 5)
  val z = for {
    a ← x
    b ← y
  } yield (a + b)
  println(s"---z = ${z.value}")
}
