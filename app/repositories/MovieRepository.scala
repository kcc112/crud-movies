package repositories

import models.Movie

import java.text.Normalizer
import javax.inject._
import reactivemongo.api.bson.collection.BSONCollection
import play.modules.reactivemongo.ReactiveMongoApi

import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.api.{Cursor, ReadPreference}
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import org.joda.time.DateTime
import reactivemongo.api.commands.WriteResult
import reactivemongo.api.bson.compat._

import scala.:+
import scala.collection.mutable.ArrayBuffer


@Singleton
class MovieRepository @Inject()(
  implicit executionContext: ExecutionContext,
  reactiveMongoApi: ReactiveMongoApi
) {
  def collection: Future[BSONCollection] = reactiveMongoApi.database.map(db => db.collection("movies"))

  def findAll(limit: Int = 100): Future[Seq[Movie]] = {
    collection.flatMap(
      _.find(BSONDocument(), Option.empty[Movie])
        .cursor[Movie](ReadPreference.Primary)
        .collect[Seq](limit, Cursor.FailOnError[Seq[Movie]]())
    )
  }

  def findOneById(id: BSONObjectID): Future[Option[Movie]] = {
    collection.flatMap(
      _.find(BSONDocument("_id" -> id), Option.empty[Movie])
        .one[Movie]
    )
  }

  def findManyByTitle(titlee: String, limit: Int = 100): Future[Seq[Movie]] = {
    val title = "Ala ma kota i kiciuś żółćąśćłĄŻŚĆŁŃ"

    val keywords = title.split(" ")

    val searchPhrases = List[String]()

    var phrase = new String()
    for (i <- 0 to (keywords.length - 1)) {
      phrase = new String(keywords(i) + " ")
      searchPhrases :+ phrase
      for (j <- i + 1 to (keywords.length - 1)) {
        println(keywords(j))
        phrase = phrase + " " + keywords(j)
        searchPhrases :+ phrase
      }
    }
    return null
//
    //    val tab = Future[BSONCollection]
    //
    //    searchPhrases.foreach(phrase => {
    //      tab = collection.flatMap(
    //        _.find(BSONDocument("title" -> phrase), Option.empty[Movie])
    //          .cursor[Movie](ReadPreference.Primary)
    //          .collect[Seq](limit, Cursor.FailOnError[Seq[Movie]]())
    //      )
    //      retrun tab
    //    })
    //
    //
    //
    //
    //    searchPhrases.foreach(phrase => {
    //      tab :+ collection.flatMap(
    //        _.find(BSONDocument("title" -> phrase), Option.empty[Movie])
    //          .one[Movie]
    //      )
    //    })
    //
    //    return


  //sort by keyword length (most valuable results will be first found)
  //search for results with them
  //remove duplicates
  //return
  //    collection.flatMap(
  //      _.find(BSONDocument(), Option.empty[Movie])
  //        .cursor[Movie](ReadPreference.Primary)
  //        .collect[Seq](limit, Cursor.FailOnError[Seq[Movie]]())
  //    )
}


  def create(movie: Movie): Future[WriteResult] = {
    collection.flatMap(
      _.insert(ordered = false)
      .one(movie.copy(creationDate = Option(new DateTime()), updateDate = Option(new DateTime()), premiere = Option(new DateTime()))))
  }

  def update(id: BSONObjectID, movie: Movie): Future[WriteResult] = {
    collection.flatMap(
      _.update(ordered = false)
      .one(BSONDocument("_id" -> id), movie.copy(updateDate = Option(new DateTime())))
    )
  }

  def delete(id: BSONObjectID): Future[WriteResult] = {
    collection.flatMap(
      _.delete()
      .one(BSONDocument("_id" -> id), Option(1))
    )
  }

  def deleteAll(): Future[WriteResult] = {
    collection.flatMap(
      _.delete().one(BSONDocument(), Option(1))
    )
  }
}
