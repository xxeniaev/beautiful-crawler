import cats.effect._
import cats.implicits._
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.circe._
import org.http4s.ember.server._
import org.http4s.server.Router
import io.circe.generic.auto._
import org.jsoup.Jsoup
import io.circe.syntax._
import com.comcast.ip4s._

object Crawler extends IOApp {

  case class UrlRequest(urls: List[String])
  case class UrlResponse(results: Map[String, String])

  implicit val urlRequestDecoder: EntityDecoder[IO, UrlRequest] =
    jsonOf[IO, UrlRequest]
  implicit val urlResponseEncoder: EntityEncoder[IO, UrlResponse] =
    jsonEncoderOf[IO, UrlResponse]

  def fetchTitle(url: String): IO[(String, String)] = IO {
    val doc = Jsoup.connect(url).get()
    val title = doc.title()
    (url, title)
  }.handleError { case ex: Throwable =>
    (url, s"Error: ${ex.getMessage}")
  }

  def routes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case req @ POST -> Root / "extract-titles" =>
      for {
        input <- req.as[UrlRequest]
        results <- input.urls.traverse(fetchTitle)
        response <- Ok(UrlResponse(results.toMap).asJson)
      } yield response
  }

  def httpApp: HttpApp[IO] = Router("/" -> routes).orNotFound

  def run(args: List[String]): IO[ExitCode] =
    EmberServerBuilder
      .default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8080")
      .withHttpApp(httpApp)
      .build
      .use(_ => IO.never)
      .as(ExitCode.Success)
}
