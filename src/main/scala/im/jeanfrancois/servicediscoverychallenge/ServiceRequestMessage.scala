package im.jeanfrancois.servicediscoverychallenge

/**
 * Document me!
 *
 * @author jfim
 */

case class ServiceRequestMessage(className : String, methodName : String, methodArgs : List[Object])