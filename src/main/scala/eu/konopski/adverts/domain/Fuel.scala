package eu.konopski.adverts.domain

sealed abstract class Fuel(name: String)
case object Gasoline extends Fuel("gasoline")
case object Diesel extends Fuel("diesel")
