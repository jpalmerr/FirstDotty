val dottyVersion = "0.27.0-RC1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "dotty-simple",
    version := "3.0.0-RC2",

    scalaVersion := dottyVersion,

    libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"
  )
