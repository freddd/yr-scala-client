package com.freddd.yr.models

import java.util.{ArrayList => JList}

import org.joda.time.LocalDateTime

import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamAsAttribute

case class WeatherData(location: Location, credit: Credit, meta: Meta, links: Links, sun: Sun, forecast: Forecast)
case class Observations(weatherStations: Seq[WeatherStation])
case class WeatherStation(stno: Integer, sttype: String, name: String, distance: Double, lat: Double, source: String)
case class Links(links: JList[Link])
case class Meta(lastupdate: String, nextupdate: String)
case class Location(name: String, locationType: String, country: String, timezone: Timezone, location: InnerLocation)
case class Credit(link: Link)
case class Forecast(text: Text, tabular: Tabular)
case class Text(location: Location)
case class Title(title: String)
case class Body(body: String)
case class Tabular(rows: JList[Time])

class Timezone(_id: String, _utcoffsetMinutes: String){
  @XStreamAsAttribute
  var id = _id
  @XStreamAsAttribute
  var utcoffsetMinutes = _utcoffsetMinutes
  
  override def toString = "[Timezone: id= "+id+" utcoffsetMinutes= "+ utcoffsetMinutes +"]"
}

class Link(_text: String, _url:String, _id: String){
  @XStreamAsAttribute
  var id = _id
  @XStreamAsAttribute
  var url = _url
  @XStreamAsAttribute
  var text = _text
  
  override def toString = "[Link: id= "+id+" url= "+ url + " text= "+ text +"]"
}

class Sun(_rise: String, _set: String){
  @XStreamAsAttribute
  var rise: LocalDateTime = LocalDateTime.parse(_rise)
  @XStreamAsAttribute
  var set: LocalDateTime = LocalDateTime.parse(_set)
  
  override def toString = "[Sun: rise= "+rise+" set= "+ set +"]"
}

class Time(_symbol: Symbol, _precipitation: Precipitation, _windDirection: WindDirection, _windSpeed: WindSpeed, _temperature: Temperature, _pressure: Pressure, _from: String, _to: String, _period: String){
  @XStreamAsAttribute
  var from: LocalDateTime = LocalDateTime.parse(_from)
  @XStreamAsAttribute
  var to: LocalDateTime = LocalDateTime.parse(_to)
  @XStreamAsAttribute
  var period = _period
  
  var symbol = _symbol
  var precipitation = _precipitation
  var windDirection = _windDirection
  var windSpeed = _windSpeed
  var temperature = _temperature
  var pressure = _pressure
  
  override def toString = "[Time: from= "+from+" to= "+ to +" period= "+ period +"]"
}

class Precipitation(_value: Double, _minvalue: Double, _maxvalue: Double){
  @XStreamAsAttribute
  var value = _value
  @XStreamAsAttribute
  var minvalue = _minvalue
  @XStreamAsAttribute
  var maxvalue = _maxvalue
  
  override def toString = "[Precipitation: value= "+value+" minvalue= "+ minvalue +" maxvalue= "+ maxvalue +"]"
}

class WindDirection(_deg: Double, _code: String, _name: String, _time: String){
  @XStreamAsAttribute
  var deg = _deg
  @XStreamAsAttribute
  var code = _code
  @XStreamAsAttribute
  var name = _name
  @XStreamAsAttribute
  var time: LocalDateTime = LocalDateTime.parse(_time)
  
  override def toString = "[WindDirection: deg= "+deg+" code= "+ code +" name= "+ name +" time= "+ time +"]"
}

class WindSpeed(_mps: Double, _name: String, _time: String){
  @XStreamAsAttribute
  var mps = _mps
  @XStreamAsAttribute
  var name = _name
  @XStreamAsAttribute
  var time: LocalDateTime = LocalDateTime.parse(_time)
  
  override def toString = "[WindSpeed: mps= "+mps+" name= "+ name +" time= "+ time +"]"
}

class Temperature(_unit: String, _value: Integer, _time: String){
  @XStreamAsAttribute
  var unit = _unit
  @XStreamAsAttribute
  var value = _value
  @XStreamAsAttribute
  var time: LocalDateTime = LocalDateTime.parse(_time)
  
  override def toString = "[Temperature: unit= "+unit+" value= "+ value +" time= "+ time +"]"
}

class Pressure(_unit: String, _value: Double){
  @XStreamAsAttribute
  var unit = _unit
  @XStreamAsAttribute
  var value = _value
  
  override def toString = "[Pressure: unit= "+unit+" value= "+ value +"]"
}

class Symbol(_number: Integer, _name: String, _variable: String, _time: String){
  @XStreamAsAttribute
  var number = _number
  @XStreamAsAttribute
  var variable = _variable
  @XStreamAsAttribute
  var name = _name
  @XStreamAsAttribute
  var time: LocalDateTime = LocalDateTime.parse(_time)
  
  override def toString = "[Symbol: number= "+number+" variable= "+ variable +" name= "+ name +" time= "+ time +"]"
}

@XStreamAlias("location")
class InnerLocation(_altitude: Double, _latitude: Double, _longitude: Double, _geobase: String, _geobaseid: String){
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
  
  override def toString = "[InnerLocation: altitude= "+altitude+" latitude= "+ latitude +" longitude= "+ longitude +" geobase= "+ geobase +" geobaseid= "+ geobaseid +"]"
}