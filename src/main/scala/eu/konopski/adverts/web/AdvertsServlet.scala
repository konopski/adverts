package eu.konopski.adverts.web


import java.util.Date

import eu.konopski.adverts.data.Adverts
import eu.konopski.adverts.data.Adverts.Sort
import eu.konopski.adverts.domain.{Advert, Fuel}
import org.json4s.FieldSerializer
import org.json4s.FieldSerializer.renameTo
import org.json4s.prefs.EmptyValueStrategy
import org.scalatra._
import scalaz._
import scalaz.syntax.std.option._

import scala.util.Try

// JSON-related libraries
import org.json4s.{DefaultFormats, Formats}

// JSON handling support from Scalatra
import org.scalatra.json._

class AdvertsServlet extends ScalatraServlet with JacksonJsonSupport {
  protected implicit val jsonFormats: Formats =
    DefaultFormats.withEmptyValueStrategy(EmptyValueStrategy.skip) +
      FuelSerializer +
      FieldSerializer[Advert](renameTo("is_new", "new"))

  before() {
    contentType = formats("json")
  }

  override def renderPipeline: RenderPipeline = ({
    case \/-(right) => right
    case -\/(left) => left
  }: RenderPipeline) orElse super.renderPipeline


  get("/") {
    redirect("/adverts?sortBy=title")
  }

//  * have functionality to return list of all car adverts;
//  * optional sorting by any field specified by query parameter, default sorting - by **id**;
  get("/adverts") {
    Adverts.getAll(Sort.of(params.get("sortBy")))
  }

  //  * have functionality to add car advert;
  post("/adverts") {
    for {
      title   <- (parsedBody \ "title").extractOpt[String] \/> BadRequest("title")
      fuel    <- (parsedBody \ "fuel").extractOpt[String]  \/> BadRequest("fuel")
      price   <- (parsedBody \ "price").extractOpt[Int]    \/> BadRequest("price")
      is_new  <- (parsedBody \ "new").extractOpt[Boolean]  \/> BadRequest("new")
      mileage <- extractMileageForUsed(is_new)             \/> BadRequest("mileage")
      regDate <- extractFirstRegistrationForUsed(is_new)   \/> BadRequest("firstRegistration")
    } yield Created(
        if (is_new) Adverts.putNew(title, Fuel(fuel), price)
        else Adverts.putUsed(title, Fuel(fuel), price, mileage, regDate)
    )
  }

  //  * have functionality to return data for single car advert by id;
  get("/adverts/:id") {
    val ad = for {
      idStr <- params.get("id")
      id    <- Try(idStr.toInt).toOption
      found <- Adverts.get(_.id == id)
    } yield found
    if(ad.isDefined) ad
    else halt(404, "Not found")
  }

//  * have functionality to modify car advert by id;
  put("/adverts/:id") {
    val update = for {
      id      <- (parsedBody \ "id").extractOpt[Int]       \/> BadRequest("id")
      _       <- verifyIdMatchesPath(id)                   \/> BadRequest("param[id]!=id")
      title   <- (parsedBody \ "title").extractOpt[String] \/> BadRequest("title")
      fuel    <- (parsedBody \ "fuel").extractOpt[String]  \/> BadRequest("fuel")
      price   <- (parsedBody \ "price").extractOpt[Int]    \/> BadRequest("price")
      is_new  <- (parsedBody \ "new").extractOpt[Boolean]  \/> BadRequest("new")
      mileage <- extractMileageForUsed(is_new)             \/> BadRequest("mileage")
      regDate <- extractFirstRegistrationForUsed(is_new)   \/> BadRequest("firstRegistration")
    } yield Advert(id, title, Fuel(fuel), price, is_new, mileage, regDate)
    update.map { advert =>
      Adverts.update(advert) \/> NotFound("not found id:" + advert.id)
    }
    update
  }

  private def extractFirstRegistrationForUsed(is_new: Boolean) =
    extractValid((parsedBody \ "firstRegistration").extractOpt[Date], is_new)

  private def extractMileageForUsed(is_new: Boolean) =
    extractValid((parsedBody \ "mileage").extractOpt[Int], is_new)

  private def extractValid[T](received: Option[T], is_new: Boolean) = {
    if (is_new) {
      if (received.isEmpty) Some(None)
      else None
    }
    else received map { Some(_) }
  }

  private def verifyIdMatchesPath(id: Int) = {
    params.get("id") flatMap { idStr =>
      if (id.toString == idStr) Some(id)
      else None
    }
  }

//  * have functionality to delete car advert by id;
  delete("/adverts/:id") {
    for {
      idStr <- params.get("id")
      id    <- Try(idStr.toInt).toOption
      rm    <- Adverts.deleteWhere(_.id == id)
    } yield rm

  }

//  * have validation (see required fields and fields only for used cars);
//  * accept and return data in JSON format, use standard JSON date format for the **first registration** field.

}
