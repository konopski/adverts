package eu.konopski.adverts.web

import org.scalatra._


class AdvertsServlet extends ScalatraServlet {

  get("/") {
    redirect("/adverts?sortBy=title")
  }

//  * have functionality to return list of all car adverts;
//  * optional sorting by any field specified by query parameter, default sorting - by **id**;
  get("/adverts") {

  }

  //  * have functionality to add car advert;
  post("/adverts") {

  }

  //  * have functionality to return data for single car advert by id;
  get("/adverts/:id") {
    views.html.hello()
  }

//  * have functionality to modify car advert by id;
  put("/adverts/:id") {

  }

//  * have functionality to delete car advert by id;
  delete("/adverts/:id") {

  }

//  * have validation (see required fields and fields only for used cars);
//  * accept and return data in JSON format, use standard JSON date format for the **first registration** field.

}
