package com.sijanneupane.mvvmnews.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sijanneupane.mvvmnews.R
import com.sijanneupane.mvvmnews.adapters.NewsAdapter
import com.sijanneupane.mvvmnews.ui.MainActivity
import com.sijanneupane.mvvmnews.ui.NewsViewModel
import com.sijanneupane.mvvmnews.utils.Constants.Companion.SEARCH_DELAY
import com.sijanneupane.mvvmnews.utils.Resource
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment (R.layout.fragment_search_news) {

    lateinit var viewModel: NewsViewModel
    val TAG= "SearchNewsFragment"
    lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //set viewModels to fragment activity
        //and we cast that as MainActivity so that we can have access to the view model created at MainActivity
        viewModel= (activity as MainActivity).viewModel
        setupRecyclerView()

        //implementing search delay
        var job: Job? = null
        etSearch.addTextChangedListener {editable->
            job?.cancel() //cancel current job

            //new job
            job= MainScope().launch {
                delay(SEARCH_DELAY)

                //after delay, check editable for null,
                editable?.let {
                    if (editable.toString().isNotEmpty()){
                        viewModel.searchNews(editable.toString())
                    }
                }
            }
        }

        //subscribe to live data
        viewModel.searchNews.observe(viewLifecycleOwner, Observer { response->
            when(response){
                is Resource.Success->{
                    hideProgressBar()
                    //check null
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error->{
                    hideProgressBar()
                    response.message?.let { message->
                        Log.e(TAG, "An error occured: $message" )
                    }
                }
                is Resource.Loading->{
                    showProgressBar()
                }
            }
        })
    }

    private fun setupRecyclerView(){
        newsAdapter= NewsAdapter()
        rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
    private fun hideProgressBar(){
        paginationProgressBar.visibility= View.INVISIBLE
    }
    private fun showProgressBar(){
        paginationProgressBar.visibility= View.VISIBLE
    }
}