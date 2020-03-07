package tapiroexample.school

import scala.annotation.StaticAnnotation

import zio.Task

class query extends StaticAnnotation
class command extends StaticAnnotation

trait SchoolController[+F[_], T] {
  @query
  def list(): F[Either[Nothing, List[School]]]

  @query
  def read(name: String): F[Either[SchoolReadError, School]]

  @command
  def create(school: School): F[Either[SchoolCreateError, Unit]]
}

object SchoolController {

  def create(schoolService: SchoolService) =
    new SchoolController[Task, String] {

      override def list(): Task[Either[Nothing, List[School]]] =
        schoolService.list().map(Right(_))

      override def read(name: String): Task[Either[SchoolReadError, School]] =
        schoolService.read(name).someOrFail(SchoolReadError.NotFound).either

      override def create(
          school: School
      ): Task[Either[SchoolCreateError, Unit]] =
        schoolService.create(school).either

    }
}
