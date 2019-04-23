package q2io.free

import cats.free.FreeApplicative
import cats.free.FreeApplicative.lift

object ApplicativcesImpl {
  type Validation[A] = FreeApplicative[ValidationOp, A]

  def size(size: Int):Validation[Boolean] = lift(Size(size))
  val hasNumber: validation[Boolean] = lift(HasNumber)
}

