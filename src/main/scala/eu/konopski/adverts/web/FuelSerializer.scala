package eu.konopski.adverts.web

import eu.konopski.adverts.domain.Fuel
import org.json4s.JsonAST._
import org.json4s.CustomSerializer

object FuelSerializer extends CustomSerializer[Fuel](implicit formats => ( {
  case jv: JValue =>
    Fuel((jv \ "name").extract[String])
}, {
  case f: Fuel => JString(f.name)
}))
