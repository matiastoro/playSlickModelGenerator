name := "playSlickCrudGenerator"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.2",
  "com.typesafe" % "config" % "1.4.0",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2"
)
