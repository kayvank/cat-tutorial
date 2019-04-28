package q2io.free
 
import cats.arrow._ //FunctionK
import cats.{Id, ~>}
import scala.collection.mutable 
import KVStoreA._

object CompilerImpure {
  def ic: KVStoreA ~> Id =
    new (KVStoreA ~> Id) {
      val kvs = mutable.Map.empty[String, Any]
      def apply[A](fa: KVStoreA[A]): Id[A] = fa match {
        case Put(key, value) ⇒
          println(s"put($key, $value)")
          kvs(key)=value
          ()
        case Get(key) ⇒
          println(s"get ($key)")
          kvs.get(key).map(_.asInstanceOf[A])
        case Delete(key) ⇒
          println(s"delete ($key)")
          kvs.remove("key")
          ()
      }
    }
}
