package tapiroexample.school

import io.circe.generic.JsonCodec

@JsonCodec sealed trait SchoolCreateError

object SchoolCreateError {
  case object InvalidLocation extends SchoolCreateError
}
