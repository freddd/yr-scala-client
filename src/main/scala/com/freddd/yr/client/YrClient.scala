package com.freddd.yr.client

import com.freddd.yr.caller.Caller
import com.freddd.yr.models.Credit
import com.freddd.yr.models.Forecast
import com.freddd.yr.models.Link
import com.freddd.yr.models.Links
import com.freddd.yr.models.Location
import com.freddd.yr.models.Meta
import com.freddd.yr.models.Precipitation
import com.freddd.yr.models.Pressure
import com.freddd.yr.models.Sun
import com.freddd.yr.models.Tabular
import com.freddd.yr.models.Temperature
import com.freddd.yr.models.Time
import com.freddd.yr.models.Timezone
import com.freddd.yr.models.WeatherData
import com.freddd.yr.models.WindDirection
import com.freddd.yr.models.WindSpeed
import com.freddd.yr.models.YrRequest
import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.StaxDriver
import com.typesafe.scalalogging.slf4j.Logging

import dispatch.Defaults.executor
import dispatch.Future
import dispatch.Future
import net.mixedbits.tools.XStreamConversions

/**
 * The client used by applications.
 */
class YrClient extends Logging{
  private val caller = new Caller
  private val xst = XStreamConversions(new XStream(new StaxDriver()))
  initXStream()
  
  /**
   * Initiating xstream, doing all of the mapping.
   */
  private def initXStream(){
    xst.autodetectAnnotations(true);
    xst.alias("weatherdata", classOf[WeatherData])
    xst.alias("location", classOf[Location])
    xst.aliasField("type", classOf[Location], "locationType");
    xst.alias("credit", classOf[Credit])
    xst.alias("links", classOf[Links])
    xst.addImplicitCollection(classOf[Links], "l") 
    xst.alias("link", classOf[Link])
    xst.alias("meta", classOf[Meta])
    xst.alias("forecast", classOf[Forecast])
    xst.alias("sun", classOf[Sun])
    xst.alias("timezone", classOf[Timezone])
    xst.alias("forecast", classOf[Forecast])
    xst.alias("time", classOf[Time])
    xst.alias("tabular", classOf[Tabular])
    xst.addImplicitCollection(classOf[Tabular], "r")
    xst.alias("symbol", classOf[Symbol])
    xst.alias("precipitation", classOf[Precipitation])
    xst.alias("windDirection", classOf[WindDirection])
    xst.alias("windSpeed", classOf[WindSpeed])
    xst.alias("temperature", classOf[Temperature])
    xst.alias("pressure", classOf[Pressure]) 
  }
  
  /**
   * Doing the "forecast" call to yr.
   * @param request - data needed to do the request to yr
   * @return a future containing the WeatherData.
   */
  def getLocationForecast(request: YrRequest): Future[Option[WeatherData]] = {
    val result = caller.call(caller.Constants.FORECAST, request)
    result.map(resp => sanitize(resp))
  }
  
  /**
   * Doing the "byHour" call to yr.
   * @param request - data needed to do the request to yr
   * @return a future containing the WeatherData.
   */
  def getByHour(request: YrRequest): Future[Option[WeatherData]] = {
    val result = caller.call(caller.Constants.BY_HOUR, request)
    result.map(resp => sanitize(resp))
  }
  
  /**
   * Makes sure that an option is always returned (even if we get a 404 or similar).
   * @param response - an option that might be containing the response from the call
   */
  private def sanitize(response: Option[String]) = response match{
    case Some(_) => Option(xst.fromXML(response.get).asInstanceOf[WeatherData])
    case None => logger.error("YrClient - something went wrong, weather cannot be retrieved");None
  }
  
}