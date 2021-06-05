import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.http.Status
import play.api.test.FakeRequest
import play.api.test.Helpers._

class MovieControllerSpec extends PlaySpec with GuiceOneAppPerSuite {

  "Routes" should {
    "receive 404" in  {
      route(app, FakeRequest(GET, "/test")).map(status(_)) mustBe Some(NOT_FOUND)
    }

    "receive 200" in  {
      route(app, FakeRequest(GET, "/movies")).map(status(_)) mustBe Some(OK)
    }
  }

  "MovieController" should {
    "render the index page" in {
      val response = route(app, FakeRequest(GET, "/movies")).get

      status(response) mustBe Status.OK
      contentType(response) mustBe Some("application/json")
    }
  }
}