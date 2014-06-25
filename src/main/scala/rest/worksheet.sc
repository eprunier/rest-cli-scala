package tsunami.rest

import rest.util.AuthUtil
import rest.RestClient

object worksheet {
 
  RestClient(Array("get", "-u", "foo:foo", "http://localhost:9080/directory"))
                                                  //> {
                                                  //|   "groups" : [ {
                                                  //|     "id" : 50,
                                                  //|     "name" : "Résidence des cèdres",
                                                  //|     "nbSubGroups" : 0,
                                                  //|     "nbPersons" : 2
                                                  //|   }, {
                                                  //|     "id" : 51,
                                                  //|     "name" : "Résidence Isabella",
                                                  //|     "nbSubGroups" : 1,
                                                  //|     "nbPersons" : 0
                                                  //|   }, {
                                                  //|     "id" : 53,
                                                  //|     "name" : "Résidence des pyramides",
                                                  //|     "nbSubGroups" : 0,
                                                  //|     "nbPersons" : 0
                                                  //|   } ]
                                                  //| }
 
}