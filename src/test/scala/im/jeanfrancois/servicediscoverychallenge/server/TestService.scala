package im.jeanfrancois.servicediscoverychallenge.server

import im.jeanfrancois.servicediscoverychallenge.ServiceMethod
/**
 * Document me!
 *
 * @author jfim
 */

class TestService extends Service {
  @ServiceMethod
  def myServiceMethod {
  }

  def notAServiceMethod {
  }
}

class TestService2 extends Service {
  @ServiceMethod
  def stringService() : String = {
    "Hello world!"
  }

  @ServiceMethod
  def parameterService(x : Int) = {
    x
  }
}