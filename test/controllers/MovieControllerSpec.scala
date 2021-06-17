package controllers

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
import org.joda.time.DateTime
import reactivemongo.bson.BSONObjectID

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
      val response0 = route(app, FakeRequest(POST, "/movies", FakeHeaders(), AnyContentAsJson(Json.parse(
        """{ "title":"best dog",
          |"description":"My favorite movie description",
          |"genres":"@@@, @@@, @@@, @@@, @@@, @@@", "countries":"@@@, @@@", "directors":"@@@, @@@, @@@, @@@",
          |"screenwriters":"@@@, @@@, @@@", "cast":"@@@, @@@", "coverURL":"@@@" }""".stripMargin)))).get
      val response1 = route(app, FakeRequest(POST, "/movies", FakeHeaders(), AnyContentAsJson(Json.parse(
        """{ "title":"best cat",
          |"description":"My favorite movie description",
          |"genres":"@@@, @@@, @@@, @@@, @@@, @@@", "countries":"@@@, @@@", "directors":"@@@, @@@, @@@, @@@",
          |"screenwriters":"@@@, @@@, @@@", "cast":"@@@, @@@", "coverURL":"@@@" }""".stripMargin)))).get
      val response2 = route(app, FakeRequest(POST, "/movies", FakeHeaders(), AnyContentAsJson(Json.parse(
        """{ "title":"my best dog",
          |"description":"My favorite movie description",
          |"genres":"@@@, @@@, @@@, @@@, @@@, @@@", "countries":"@@@, @@@", "directors":"@@@, @@@, @@@, @@@",
          |"screenwriters":"@@@, @@@, @@@", "cast":"@@@, @@@", "coverURL":"@@@" }""".stripMargin)))).get
      val response3 = route(app, FakeRequest(POST, "/movies", FakeHeaders(), AnyContentAsJson(Json.parse(
        """{ "title":"my best cat",
          |"description":"My favorite movie description",
          |"genres":"@@@, @@@, @@@, @@@, @@@, @@@", "countries":"@@@, @@@", "directors":"@@@, @@@, @@@, @@@",
          |"screenwriters":"@@@, @@@, @@@", "cast":"@@@, @@@", "coverURL":"@@@" }""".stripMargin)))).get
      val response4 = route(app, FakeRequest(POST, "/movies", FakeHeaders(), AnyContentAsJson(Json.parse(
        """{ "title":"best",
          |"description":"My favorite movie description",
          |"genres":"@@@, @@@, @@@, @@@, @@@, @@@", "countries":"@@@, @@@", "directors":"@@@, @@@, @@@, @@@",
          |"screenwriters":"@@@, @@@, @@@", "cast":"@@@, @@@", "coverURL":"@@@" }""".stripMargin)))).get
      val response5 = route(app, FakeRequest(POST, "/movies", FakeHeaders(), AnyContentAsJson(Json.parse(
        """{ "title":"my",
          |"description":"My favorite movie description",
          |"genres":"@@@, @@@, @@@, @@@, @@@, @@@", "countries":"@@@, @@@", "directors":"@@@, @@@, @@@, @@@",
          |"screenwriters":"@@@, @@@, @@@", "cast":"@@@, @@@", "coverURL":"@@@" }""".stripMargin)))).get
      val response6 = route(app, FakeRequest(POST, "/movies", FakeHeaders(), AnyContentAsJson(Json.parse(
        """{ "title":"dog and cat",
          |"description":"My favorite movie description",
          |"genres":"@@@, @@@, @@@, @@@, @@@, @@@", "countries":"@@@, @@@", "directors":"@@@, @@@, @@@, @@@",
          |"screenwriters":"@@@, @@@, @@@", "cast":"@@@, @@@", "coverURL":"@@@" }""".stripMargin)))).get
      val response7 = route(app, FakeRequest(POST, "/movies", FakeHeaders(), AnyContentAsJson(Json.parse(
        """{ "title":"cat",
          |"description":"My favorite movie description",
          |"genres":"@@@, @@@, @@@, @@@, @@@, @@@", "countries":"@@@, @@@", "directors":"@@@, @@@, @@@, @@@",
          |"screenwriters":"@@@, @@@, @@@", "cast":"@@@, @@@", "coverURL":"@@@" }""".stripMargin)))).get
      val response8 = route(app, FakeRequest(POST, "/movies", FakeHeaders(), AnyContentAsJson(Json.parse(
        """{ "title":"and",
          |"description":"My favorite movie description",
          |"genres":"@@@, @@@, @@@, @@@, @@@, @@@", "countries":"@@@, @@@", "directors":"@@@, @@@, @@@, @@@",
          |"screenwriters":"@@@, @@@, @@@", "cast":"@@@, @@@", "coverURL":"@@@" }""".stripMargin)))).get
      val response9 = route(app, FakeRequest(POST, "/movies", FakeHeaders(), AnyContentAsJson(Json.parse(
        """{ "title":"dog",
          |"description":"My favorite movie description",
          |"genres":"@@@, @@@, @@@, @@@, @@@, @@@", "countries":"@@@, @@@", "directors":"@@@, @@@, @@@, @@@",
          |"screenwriters":"@@@, @@@, @@@", "cast":"@@@, @@@", "coverURL":"@@@" }""".stripMargin)))).get

      status(response0) mustBe 201
      status(response1) mustBe 201
      status(response2) mustBe 201
      status(response3) mustBe 201
      status(response4) mustBe 201
      status(response5) mustBe 201
      status(response6) mustBe 201
      status(response7) mustBe 201
      status(response8) mustBe 201
      status(response9) mustBe 201
    }

    "findAll be successful" in {
      val response = route(app, FakeRequest(GET, "/movies")).get

      status(response) mustBe Status.OK
      contentType(response) mustBe Some("application/json")
    }

    "findOneById receive 400" in {
      val response = route(app, FakeRequest(GET, "/movies/1")).get

      status(response) mustBe 400
    }

    "findManyByTitle receive 400" in {
      val response = route(app, FakeRequest(GET, "/movies/search/title?title=dog+and+cat&limit=1")).get

      status(response) mustBe 200
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