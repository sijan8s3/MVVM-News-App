package com.sijanneupane.mvvmnews.ui.fragments

import android.content.ClipData
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sijanneupane.mvvmnews.R
import com.sijanneupane.mvvmnews.adapters.NewsAdapter
import com.sijanneupane.mvvmnews.ui.MainActivity
import com.sijanneupane.mvvmnews.ui.NewsViewModel
import kotlinx.android.synthetic.main.fragment_saved_news.*
import kotlinx.android.synthetic.main.fragment_search_news.*

class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //set viewModels to fragment activity
        //and we cast that as MainActivity so that we can have access to the view model created at MainActivity
        viewModel= (activity as MainActivity).viewModel

        setupRecyclerView()


        newsAdapter.setOnItemClickListener {
            val bundle= Bundle().apply{
                //adding serializable article object class to the bundle
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_savedNewsFragment_to_articleFragment,
                bundle
            )
        }

        //swipe delete variable
        val itemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, //direction
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT // swipe direction
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position= viewHolder.adapterPosition
                val article= newsAdapter.differ.currentList[position]
                viewModel.deleteSavedArticle(article)
                //execute undo Snackbar
                Snackbar.make(view, " Article Deleted Successfully ", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        viewModel.saveArticle(article)
                    }
                    show()
                }
            }
        }
        //item touch helper
        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(rvSavedNews)
        }

        //get saved news, observe on changes on out database
        viewModel.getSavedArticle().observe(viewLifecycleOwner, Observer { articles -> //new list of articles
            newsAdapter.differ.submitList(articles) //update recyclerview //differ will calculate the difference between lists
        })
    }

    private fun setupRecyclerView(){
        newsAdapter= NewsAdapter()
        rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}