package eu.konopski.adverts.data

import eu.konopski.adverts.domain.Advert

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
