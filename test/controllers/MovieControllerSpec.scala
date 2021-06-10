import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.http.Status
import play.api.test.FakeRequest
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.mvc.AnyContentAsJson
import play.api.test.FakeHeaders
import play.api.libs.json.JsValue
import models.Movie

class MovieControllerSpec extends PlaySpec with GuiceOneAppPerSuite  {
  var id = ""

  "Routes" should {
    "receive 404" in  {
      route(app, FakeRequest(GET, "/test")).map(status(_)) mustBe Some(NOT_FOUND)
    }

    "receive 200" in  {
      route(app, FakeRequest(GET, "/movies")).map(status(_)) mustBe Some(OK)
    }
  }

  "MovieController" should {
    "should create resource" in {
      val response = route(app, FakeRequest(POST, "/movies", FakeHeaders(), AnyContentAsJson(Json.parse("""{"title":"My favorite movie", "description":"My favorite movie description"}""")))).get

      status(response) mustBe 201

      contentAsString(response) must include ("My favorite movie")
    }

    "findAll be successful" in {
      val response = route(app, FakeRequest(GET, "/movies")).get

      status(response) mustBe Status.OK
      contentType(response) mustBe Some("application/json")
    }

    "findOne receive 400" in {
      val response = route(app, FakeRequest(GET, "/movies/1")).get

      status(response) mustBe 400
    }

    "delete receive 400" in {
      val response = route(app, FakeRequest(DELETE, "/movies/1")).get

      status(response) mustBe 400
    }

    "update receive 404" in {
      val response = route(app, FakeRequest(PATCH, "/movies/1")).get

      status(response) mustBe 404
    }
  }
}