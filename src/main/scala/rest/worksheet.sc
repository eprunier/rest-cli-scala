package tsunami.rest

import rest.util.AuthUtil
import rest.RestClient

object worksheet {
 
  RestClient(Array("get", "-u", "foo:foo", "http://localhost:9080/directory"))
                                                  //> {
                                                  //|   "groups" : [ {
                                                  //|     "id" : 50,
                                                  //|     "name" : "R�sidence des c�dres",
                                                  //|     "nbSubGroups" : 0,
                                                  //|     "nbPersons" : 2
                                                  //|   }, {
                                                  //|     "id" : 51,
                                                  //|     "name" : "R�sidence Isabella",
                                                  //|     "nbSubGroups" : 1,
                                                  //|     "nbPersons" : 0
                                                  //|   }, {
                                                  //|     "id" : 53,
                                                  //|     "name" : "R�sidence des pyramides",
                                                  //|     "nbSubGroups" : 0,
                                                  //|     "nbPersons" : 0
                                                  //|   } ]
                                                  //| }
 
}