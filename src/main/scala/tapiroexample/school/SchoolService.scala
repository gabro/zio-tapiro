package tapiroexample.school

import zio.UIO
import zio.IO
import zio.Ref

trait SchoolService {
  def list(): UIO[List[School]]
  def read(name: String): UIO[Option[School]]
  def create(school: School): IO[SchoolCreateError, Unit]
}

object SchoolService {
  def inMemory =
    Ref.make(Map.empty[String, School]).map { schools =>
      new SchoolService {

        override def list(): UIO[List[School]] =
          schools.get.map(_.values.toList)

        override def read(name: String): UIO[Option[School]] =
          schools.get.map(_.get(name))

        override def create(school: School): IO[SchoolCreateError, Unit] =
          schools.update(_.updated(school.name, school))

      }
    }
}
