package com.freddd.yr.client

import org.scalatest.FunSuite
import scala.util.Failure
import scala.util.Success
import dispatch._
import Defaults._
import com.freddd.yr.models.YrRequest

class YrClientTest extends FunSuite{
  val client = new YrClient
  
  test("yr test - getLocationForecast"){
    client.getLocationForecast(new YrRequest("Sweden", "Stockholm", "Stockholm")) onComplete {
      case Success(data) => assert(data.get.location.country.equals("Sweden"));
      case Failure(t) => assert(false)
    }
  }  
  
  test("yr test - getByHour"){
    client.getByHour(new YrRequest("Sweden", "Stockholm", "Stockholm")) onComplete {
      case Success(data) => assert(data.get.location.country.equals("Sweden"))
      case Failure(t) => assert(false)
    }
  }
  
  test("yr test - handling of 404"){
    client.getByHour(new YrRequest("United_States", "TX", "Austin")) onComplete {
      case Success(data) => assert(!data.isDefined)
      case Failure(t) => assert(false)
    }
  }

}