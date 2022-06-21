
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class OrangeHRMLoginTest extends Simulation {

  private val httpProtocol = http
    .baseUrl("https://opensource-demo.orangehrmlive.com")
    .inferHtmlResources(AllowList(), DenyList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*"""))
    .acceptHeader("*/*")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("ro-RO,ro;q=0.9,en-US;q=0.8,en;q=0.7,de;q=0.6")
    .doNotTrackHeader("1")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36")
  
  private val headers_0 = Map(
  		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
  		"Cache-Control" -> "max-age=0",
  		"Sec-Fetch-Dest" -> "document",
  		"Sec-Fetch-Mode" -> "navigate",
  		"Sec-Fetch-Site" -> "cross-site",
  		"Sec-Fetch-User" -> "?1",
  		"Upgrade-Insecure-Requests" -> "1",
  		"sec-ch-ua" -> """ Not A;Brand";v="99", "Chromium";v="102", "Google Chrome";v="102""",
  		"sec-ch-ua-mobile" -> "?0",
  		"sec-ch-ua-platform" -> "Windows"
  )
  
  private val headers_1 = Map(
  		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
  		"Cache-Control" -> "max-age=0",
  		"Origin" -> "https://opensource-demo.orangehrmlive.com",
  		"Sec-Fetch-Dest" -> "document",
  		"Sec-Fetch-Mode" -> "navigate",
  		"Sec-Fetch-Site" -> "same-origin",
  		"Sec-Fetch-User" -> "?1",
  		"Upgrade-Insecure-Requests" -> "1",
  		"sec-ch-ua" -> """ Not A;Brand";v="99", "Chromium";v="102", "Google Chrome";v="102""",
  		"sec-ch-ua-mobile" -> "?0",
  		"sec-ch-ua-platform" -> "Windows"
  )
  
  private val headers_2 = Map(
  		"Sec-Fetch-Dest" -> "empty",
  		"Sec-Fetch-Mode" -> "cors",
  		"Sec-Fetch-Site" -> "same-origin",
  		"X-Requested-With" -> "XMLHttpRequest",
  		"sec-ch-ua" -> """ Not A;Brand";v="99", "Chromium";v="102", "Google Chrome";v="102""",
  		"sec-ch-ua-mobile" -> "?0",
  		"sec-ch-ua-platform" -> "Windows"
  )


  private val scn = scenario("OrangeHRMLoginTest")
    .exec(
      http("request_0")
        .get("/")
        .headers(headers_0)
    )
    .pause(30)
    .exec(
      http("request_1")
        .post("/index.php/auth/validateCredentials")
        .headers(headers_1)
        .formParam("actionID", "")
        .formParam("hdnUserTimeZoneOffset", "3")
        .formParam("installation", "")
        .formParam("_csrf_token", "6ec1ccb5a71a8dc7d68f3eca4dc871d7")
        .formParam("txtUsername", "Admin")
        .formParam("txtPassword", "admin123")
        .formParam("Submit", "LOGIN")
        .resources(
          http("request_2")
            .get("/index.php/dashboard/employeeDistribution")
            .headers(headers_2),
          http("request_3")
            .get("/index.php/dashboard/pendingLeaveRequests")
            .headers(headers_2)
        )
    )

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}
