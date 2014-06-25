package rest

import org.apache.http._
import org.apache.http.client.ResponseHandler
import org.apache.http.client.entity._
import org.apache.http.client.methods._
import org.apache.http.impl.client._
import org.apache.http.client.utils._
import org.apache.http.message._
import org.apache.http.params._
import rest.util.RequestUtil
import play.api.libs.json.JsValue
import play.api.libs.json.Json

class RestClient(val args: Array[String]) {

  require(
    args.size >= 2,
    "At minimum you should specify action(head, get, post, put, delete, options) and URL")

  val command = args.head
  val url = args.last
  val params = parseArgs(args)

  /**
   * Parse script args.
   */
  def parseArgs(args: Array[String]): Map[String, List[String]] = {
    def nameValuePair(paramName: String) = {
      def values(commaSeparatedValues: String) = commaSeparatedValues.split(",").toList
      val index = args.indexWhere(_ == paramName)

      (paramName, if (index == -1) Nil else values(args(index + 1)))
    }

    def getAuth = {
      val index = args.indexOf("-u")
      ("-u", List((args(index + 1))))
    }

    Map(nameValuePair("-d"), nameValuePair("-h"), getAuth)
  }

  def executeRequest = {
    command match {
      case "head" => handleHeadRequest(params)
      case "get" => handleGetRequest(params)
      case "post" => handlePostRequest(params)
      case "put" => handlePutRequest(params)
      case "delete" => handleDeleteRequest
      case "options" => handleOptionsRequest(params)
    }
  }

  /**
   * Handle HEAD request.
   */
  def handleHeadRequest(params: Map[String, List[String]]) = {
    val query = params("-d").mkString("&")
    val request = new HttpHead(s"${url}?${query}")

    val response = execute(request)
    println(response)
  }

  /**
   * Execute HTTP request.
   */
  def execute(request: HttpRequestBase) = {
    new DefaultHttpClient().execute(request)
  }

  /**
   * Handle GET requests.
   */
  def handleGetRequest(params: Map[String, List[String]]) = {
    val query = params("-d").mkString("&")
    val httpget = new HttpGet(s"${url}?${query}")
    RequestUtil.addHeaders(httpget, params)
    RequestUtil.addAuthorization(httpget, params)

    val response = execute(httpget, new BasicResponseHandler())
    displayJSON(response)
  }

  /**
   * Execute HTTP request.
   */
  def execute(request: HttpRequestBase, responseHandler: ResponseHandler[String]) = {
    new DefaultHttpClient().execute(request, responseHandler)
  }

  /**
   * Pretty print JSON
   */
  def displayJSON(json: String) = {
    val value: JsValue = Json.parse(json)
    println(Json.prettyPrint(value))
  }

  /**
   * Handle POST request.
   */
  def handlePostRequest(params: Map[String, List[String]]) = {
    val request = new HttpPost(url)
    RequestUtil.addHeaders(request, params)
    RequestUtil.addFormEntity(request, params)
    val response = execute(request)
    println(response.getStatusLine())
  }

  /**
   * Handle PUT request.
   */
  def handlePutRequest(params: Map[String, List[String]]) = {
    val request = new HttpPut(url)
    RequestUtil.addHeaders(request, params)
    RequestUtil.addFormEntity(request, params)
    val response = execute(request)
    println(response.getStatusLine())
  }

  /**
   * Handle DELETE request.
   */
  def handleDeleteRequest = {
    val request = new HttpDelete(url)
    val response = execute(request)
    println(response.getStatusLine())
  }

  /**
   * Handle OPTIONS request.
   */
  def handleOptionsRequest(params: Map[String, List[String]]) = {
    val request = new HttpOptions(url)
    RequestUtil.addHeaders(request, params)
    val response = execute(request)
    println("Allowed methods: " + request.getAllowedMethods(response))
  }
}

object RestClient extends App {
  def apply(args: Array[String]) = {
    val client = new RestClient(args)
    client.executeRequest
  }
}
