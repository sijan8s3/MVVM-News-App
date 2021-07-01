package com.sijanneupane.mvvmnews.utils
/*
RESOURCE:
recommended by google
to wrap around network responses

this will be an generic class
very useful to differentiate between success and error responses
and also helps in handling the loading state
 */
/*
SEALED CLASS:
kind of an abstract class where we can define which class are allowed to inherit the from this Resource Class
 */
sealed class Resource<T>(
    //parameters
    val data: T?=null,
    val message: String?= null,

        ){

    //classes allowed to inherit
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T?= null): Resource<T>(data, message)
    class Loading<T>: Resource<T>()


}