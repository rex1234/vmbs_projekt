name := "vmbs_projekt"

version := "1.0"

lazy val `vmbs_projekt` = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq( javaJdbc , javaEbean , cache , javaWs )

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

libraryDependencies ++= Seq(
  "com.feth" % "play-authenticate_2.11" % "0.6.8",
  "com.google.http-client" % "google-http-client-jackson2" % "1.19.0",
  "mysql" % "mysql-connector-java" % "5.1.34",
  "joda-time" % "joda-time" % "2.7",
  "org.json" % "json" % "20090211",
  "org.julienrf" %% "play-jsmessages" % "1.6.2",
  "joda-time" % "joda-time" % "2.7",
  javaJdbc,
  javaEbean,
  cache,
  javaWs
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

resolvers ++= Seq(
  "Apache" at "http://repo1.maven.org/maven2/",
  "jBCrypt Repository" at "http://repo1.maven.org/maven2/org/",
  "play-easymail (release)" at "http://joscha.github.io/play-easymail/repo/releases/",
  "play-easymail (snapshot)" at "http://joscha.github.io/play-easymail/repo/snapshots/",
  "play-authenticate (release)" at "http://joscha.github.io/play-authenticate/repo/releases/",
  "play-authenticate (snapshot)" at "http://joscha.github.io/play-authenticate/repo/snapshots/"
)