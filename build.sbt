import sbt._

name := "yr-scala-client"

organization := "com.freddd"

version := "1.0.0-SNAPSHOT"

homepage := Some(url("https://github.com/freddd/yr-scala-client"))

startYear := Some(2013)

licenses := Seq(
  ("GPLv3", url("http://www.gnu.org/licenses/gpl-3.0.txt"))
)

scmInfo := Some(
  ScmInfo(
    url("https://github.com/freddd/yr-scala-client"),
    "scm:git:https://github.com/freddd/yr-scala-client.git",
    Some("scm:git:git@github.com:freddd/yr-scala-client.git")
  )
)

/* scala versions and options */
scalaVersion := "2.10.1"

crossScalaVersions := Seq(
  "2.8.0", "2.8.1", "2.8.2",
  "2.9.0", "2.9.0-1",
  "2.9.1", "2.9.1-1",
  "2.9.2",
  "2.9.3"
)

// These options will be used for *all* versions.
scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
  "-encoding", "UTF-8"
)

scalacOptions ++= Seq(
  "-Yclosure-elim",
  "-Yinline"
)

scalacOptions <++= scalaVersion map { sv =>
  if (sv startsWith "2.10") List(
    "-Xverify",
    "-Ywarn-all",
    "-feature"
  )
  else Nil
}

javacOptions ++= Seq("-Xlint:unchecked", "-Xlint:deprecation")

/* dependencies */
libraryDependencies += "org.scalatest" % "scalatest_2.10" % "1.9.1" % "test"

libraryDependencies += "com.typesafe" %% "scalalogging-slf4j" % "1.0.1"

libraryDependencies += "net.databinder.dispatch" %% "dispatch-core" % "0.10.0"

libraryDependencies += "com.thoughtworks.xstream" % "xstream" % "1.4.3"

libraryDependencies += "org.codehaus.jettison" % "jettison" % "1.3.3"

libraryDependencies += "joda-time" % "joda-time" % "2.1"

libraryDependencies += "org.joda" % "joda-convert" % "1.3.1"

resolvers ++= Seq(
  "releases"  at "http://oss.sonatype.org/content/repositories/releases",
  "sonatype-public" at "https://oss.sonatype.org/​content/repositories/public",
  "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/"
)

/* testing */
parallelExecution in Test := false

/* sbt behavior */
logLevel in compile := Level.Warn

traceLevel := 5

offline := false

/* publishing */
publishMavenStyle := true

publishTo <<= version { (v: String) =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT")) Some(
    "snapshots" at nexus + "content/repositories/snapshots"
  )
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

mappings in (Compile, packageBin) ~= { (ms: Seq[(File, String)]) =>
  ms filter { case (file, toPath) =>
      toPath != "application.conf"
  }
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
  <developers>
    <developer>
      <id>freddd</id>
      <name>Fredrik Joensson</name>
      <email>fjnsson@gmail.com</email>
      <url>https://github.com/freddd</url>
    </developer>
  </developers>
)

assemblySettings

test in AssemblyKeys.assembly := {}

