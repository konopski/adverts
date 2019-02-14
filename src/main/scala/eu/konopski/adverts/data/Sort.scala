package eu.konopski.adverts.data

trait Sort[T] {
  def sort(adverts: List[T]): List[T]
}
