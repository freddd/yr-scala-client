package com.freddd.yr.caller

import dispatch._
import dispatch.Defaults._
import com.typesafe.scalalogging.slf4j.Logging
import com.freddd.yr.models.YrRequest

/**
 * Doing the actual calls
 */
class Caller extends Logging{
  
	/**
	 * Method used to call the yr api.
	 * @param yrAction - the action e.g. AvailableActions.GET_LOCATION_FORECAST
	 * @param parameters - any additional parameters e.g. lat, lon
	 * @return returning a future containing xml.
	 */
	def call(yrAction: String, request: YrRequest): Future[String] = {
	  val base = Constants.BASE + request.country + "/"  + request.state + "/" + request.city + "/" + yrAction + Constants.FORMAT
	  val call = url(base).GET

	  logger.info("url: "+call.url)
	  
	  Http(call OK as.String)
	}
	
  object Constants {
	val BASE = "http://www.yr.no/place/"
	val BY_HOUR = "forecast_hour_by_hour"
	val FORECAST = "forecast"
	val FORMAT = ".xml"
  }
}