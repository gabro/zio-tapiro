inThisBuild(
  List(
    scalaVersion := "2.13.1"
  )
)

lazy val root = project
  .in(file("."))
  .settings(
    libraryDependencies ++= List(
      "dev.zio" %% "zio" % "1.0.0-RC18-1",
      "dev.zio" %% "zio-interop-cats" % "2.0.0.0-RC11",
      "org.http4s" %% "http4s-blaze-server" % "0.21.1",
      "org.http4s" %% "http4s-circe" % "0.21.1",
      "io.circe" %% "circe-generic" % "0.13.0",
      "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % "0.12.23",
      "com.softwaremill.sttp.tapir" %% "tapir-core" % "0.12.23",
      "com.softwaremill.sttp.tapir" %% "tapir-http4s-server" % "0.12.23"
    ),
    tapiro / tapiroRoutesPaths := List(""),
    tapiro / tapiroModelsPaths := List(""),
    tapiro / tapiroOutputPath := "tapiroexample/endpoints",
    tapiro / tapiroEndpointsPackages := List("tapiroexample"),
    tapiro / tapiroServer := _root_.io.buildo.tapiro.Server.Http4s
  )
  .enablePlugins(SbtTapiro)
