//----------------------------------------------------------
//  This code was generated by tapiro.
//  Changes to this file may cause incorrect behavior
//  and will be lost if the code is regenerated.
//----------------------------------------------------------

package tapiroexample
import tapiroexample.school._
import sttp.tapir._
import sttp.tapir.Codec.{JsonCodec, PlainCodec}
import sttp.model.StatusCode

trait SchoolControllerEndpoints[AuthToken] {
  val list: Endpoint[Unit, Nothing, List[School], Nothing]
  val read: Endpoint[String, SchoolReadError, School, Nothing]
  val create: Endpoint[School, SchoolCreateError, Unit, Nothing]
}

object SchoolControllerEndpoints {

  def create[AuthToken](
      statusCodes: String => StatusCode = _ => StatusCode.UnprocessableEntity
  )(
      implicit codec1: JsonCodec[List[School]],
      codec2: JsonCodec[School],
      codec3: JsonCodec[SchoolReadError.NotFound.type],
      codec4: JsonCodec[SchoolCreateError.InvalidLocation.type],
      codec5: PlainCodec[String]
  ) = new SchoolControllerEndpoints[AuthToken] {
    override val list: Endpoint[Unit, Nothing, List[School], Nothing] =
      infallibleEndpoint.get
        .in("list")
        .out(jsonBody[List[School]])
    override val read: Endpoint[String, SchoolReadError, School, Nothing] =
      endpoint.get
        .in("read")
        .in(query[String]("name"))
        .errorOut(
          oneOf[SchoolReadError](
            statusMapping(
              statusCodes("NotFound"),
              jsonBody[SchoolReadError.NotFound.type]
            )
          )
        )
        .out(jsonBody[School])
    override val create: Endpoint[School, SchoolCreateError, Unit, Nothing] =
      endpoint.post
        .in("create")
        .in(jsonBody[School])
        .errorOut(
          oneOf[SchoolCreateError](
            statusMapping(
              statusCodes("InvalidLocation"),
              jsonBody[SchoolCreateError.InvalidLocation.type]
            )
          )
        )
  }
}