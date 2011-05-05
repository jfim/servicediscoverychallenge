package im.jeanfrancois.servicediscoverychallenge.endpointdiscovery

import actors.remote.Node
import collection.mutable.ListBuffer
import java.net.{SocketTimeoutException, DatagramPacket, MulticastSocket, InetAddress}

/**
 * Document me!
 *
 * @author jfim
 */

class MulticastEndpointDiscoveryService extends EndpointDiscoveryService {
  val multicastGroupAddress = InetAddress.getByName("232.232.232.232")
  val multicastSocketPort = 4321;
  val servicePort = 1234;

  @volatile
  var discoverable = false;

  def findEndpoints(): List[Node] = {
    // Join the multicast group
    val multicastSocket = new MulticastSocket(multicastSocketPort)
    multicastSocket.joinGroup(multicastGroupAddress)

    // Broadcast hello
    val datagramPacket = new DatagramPacket("Hello".getBytes, 5, multicastGroupAddress, multicastSocketPort)
    multicastSocket.send(datagramPacket)

    // Create a node for each reply in the next five seconds
    val listBuffer = new ListBuffer[Node]
    var endTime = System.currentTimeMillis + 5000
    var timeLeftToWait = 5000l;
    do {
      // Receive a packet
      try {
        multicastSocket.setSoTimeout(timeLeftToWait.toInt);
        val responseDatagramPacket = new DatagramPacket(new Array[Byte](50), 50);
        multicastSocket.receive(responseDatagramPacket);
        val remoteNodeAddress = responseDatagramPacket.getAddress.getHostAddress
        listBuffer += new Node(remoteNodeAddress, servicePort)
      } catch {
        case timeout: SocketTimeoutException => // Don't care
        case e => e.printStackTrace()
      }

      timeLeftToWait = endTime - System.currentTimeMillis
    } while (0 < timeLeftToWait)

    // Leave the multicast group
    multicastSocket.leaveGroup(multicastGroupAddress)

    listBuffer.toList
  }

  def makeThisEndpointNotDiscoverable() = {
    discoverable = false
  }

  def makeThisEndpointDiscoverable() = {
    discoverable = true

    val myThread = new Thread {
      override def run() {
        val multicastSocket = new MulticastSocket(multicastSocketPort)
        multicastSocket.setSoTimeout(1000)
        multicastSocket.joinGroup(multicastGroupAddress)

        while (discoverable) {
          try {
            val datagramPacket = new DatagramPacket(new Array[Byte](50), 50)
            multicastSocket.receive(datagramPacket)

            // Upon receiving a packet, reply to it if it is a service discovery packet
            if (datagramPacket.getLength == 5) {
              val responseDatagramPacket = new DatagramPacket("Hello!".getBytes, 6)
              responseDatagramPacket.setAddress(datagramPacket.getAddress)
              multicastSocket.send(responseDatagramPacket)
            }
          } catch {
            case timeout: SocketTimeoutException => // Don't care
            case e => e.printStackTrace()
          }
        }
      }
    }

    myThread.start()
  }
}