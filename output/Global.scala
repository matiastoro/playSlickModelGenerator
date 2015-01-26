
import actors.Statictitian
import akka.actor.Props
import play.api.libs.concurrent.Akka

import play.api._

import play.api.Application

import play.api.Play.current
import models.db




object Global  extends GlobalSettings {


  override def onStart(app: Application) {
    println("Starting Application")
    startActor
  }

  override def onStop(app: Application) {
    println("Stopping Application")
  }


  lazy val startActor = {
    db.createDB
  }

}