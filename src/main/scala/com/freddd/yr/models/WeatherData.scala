package com.freddd.yr.models

import java.util.ArrayList

import org.joda.time.LocalDateTime

import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamAsAttribute
import scala.collection.JavaConverters._

/**
 * The class containing all of the weather information.
 */
case class WeatherData(location: Location, credit: Credit, meta: Meta, links: Links, sun: Sun, forecast: Forecast)

/**
 * Representing a location
 * <location>
 *   <name>Stockholm</name>
 *   <type>Capital</type>
 *   <country>Sweden</country>
 *   <timezone id="Europe/Stockholm" utcoffsetMinutes="120"/>
 *   <location altitude="28" latitude="59.33258" longitude="18.0649" geobase="geonames" geobaseid="2673730"/>
 * </location>
 */
case class Location(name: String, locationType: String, country: String, timezone: Timezone, location: InnerLocation)

/**
 * Representing Credit
 * <credit>
 *  <link text="Weather forecast from yr.no, delivered by the Norwegian Meteorological Institute and the NRK" url="http://www.yr.no/place/Sweden/Stockholm/Stockholm/"/>
 * </credit>
 */
case class Credit(link: Link)

/**
 * Representing the Forecast
 * <forecast>
 *  <tabular>...</tabular>
 * </forecast>
 */
case class Forecast(tabular: Tabular)

/**
 * Representing Meta
 * <meta>
 *  <lastupdate>2013-06-01T06:37:00</lastupdate>
 *  <nextupdate>2013-06-01T20:00:00</nextupdate>
 * </meta>
 */
case class Meta(_lastupdate: String, _nextupdate: String) {
  @XStreamAsAttribute
  var lu = _lastupdate
  @XStreamAsAttribute
  var nu = _nextupdate

  def nextupdate = LocalDateTime.parse(nu)
  def lastupdate = LocalDateTime.parse(lu)

  override def toString = "[Meta: nextupdate= " + nextupdate + " lastupdate= " + lastupdate + "]"
}

/**
 * Representing the links element (i.e. a class containing a list of links)
 * <links>
 * 	<link id="xmlSource" url="http://www.yr.no/place/Sweden/Stockholm/Stockholm/forecast.xml"/>
 * 	<link id="xmlSourceHourByHour" url="http://www.yr.no/place/Sweden/Stockholm/Stockholm/forecast_hour_by_hour.xml"/>
 * 	<link id="overview" url="http://www.yr.no/place/Sweden/Stockholm/Stockholm/"/>
 * 	<link id="hourByHour" url="http://www.yr.no/place/Sweden/Stockholm/Stockholm/hour_by_hour"/>
 * 	<link id="longTermForecast" url="http://www.yr.no/place/Sweden/Stockholm/Stockholm/long"/>
 * </links>
 */
case class Links(_links: ArrayList[Link]) {
  private var l = _links

  def links = l.asScala

  override def toString = "[Links: links= " + links + "]"
}

/**
 * Representing the tabular (i.e. a list of time)
 * <tabular>
 *   <time from="2013-06-01T15:00:00" to="2013-06-01T18:00:00" period="2">...</time>
 *   <time from="2013-06-01T18:00:00" to="2013-06-02T00:00:00" period="3">...</time>
 *   ....
 * </tabular>
 */
class Tabular(_rows: ArrayList[Time]) {
  private var r = _rows

  def rows = r.asScala

  override def toString = "[Tabular: rows= " + rows + "]"
}

/**
 * Representing the timezone.
 * <timezone id="Europe/Stockholm" utcoffsetMinutes="120"/>
 */
class Timezone(_id: String, _utcoffsetMinutes: String) {
  @XStreamAsAttribute
  var id = _id
  @XStreamAsAttribute
  var utcoffsetMinutes = _utcoffsetMinutes

  override def toString = "[Timezone: id= " + id + " utcoffsetMinutes= " + utcoffsetMinutes + "]"
}

/**
 * Representing a link. Note that both id and text can be None.
 * <link text="Weather forecast from yr.no, delivered by the Norwegian Meteorological Institute and the NRK" url="http://www.yr.no/place/Sweden/Stockholm/Stockholm/"/>
 * <link id="xmlSource" url="http://www.yr.no/place/Sweden/Stockholm/Stockholm/forecast.xml"/>
 */
class Link(_text: String, _url: String, _id: String) {
  @XStreamAlias("id")
  @XStreamAsAttribute
  private var i = _id

  @XStreamAsAttribute
  var url = _url

  @XStreamAlias("text")
  @XStreamAsAttribute
  private var t = _text

  def id = Option(i)
  def text = Option(t)

  override def toString = "[Link: id= " + id + " url= " + url + " text= " + text + "]"
}

/**
 * Sunrise and sunset
 * <sun rise="2013-06-01T03:43:17" set="2013-06-01T21:48:59"/>
 */
class Sun(_rise: String, _set: String) {

  @XStreamAlias("rise")
  @XStreamAsAttribute
  private var r = _rise

  @XStreamAlias("set")
  @XStreamAsAttribute
  private var s = _set

  def rise = LocalDateTime.parse(r)
  def set = LocalDateTime.parse(s)

  override def toString = "[Sun: rise= " + rise + " set= " + set + "]"
}

/**
 * Representing time. Note that it does not only contain time but also windspeed etc.
 * "period" can be None, dates are represented as localdatetime
 * <time from="2013-06-01T18:00:00" to="2013-06-02T00:00:00" period="3">
 *   <symbol number="4" name="Cloudy" var="04"/>
 *   <precipitation value="0" minvalue="0" maxvalue="0.1"/>
 *   <windDirection deg="50.8" code="NE" name="Northeast"/>
 *   <windSpeed mps="3.2" name="Light breeze"/>
 *   <temperature unit="celsius" value="15"/>
 *   <pressure unit="hPa" value="1010.3"/>
 * </time>
 */
class Time(_symbol: Symbol, _precipitation: Precipitation, _windDirection: WindDirection, _windSpeed: WindSpeed, _temperature: Temperature, _pressure: Pressure, _from: String, _to: String, _period: Integer) {

  @XStreamAlias("from")
  @XStreamAsAttribute
  private var f = _from

  @XStreamAlias("to")
  @XStreamAsAttribute
  private var t = _to

  @XStreamAlias("period")
  @XStreamAsAttribute
  private var p = _period

  def period = Option(p)
  def from = LocalDateTime.parse(f)
  def to = LocalDateTime.parse(t)

  var symbol = _symbol
  var precipitation = _precipitation
  var windDirection = _windDirection
  var windSpeed = _windSpeed
  var temperature = _temperature
  var pressure = _pressure

  override def toString = "[Time: from= " + from + " to= " + to + " period= " + period + "]"
}

/**
 * Represents Precipitation
 * <precipitation value="0" minvalue="0" maxvalue="0.1"/>
 */
class Precipitation(_value: Double, _minvalue: Double, _maxvalue: Double) {
  @XStreamAsAttribute
  var value = _value
  @XStreamAsAttribute
  var minvalue = _minvalue
  @XStreamAsAttribute
  var maxvalue = _maxvalue

  override def toString = "[Precipitation: value= " + value + " minvalue= " + minvalue + " maxvalue= " + maxvalue + "]"
}

/**
 * Represents WindDirection
 * <windDirection deg="50.8" code="NE" name="Northeast"/>
 */
class WindDirection(_deg: Double, _code: String, _name: String, _time: String) {
  @XStreamAsAttribute
  var deg = _deg
  @XStreamAsAttribute
  var code = _code
  @XStreamAsAttribute
  var name = _name

  @XStreamAlias("time")
  @XStreamAsAttribute
  private var t = _time

  def time = LocalDateTime.parse(t)

  override def toString = "[WindDirection: deg= " + deg + " code= " + code + " name= " + name + " time= " + time + "]"
}

/**
 * Represents WindSpeed
 * <windSpeed mps="3.2" name="Light breeze"/>
 */
class WindSpeed(_mps: Double, _name: String, _time: String) {
  @XStreamAsAttribute
  var mps = _mps
  @XStreamAsAttribute
  var name = _name

  @XStreamAlias("time")
  @XStreamAsAttribute
  private var t = _time

  def time = LocalDateTime.parse(t)

  override def toString = "[WindSpeed: mps= " + mps + " name= " + name + " time= " + time + "]"
}

/**
 * Represents Temperature
 * <temperature unit="celsius" value="15"/>
 */
class Temperature(_unit: String, _value: Integer, _time: String) {
  @XStreamAsAttribute
  var unit = _unit
  @XStreamAsAttribute
  var value = _value

  @XStreamAlias("time")
  @XStreamAsAttribute
  private var t = _time

  def time = LocalDateTime.parse(t)

  override def toString = "[Temperature: unit= " + unit + " value= " + value + " time= " + time + "]"
}

/**
 * Represents Pressure
 * <pressure unit="hPa" value="1010.3"/>
 */
class Pressure(_unit: String, _value: Double) {
  @XStreamAsAttribute
  var unit = _unit
  @XStreamAsAttribute
  var value = _value

  override def toString = "[Pressure: unit= " + unit + " value= " + value + "]"
}

/**
 * Represents Symbol
 * Note that time can be None
 * <symbol number="11" name="Heavy rain and thunder" var="11"/>
 */
class Symbol(_number: Integer, _name: String, _variable: String, _time: String) {
  @XStreamAsAttribute
  var number = _number
  @XStreamAsAttribute
  var variable = _variable
  @XStreamAsAttribute
  var name = _name

  @XStreamAlias("time")
  @XStreamAsAttribute
  private var t = _time

  def time = Option(LocalDateTime.parse(t))

  override def toString = "[Symbol: number= " + number + " variable= " + variable + " name= " + name + " time= " + time + "]"
}

/**
 * Represents the inner location in location
 * <location altitude="28" latitude="59.33258" longitude="18.0649" geobase="geonames" geobaseid="2673730"/>
 */
@XStreamAlias("location")
class InnerLocation(_altitude: Double, _latitude: Double, _longitude: Double, _geobase: String, _geobaseid: String) {
  @XStreamAsAttribute
  var altitude = _altitude
  @XStreamAsAttribute
  var latitude = _latitude
  @XStreamAsAttribute
  var longitude = _longitude
  @XStreamAsAttribute
  var geobase = _geobase
  @XStreamAsAttribute
  var geobaseid = _geobaseid

  override def toString = "[InnerLocation: altitude= " + altitude + " latitude= " + latitude + " longitude= " + longitude + " geobase= " + geobase + " geobaseid= " + geobaseid + "]"
}