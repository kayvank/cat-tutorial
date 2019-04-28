package q2io.free

sealed abstract class ValidationOps[A]
case class Size(size: Int) extends ValidationOps[Boolean]
case object HasNumber extends ValidationOps[Boolean]
