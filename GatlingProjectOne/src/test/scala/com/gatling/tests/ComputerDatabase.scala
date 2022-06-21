package com.gatling.tests

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class ComputerDatabase extends Simulation {

	val httpProtocol = http
		.baseUrl("https://computer-database.gatling.io")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("ro-RO,ro;q=0.9,en-US;q=0.8,en;q=0.7,de;q=0.6")
		.doNotTrackHeader("1")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36")

	val scn =  scenario("ComputerDatabase")
		.exec(http("ComputersDatabasePage")
			.get("/computers")
			)
		.pause(3)
		.exec(http("NewComputersPage")
			.get("/computers/new")
			)
		.pause(3)
		.exec(http("CreatingNewComputer")
			.post("/computers")

			.formParam("name", "MyComputer1")
			.formParam("introduced", "2010-01-01")
			.formParam("discontinued", "2020-01-01")
			.formParam("company", "1"))
		.pause(3)
		.exec(http("FilterComputerPage")
			.get("/computers?f=MyComputer1")
			)

	setUp(scn.inject(atOnceUsers(50))).protocols(httpProtocol)
}