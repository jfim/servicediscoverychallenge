package im.jeanfrancois.servicediscoverychallenge

import org.scalatest.matchers.MustMatchers
import org.scalatest.Spec

/**
 * Document me!
 *
 * @author jfim
 */

class TestServiceTrait extends Spec with MustMatchers {
  describe("A Service") {
    val testService = new TestService

    it("should only list methods that are annotated with the ServiceMethod annotation") {
      testService.serviceMethods must (contain (testService.getClass.getDeclaredMethod("myServiceMethod")) and not contain (testService.getClass.getDeclaredMethod("notAServiceMethod")))
    }

    it("should reply to ProcessServiceMethod messages") {
      testService.start
      testService !? ProcessServiceMethod("myServiceMethod", Nil)
      testService ! Stop
    }

    it("should return the proper value") {
      val testService2 = new TestService2
      testService2.start
      testService2 !? ProcessServiceMethod("stringService", Nil) must (equal("Hello world!"))
      testService2 ! Stop
    }

    it("should pass parameters") {
      val testService2 = new TestService2
      testService2.start
      testService2 !? ProcessServiceMethod("parameterService", List(5.asInstanceOf[Integer])) must (equal(5))
      testService2 ! Stop
    }
  }
}