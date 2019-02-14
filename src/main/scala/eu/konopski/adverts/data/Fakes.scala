package eu.konopski.adverts.data

import java.time.{LocalDate, Month}
import java.util.Date

import eu.konopski.adverts.domain.{Advert, Fuel}

object Fakes {

  def someDate(year: Int, month: Month, day: Int) = Some(new Date(LocalDate.of(year, month, day).toEpochDay))

  val audi = Advert(
    id=1,
    title="Audi",
    fuel=Fuel.diesel,
    price=12345,
    is_new=false,
    mileage=Some(22012340),
    firstRegistration=someDate(1979, Month.APRIL, 1)
  )

  val volvo = Advert(
    id=2,
    title="Volvo",
    fuel=Fuel.gasoline,
    price=2*12345,
    is_new=false,
    mileage=Some(12012340),
    firstRegistration=someDate(1979, Month.APRIL, 8)
  )

  val polonaise = Advert(
    id=3,
    title="Polonez",
    fuel=Fuel.gasoline,
    price=3*12345,
    is_new=false,
    mileage=Some(32012340),
    firstRegistration=someDate(1979, Month.APRIL, 5)
  )

  val siren = Advert(
    id=4,
    title="Syrena",
    fuel=Fuel.gasoline,
    price=5*12345,
    is_new=true,
    mileage=None,
    firstRegistration=None
  )


}
