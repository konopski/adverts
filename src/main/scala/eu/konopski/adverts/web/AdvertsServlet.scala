package eu.konopski.adverts.web

import eu.konopski.adverts.data.Adverts
import eu.konopski.adverts.data.Adverts.Sort
import org.json4s.prefs.EmptyValueStrategy
import org.scalatra._

import scala.util.Try

// JSON-related libraries
import org.json4s.{DefaultFormats, Formats}

// JSON handling support from Scalatra
import org.scalatra.json._

class AdvertsServlet extends ScalatraServlet with JacksonJsonSupport {
  protected implicit val jsonFormats: Formats =
    DefaultFormats.withEmptyValueStrategy(EmptyValueStrategy.skip) + FuelSerializer

  before() {
    contentType = formats("json")
  }

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
    halt(501, "Not implemeted yet ;)")
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
    halt(501, "Not implemeted yet ;) ")
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
