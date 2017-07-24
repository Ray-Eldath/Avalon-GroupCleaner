package ray.eldath.avalon.gc.util

import java.io.File

object Constant {
  val _CURRENT_PATH: String = new File("").getCanonicalPath
  val _KICK_NEXT_TIME_LIST: File = new File(_CURRENT_PATH + "/remove.json")
  val _DEBUG: Boolean = true
}