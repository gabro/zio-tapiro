package tapiroexample

import zio._
import zio.interop.catz._
import zio.clock.Clock
import zio.blocking.Blocking
import zio.console.putStrLn
import org.http4s.HttpRoutes
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import sttp.tapir.json.circe._
import cats.syntax.reducible._
import cats.data.NonEmptyList
// FIXME(gabro): how to provide error codecs?
import io.circe.generic.auto._

import tapiroexample.school.SchoolController
import tapiroexample.school.SchoolService

object Boot extends zio.App {

  type AppEnvironment = Clock with Blocking

  type AppTask[A] = RIO[AppEnvironment, A]

  override def run(args: List[String]): ZIO[ZEnv, Nothing, Int] = {

    val program = for {
      schoolService <- SchoolService.inMemory
      schoolController = SchoolController.create(schoolService)
      routes = NonEmptyList.of[HttpRoutes[AppTask]](
        SchoolControllerHttp4sEndpoints.routes(schoolController)
      )
      httpApp = routes.reduceK.orNotFound
      server <- ZIO.runtime[AppEnvironment].flatMap { implicit rts =>
        BlazeServerBuilder[AppTask]
          .bindHttp(8000, "0.0.0.0")
          .withHttpApp(httpApp)
          .serve
          .compile
          .drain
      }
    } yield server

    program.foldM(
      err => putStrLn(s"Execution failed with: $err") *> IO.succeed(1),
      _ => IO.succeed(0)
    )

  }

}
