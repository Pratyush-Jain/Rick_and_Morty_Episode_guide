package com.example.rickandmortyepisodeguide

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyepisodeguide.ViewModels.MainViewModel
import com.example.rickandmortyepisodeguide.ViewModels.MainViewModelFactory
import com.example.rickandmortyepisodeguide.adapters.RVEpisodesAdapter
import com.example.rickandmortyepisodeguide.data.api.RetrofitHelper
import com.example.rickandmortyepisodeguide.data.api.RnMService
import com.example.rickandmortyepisodeguide.data.repository.RnMRepository
import com.example.rickandmortyepisodeguide.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),RVEpisodeClickListener {
    lateinit var viewModel:MainViewModel
    lateinit var binding:ActivityMainBinding
    var isScrolling:Boolean = false
    var totalRows: Int? = null
    var currentRow : Int? = null
    var scrolledRows: Int? = null

//    lateinit var seasonSpinnerValues:Array<Int>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val rnmService = RetrofitHelper.getInstance().create(RnMService::class.java)
        val rnmRepository = RnMRepository(rnmService)
        viewModel = ViewModelProvider(this,MainViewModelFactory(rnmRepository))[MainViewModel::class.java]
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        fetchData(1)

        viewModel.allEpisodesLivedata.observe(this, Observer {
            viewModel.allEpisodes?.addAll(it)
            binding.recyclerView.adapter = RVEpisodesAdapter(viewModel.allEpisodes,this)
            binding.progressBar.visibility = View.GONE
        })


        binding.recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                        isScrolling = true
                    }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                currentRow = layoutManager.childCount
                    totalRows = layoutManager.itemCount
                    scrolledRows = layoutManager.findFirstVisibleItemPosition()

                    if(isScrolling && (currentRow!! + scrolledRows!! ==totalRows) && totalRows!! <viewModel.totalEpisodes){

                        isScrolling = false

                        fetchData(totalRows!! +1)
                    }

            }
        })




//        binding.recyclerView.apply {
//            layoutManager =LinearLayoutManager(this@MainActivity)
//            addOnScrollListener(object: RecyclerView.OnScrollListener() {
//                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                    super.onScrollStateChanged(recyclerView, newState)
//                    if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
//                        isScrolling = true
//                    }
//                }
//
//                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                    super.onScrolled(recyclerView, dx, dy)
//                    currentRow = (layoutManager as LinearLayoutManager).childCount
//                    totalRows = (layoutManager as LinearLayoutManager).itemCount
//                    scrolledRows = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
//
//                    if(isScrolling && (currentRow!! + scrolledRows!! ==totalRows)){
//
//                        isScrolling = false
//
//                        fetchData(totalRows!!)
//
//                    }
//                }
//
//            })
//
//        }

        //getAllEpisodes()

        //Toast.makeText(this, seasonSpinnerValues[0].toString(), Toast.LENGTH_SHORT).show()
            //val spinnerAdapter = ArrayAdapter<Int>(this,android.R.layout.simple_spinner_dropdown_item,seasonSpinnerValues)
        //binding.spinner.adapter = spinnerAdapter


        binding.searchText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(!p0.isNullOrEmpty()){
                    viewModel.getEpisodeBySearch(p0)
                }
                else{

                }
            }
            override fun afterTextChanged(p0: Editable?) {}
        })


    }

    private fun fetchData(startEpisode: Int) {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.fetchData(startEpisode!!)


    }

    override fun onEpisodeCLickListener(id: Int) {
        var intent = Intent(this,EpisodeActivity::class.java)
        intent.putExtra("id",id)
        startActivity(intent)
    }
}