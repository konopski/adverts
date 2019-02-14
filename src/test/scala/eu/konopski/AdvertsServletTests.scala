package eu.konopski

import eu.konopski.adverts.web.AdvertsServlet
import org.scalatra.test.scalatest._

class AdvertsServletTests extends ScalatraFunSuite {

  addServlet(classOf[AdvertsServlet], "/*")

  test("GET / on AdvertsServlet should return status 200") {
    get("/") {
      status should equal (200)
    }
  }

}
