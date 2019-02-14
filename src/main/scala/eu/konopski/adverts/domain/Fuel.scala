package eu.konopski.adverts.domain

case class Fuel(name: String)

object Fuel {
  val gasoline = Fuel("gasoline")
  val diesel = Fuel("diesel")
}
