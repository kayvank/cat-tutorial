package q2io

import FreeMonads._
import org.specs2._

class FreeMonadsSpec extends Specification {
  def is = s2"""
    FreeMonad spsecs
     functionK Option specs $e1
     functionK empty list should return Left $e2
     functionK non empty list should return Right $e3
  """
  def e1 = Some(1) === first(List(1,2,3))

  def e2 = errorOrFirst(Nil) must beLeft
  def e3 = errorOrFirst(List(1)) must beRight
}
