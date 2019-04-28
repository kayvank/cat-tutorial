package q2io.free

import cats.free.FreeApplicative
import cats.free.FreeApplicative.lift

object ApplicativcesImpl {
  type Validation[A] = FreeApplicative[ValidationOps, A]

  def size(size: Int):Validation[Boolean] = lift(Size(size))
  val hasNumber: Validation[Boolean] = lift(HasNumber)
}

