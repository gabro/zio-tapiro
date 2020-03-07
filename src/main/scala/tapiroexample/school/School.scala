package tapiroexample.school

import io.circe.generic.JsonCodec

@JsonCodec final case class School(
    name: String,
    location: Location
)
