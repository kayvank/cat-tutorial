package q2io

import cats.~>
import cats.arrow.FunctionK


object FreeMonads {
  class hole{}
  type ErrorOr[A] = Either[String, A]

  val errorOrFirst: FunctionK[List, ErrorOr] = λ[FunctionK[List, ErrorOr]] (
    _.headOption.toRight("ERROR: the list was empty!")
  )

  val first: List ~> Option = λ [List ~> Option](_.headOption)
}

