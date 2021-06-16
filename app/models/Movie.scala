package models

import org.joda.time.DateTime
import play.api.libs.json.{Format, Json}
import reactivemongo.play.json._
import reactivemongo.play.json.compat._
import reactivemongo.bson.BSONObjectID
import reactivemongo.bson._
import play.api.libs.json.JodaWrites._
import play.api.libs.json.JodaReads._

import scala.util.Try

case class Movie(
                  id:Option[BSONObjectID],
                  title:String,
                  description:String,
                  premiere: Option[DateTime],
                  genres:String,
                  countries:String,
                  directors:String,
                  screenwriters:String,
                  cast:String,
                  coverURL:String,
                  creationDate: Option[DateTime],
                  updateDate: Option[DateTime]
                )

object Movie {
  implicit val fmt : Format[Movie] = Json.format[Movie]

  implicit object MovieBSONReader extends BSONDocumentReader[Movie] {
    def read(document: BSONDocument): Movie = {
      Movie(
        document.getAs[BSONObjectID]("id"),
        document.getAs[String]("title").get,
        document.getAs[String]("description").get,
        document.getAs[BSONDateTime]("premiere").map(date => new DateTime(date.value)),
        document.getAs[String]("genres").get,
        document.getAs[String]("countries").get,
        document.getAs[String]("directors").get,
        document.getAs[String]("screenwriters").get,
        document.getAs[String]("cast").get,
        document.getAs[String]("coverURL").get,
        document.getAs[BSONDateTime]("creationDate").map(date => new DateTime(date.value)),
        document.getAs[BSONDateTime]("updateDate").map(date => new DateTime(date.value))
      )
    }
  }

  implicit object MovieBSONWriter extends BSONDocumentWriter[Movie] {
    def write(movie: Movie): BSONDocument = {
      BSONDocument(
        "id" -> movie.id,
        "title" -> movie.title,
        "description" -> movie.description,
        "premiere" -> movie.premiere.map(date => BSONDateTime(date.getMillis)),
        "genres" -> movie.genres,
        "countries" -> movie.countries,
        "directors" -> movie.directors,
        "screenwriters" -> movie.screenwriters,
        "cast" -> movie.cast,
        "coverURL" -> movie.coverURL,
        "creationDate" -> movie.creationDate.map(date => BSONDateTime(date.getMillis)),
        "updateDate" -> movie.updateDate.map(date => BSONDateTime(date.getMillis))
      )
    }
  }
}

