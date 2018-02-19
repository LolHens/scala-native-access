name := "Scala Native Access"
version := "0.0.0"
organization := "de.lolhens"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "com.github.jnr" % "jnr-ffi" % "2.1.7"
)