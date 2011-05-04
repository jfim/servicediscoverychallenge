package im.jeanfrancois.servicediscoverychallenge

import com.google.inject.Singleton

/**
 * Document me!
 *
 * @author jfim
 */

trait ServiceRegistry {
  var serviceList : List[Service];
}