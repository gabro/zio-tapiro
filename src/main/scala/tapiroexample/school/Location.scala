package tapiroexample.school

import io.circe.generic.JsonCodec

@JsonCodec final case class Location(
    lat: Double,
    lng: Double
)
