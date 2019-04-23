package q2io

import cats.Semigroup
import cats.Functor
import cats.Apply
import cats.Applicative
import cats.Monad
import cats._
import cats.data._
import cats.Foldable
import cats.implicits._
import org.specs2._

class TypesSpecs extends Specification {
  def is = s2"""
  TypesSpecs
     semigroup should combine optins/ints/Either $e1
     semigroup should combine maps $e2
     lift functions to functors $e3
     functions products $e4
     functions compose $e5
     appy ap , <*> [A, B](ff: F(A) ⇒ B])(fa: F[A]): F[B] $e6
     applicative  extends apply by adding def pure[A](x:A) ⇒ F[A] $e7
     Monad extends applicative by adding def flatten $e8
     Traver $e9
     Reader $e10
     traverse-again $e11
     writer monad $e12
     reader-deploy-monad $e13
"""

  def parseIntEither(s: String): Either[NumberFormatException, Int] = {
    println(s"s = $s")
    Either.catchOnly[NumberFormatException](s.toInt)
  }
  def parseIntValidated(s: String): Validated[NumberFormatException, Int] =
    Validated.catchOnly[NumberFormatException](s.toInt)

  def e1 = {
    val either_3: Either[String, Int] = Right(3)
    Semigroup[Int].combine(1, 2) === 3
    Semigroup[Option[Int]].combine(Some(1), Some(2)) === Some(3)
    Semigroup[Either[String, Int]].combine(Right(1), Right(2)) === either_3
  }

  def e2 =
    Semigroup[Map[String, Int]].combine(
      Map("1" → 1, "2" → 2),
      Map("3" → 3, "4" → 4)
    ) === Map("1" → 1, "2" → 2, "3" → 3, "4" → 4)

  def e3 = {
    val lenOption: Option[String] ⇒ Option[Int] = Functor[Option].lift(_.length)
    lenOption(Some("abcd")) === Some(4)
  }

  def e4 = {
    // def fproduct(fa: F[A])(f: (A) ⇒ B) : F[(A, B)]
    val source = List("cats", "is", "awsome")
    val product = Functor[List].fproduct(source)(_.length).toMap
    product === Map(
      "cats" → "cats".length,
      "is" → "is".length,
      "awsome" → "awsome".length
    )
  }

  def e5 = {
    val listOps = Functor[List] compose Functor[Option]
    val res = listOps.map(List(Some(1), Some(2)))(_ + 1)
    res === List(Some(2), Some(3))
  }

  def e6 = {
    val intToString: Int ⇒ String = i ⇒ s"$i"
    Apply[Option].ap(Some(intToString))(Some(1)) === Some("1")
    Apply[Option].ap(Some((x: Int) ⇒ s"-$x-"))(Some(1)) === Some("-1-")
    Apply[Option].tuple2(Some(1), Some(2)) === Some((1, 2))
    Apply[Option].map3(Some(1), Some(2), Some(3))(
      (a: Int, b: Int, c: Int) ⇒ a + b + c
    ) === Some(6)
    (Option(1), Option(2), Option(3))
      .mapN((x: Int, y: Int, z: Int) ⇒ (x + y) * z) === Some(9)
  }

  def e7 = {
    case class Address(street: String)
    val address = Address("3rd street")
    Applicative[Option].pure(1) === Some(1)
    Applicative[List].pure(1) === List(1)
    Applicative[List].pure(address) === List(address)
  }

  def e8 = {
    Monad[List].flatten(List(List(1), List(2))) === List(1, 2)
    Monad[List].flatMap(List(1, 2, 3))(x ⇒ List(x, x)) == List(1, 1, 2, 2, 3, 3)
    Monad[Option].pure(1) === Some(1)
    Monad[Option].ifM(Option(false))(Option("truthy"), Option("falsly")) === Option(
      "falsly"
    )
  }

  def e9 = {
    val F = Foldable[List]
    val res = F.traverse_(List("1", "2", "3"))(parseIntEither)
    val res2 = F.traverse_(List("1", "2", "x"))(parseIntEither)
    println(s"""---------res- = ${res}""")
    println(s"""---------res2- = ${res2}""")
    println(s"""---------- = ${parseIntEither("X")}""")
    val actual: Either[NumberFormatException, Int] = Right(1)
    actual.right === parseIntEither("1").right
  }
  def e10 = {
    val f = (_: Int) * 2
    val g = (_: Int) + 10
    val h = (f, g) mapN { _ + _ }
    h(3) === 19
  }
  def e11 = {
    val resSome = List(1, 2, 3, 4) traverse { (x: Int) ⇒
      (Some(x + 1): Option[Int])
    }
    val resNone = List(1, 2, 3, 4) traverse { (x: Int) ⇒
      None
    }
    Some(List(2, 3, 4, 5)) === resSome
    None === resNone
  }
  def e12 = {
    val x = Writer(Vector("some intermediate computation"), 3)
    val y = Writer(Vector("another computation"), 5)
    val z = for {
      a ← x
      b ← y
    } yield (a + b)
    val zz = z.run
    println(s"---z = ${z.value} zz = ${zz}")
    z.value === 8 

  }
  def e13 = {
    import ReaderM._

    val songId = "id1"
    val songTopic: SongTopic = SongTopic(songName="how deep is your love", id=songId)
    val data = (1 to 5).map(
      x ⇒ songTopic.copy(songName = s"${songTopic.songName}-version-${x}")
    )
    val z: Job[Result] = processTopic(data.toList.head)
    val songContext= SongContext(songId, "myUri", "myTopic")
    val zz: Result = z.run(songContext)
    zz === DeployResult(id=songContext.id, status="Success")
  }
}
