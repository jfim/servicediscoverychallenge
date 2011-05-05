package im.jeanfrancois.servicediscoverychallenge.endpointdiscovery

import actors.remote.Node

/**
 * Document me!
 *
 * @author jfim
 */

trait EndpointDiscoveryService {
  def makeThisEndpointDiscoverable()
  def makeThisEndpointNotDiscoverable()
  def findEndpoints() : List[Node]
}