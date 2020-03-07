package tapiroexample.school

import io.circe.generic.JsonCodec

@JsonCodec sealed trait SchoolReadError

object SchoolReadError {
  case object NotFound extends SchoolReadError
}
