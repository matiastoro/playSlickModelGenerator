package util

import play.api.Logger

object Via56Logging {
  val logger: Logger = Logger("via56")

  def log(objs: Any*) = {
    val stackTrace = ((new Throwable).getStackTrace)
    logger.info(stackTrace(1).getClassName + " L"+stackTrace(1).getLineNumber + " " + objs.foldRight(" ")(_.toString + _))
  }
  def error(objs: Any*) = {
    val stackTrace = ((new Throwable).getStackTrace)
    logger.error(stackTrace(1).getClassName + " L"+stackTrace(1).getLineNumber + " " + objs.foldRight(" ")(_.toString + _))
  }
  def warning(objs: Any*) = {
    val stackTrace = ((new Throwable).getStackTrace)
    logger.warn(stackTrace(1).getClassName + " L"+stackTrace(1).getLineNumber + " " + objs.foldRight(" ")(_.toString + _))
  }
  def debug(objs: Any*) = {
    val stackTrace = ((new Throwable).getStackTrace)
    logger.debug(stackTrace(1).getClassName + " L"+stackTrace(1).getLineNumber + " " + objs.foldRight(" ")(_.toString + _))
  }
  def trace(objs: Any*) = {
    val stackTrace = ((new Throwable).getStackTrace)
    logger.trace(stackTrace(1).getClassName + " L"+stackTrace(1).getLineNumber + " " + objs.foldRight(" ")(_.toString + _))
  }

}