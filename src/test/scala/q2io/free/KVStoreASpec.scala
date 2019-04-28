package q2io.free

import org.specs2._
import q2io.free.KVStoreA._
import q2io.free.CompilerImpure._

class KVStoreASpec extends Specification { def is = s2"""
  Free Monad spces on KVStoreA impl
     impure compiler shoud store and get values $e1
"""
  def e1 = {
    val c = ic
    c(Put("One", 1))
    val res = c(Get("One") )
    res must beSome(1)
  } 
}
