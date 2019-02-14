package eu.konopski.adverts.domain

import java.util.Date

case class Advert
(
  id: Int, //** (_required_): **int** or **guid**, choose whatever is more convenient for you;
  title: String, //** (_required_): **string**, e.g. _"Audi A4 Avant"_;
  fuel: Fuel, //** (_required_): gasoline or diesel, use some type which could be extended in the future by adding additional fuel types;
  price: Int, //** (_required_): **integer**;
  is_new: Boolean, //** (_required_): **boolean**, indicates if car is new or used;
  mileage: Option[Int], //** (_only for used cars_): **integer**;
  firstRegistration: Option[Date] //** (_only for used cars_): **date** without time.
)
