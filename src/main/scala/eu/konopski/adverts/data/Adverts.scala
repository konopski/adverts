package eu.konopski.adverts.data

import java.util.Date

import eu.konopski.adverts.domain.{Advert, Fuel}

object Adverts {

  var all = List(Fakes.audi, Fakes.polonaise, Fakes.volvo, Fakes.siren)

  def getAll(sort: Sort[Advert]): List[Advert] = {
    sort.sort(all)
  }

  def get(predicate: Advert => Boolean) = {
    all.find(predicate)
  }

  def deleteWhere(predicate: Advert => Boolean) = {
    val found = all.find(predicate)
    all = all.filterNot(predicate)
    found
  }

  private def put(a: Advert): Advert = {
    synchronized {
      all = all :+ a
    }
    a
  }

  def putUsed(
           title: String,
           fuel: Fuel,
           price: Int,
           mileage: Option[Int],
           firstReg: Option[Date]): Advert = {

    val nextId = all.maxBy(_.id).id + 1
    put(Advert(nextId, title, fuel, price, is_new = false, mileage, firstReg))
  }

  def putNew(
           title: String,
           fuel: Fuel,
           price: Int): Advert = {

    val nextId = all.maxBy(_.id).id + 1
    put(Advert(nextId, title, fuel, price, is_new = true, None, None))
  }

  def update(advert: Advert): Option[Advert] = {
    if(all.exists(_.id == advert.id)) {
      val others = all.filterNot(_.id == advert.id)
      synchronized {
        all = others :+ advert
      }
      Some(advert)
    }
    else None
  }


  object Sort {
    val byId: Sort[Advert] = (adverts: List[Advert]) => adverts.sortBy(_.id)
    val byTitle: Sort[Advert] = (adverts: List[Advert]) => adverts.sortBy(_.title)
    val byPrice: Sort[Advert] = (adverts: List[Advert]) => adverts.sortBy(_.price)
    val byMileage: Sort[Advert] = (adverts: List[Advert]) => adverts.sortBy(a => a.mileage.getOrElse(0))
    val byFirstRegistration: Sort[Advert] = (adverts: List[Advert]) => adverts.sortBy(_.firstRegistration.map(_.getTime).getOrElse(0L))

    private def fromString(sortField: String): Sort[Advert] = Map(
      "id" -> byId,
      "title" -> byTitle,
      "price" -> byPrice,
      "mileage" -> byMileage,
      "firstRegistration" -> byFirstRegistration
    ).getOrElse(sortField, byId)


    def of(s: Option[String]): Sort[Advert] = s match {
      case Some(sortField) => Sort.fromString(sortField)
      case None => Sort.byId
    }

  }

}
