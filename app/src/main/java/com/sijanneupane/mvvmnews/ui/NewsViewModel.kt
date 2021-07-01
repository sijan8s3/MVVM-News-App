package com.sijanneupane.mvvmnews.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sijanneupane.mvvmnews.models.NewsResponse
import com.sijanneupane.mvvmnews.repository.NewsRepository
import com.sijanneupane.mvvmnews.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    val newsRepository: NewsRepository //parameter
) : ViewModel(){

    //LIVEDATA OBJECT
    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    //Pagination
    var breakingNewsPage= 1

    init {
        getBreakingNews("in")
    }

    //we cannot start the function in the coroutine so we start the it here
    /*
    viewModelScope makes the function alive only as long as the ViewModel is alive
     */
    fun getBreakingNews(countryCode: String)= viewModelScope.launch {
        breakingNews.postValue(Resource.Loading()) //init loading state before the network call

        //actual response
        val response= newsRepository.getBreakingNews(countryCode, breakingNewsPage)

        //handling response
        breakingNews.postValue(handleBreakingNewsResponse(response))


    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                return  Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

}