package rest.util

import org.apache.http.client.methods.HttpRequestBase
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.message.BasicHeader
import org.apache.http.message.BasicNameValuePair
import org.apache.http.message._

object RequestUtil {

  /**
   * Add headers to request.
   */
  def addHeaders(request: HttpRequestBase, params: Map[String, List[String]]) = {
    var headers = createHeaders(params)
    headers.foreach { request.addHeader(_) }
  }

  /**
   * Create headers.
   */
  def createHeaders(params: Map[String, List[String]]) = for (nameValue <- params("-h")) yield {
    def firstEqualIndex = nameValue.indexOf("=")
    def key = nameValue.substring(0, firstEqualIndex)
    def value = nameValue.substring(firstEqualIndex + 1)

    new BasicHeader(key, value)
  }

  /**
   * Add authorization header.
   */
  def addAuthorization(request: HttpRequestBase, params: Map[String, List[String]]) = params("-u") match {
    case first :: xs => {
      val tokens = first.split(":")
      val username = tokens(0)
      val password = tokens(1)
      val auth = AuthUtil.getBasicAuthHeaderValue(username, password)
      request.addHeader("Authorization", auth)
    }
    case _ =>
  }

  
  /**
   * Add a form entity to the request.
   */
  def addFormEntity(request: HttpRequestBase, params: Map[String, List[String]]) = {
    def toJavaList(scalaList: List[BasicNameValuePair]) = {
      java.util.Arrays.asList(scalaList.toArray: _*)
    }

    def formParams = for (nameValue <- params("-d")) yield {
      def tokens = nameValue.split("=")
      new BasicNameValuePair(tokens(0), tokens(1))
    }

    new UrlEncodedFormEntity(toJavaList(formParams), "UTF-8")
  }

}