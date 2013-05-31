package com.freddd.yr.client

import com.freddd.yr.caller.Caller
import com.freddd.yr.models.Body
import com.freddd.yr.models.Credit
import com.freddd.yr.models.Forecast
import com.freddd.yr.models.Link
import com.freddd.yr.models.Links
import com.freddd.yr.models.Location
import com.freddd.yr.models.Meta
import com.freddd.yr.models.Observations
import com.freddd.yr.models.Precipitation
import com.freddd.yr.models.Pressure
import com.freddd.yr.models.Sun
import com.freddd.yr.models.Tabular
import com.freddd.yr.models.Temperature
import com.freddd.yr.models.Text
import com.freddd.yr.models.Time
import com.freddd.yr.models.Timezone
import com.freddd.yr.models.Title
import com.freddd.yr.models.WeatherData
import com.freddd.yr.models.WeatherStation
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

class YrClient extends Logging{
  val caller = new Caller
  val xst = XStreamConversions(new XStream(new StaxDriver()))
  initXStream()
  
  def initXStream(){
    xst.autodetectAnnotations(true);
    xst.alias("weatherdata", classOf[WeatherData])
    xst.alias("location", classOf[Location])
    xst.aliasField("type", classOf[Location], "locationType");
    xst.alias("credit", classOf[Credit])
    xst.alias("links", classOf[Links])
    xst.addImplicitCollection(classOf[Links], "links") 
    xst.alias("link", classOf[Link])
    xst.alias("meta", classOf[Meta])
    xst.alias("forecast", classOf[Forecast])
    xst.alias("observations", classOf[Observations])
    xst.alias("sun", classOf[Sun])
    xst.alias("text", classOf[Text])
    xst.alias("weatherstation", classOf[WeatherStation])
    xst.alias("timezone", classOf[Timezone])
    xst.alias("forecast", classOf[Forecast])
    xst.alias("text", classOf[Text])
    xst.alias("time", classOf[Time])
    xst.alias("title", classOf[Title])
    xst.alias("body", classOf[Body])
    xst.alias("tabular", classOf[Tabular])
    xst.addImplicitCollection(classOf[Tabular], "rows")
    xst.alias("symbol", classOf[Symbol])
    xst.alias("precipitation", classOf[Precipitation])
    xst.alias("windDirection", classOf[WindDirection])
    xst.alias("windSpeed", classOf[WindSpeed])
    xst.alias("temperature", classOf[Temperature])
    xst.alias("pressure", classOf[Pressure])
    
    /*
    xst.useAttributeFor("id", classOf[Timezone])
    xst.useAttributeFor("id", classOf[Link])
    xst.useAttributeFor("url", classOf[Link])
    xst.useAttributeFor("rise", classOf[Sun])
    xst.useAttributeFor("set", classOf[Sun])
    xst.useAttributeFor("utcoffsetMinutes", classOf[Timezone]) */
  }
  
  def getLocationForecast(request: YrRequest): Future[WeatherData] = {
    val result = caller.call(caller.Constants.FORECAST, request)
    result.map(resp => xst.fromXML(resp).asInstanceOf[WeatherData])
  }
  
  def getByHour(request: YrRequest): Future[WeatherData] = {
    val result = caller.call(caller.Constants.BY_HOUR, request)
    result.map(resp => xst.fromXML(resp).asInstanceOf[WeatherData])
  }
  
}