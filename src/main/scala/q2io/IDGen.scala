package q2io

import javax.xml.bind.annotation.adapters.HexBinaryAdapter

object IdGen {

  private final val makeId = java.security.MessageDigest.getInstance("MD5")
  val idGen: String => String = id =>
    (new HexBinaryAdapter)
      .marshal(makeId.digest(id.getBytes))
      .take(40)
      .toString
      .toLowerCase
}
