package q2io.free

import cats.free.Free
import KVStoreA._

object KVStoreALift {
  type KVStore[A] = Free[KVStoreA, A]

  def put[T](key: String, value: T): KVStore[Unit] = ???
  def get[T](key: String): KVStore[Option[T]] = ???
  def delete(key: String): KVStore[Unit] = ???
  def update[T](key: String, f: T ⇒ T): KVStore[Unit] = ???

  def program: KVStore[Option[Int]] = for {
    _ ← put("wild-cats", 2)
    _ ← update[Int]("wild-cats", (_ + 12))
    n ← get[Int]("wild-cats")
    _ ← delete ("wild-cats")
  } yield n
}
