package rest.util

import org.apache.commons.codec.binary.Base64

object AuthUtil {

  /** Create the basic auth header value for login/password
    */
  def getBasicAuthHeaderValue(login: String, password: String): String = {
    var credentials = login + ":" + password;
    var credentialsBytes = Base64.encodeBase64(credentials.getBytes());

    return "Basic " + new String(credentialsBytes);
  }

}