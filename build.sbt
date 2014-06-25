name := "rest-cli"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.11.1"

libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.3.4"

libraryDependencies += "commons-codec" % "commons-codec" % "1.9"

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.3.1"