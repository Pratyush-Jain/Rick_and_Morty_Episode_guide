package com.example.rickandmortyepisodeguide



import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.EditText
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
    private lateinit var binding:ActivityMainBinding
    var isScrolling:Boolean = false
    var totalRows: Int? = null
    var currentRow : Int? = null
    var scrolledRows: Int? = null



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

        viewModel.allEpisodesLivedata.observe(this,  {
            viewModel.allEpisodes.addAll(it)
            binding.returnSearch.visibility = View.GONE
            binding.recyclerView.adapter = RVEpisodesAdapter(viewModel.allEpisodes,this)

            binding.progressBar.visibility = View.GONE

        })

        viewModel.episodeBySearch.observe(this,  {
            binding.returnSearch.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
            binding.recyclerView.adapter = RVEpisodesAdapter(it,this)

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



        binding.searchButton.setOnClickListener {
            fetchSearch(binding.searchText)


    }
        binding.returnSearch.setOnClickListener {
            viewModel.allEpisodes.clear()
            binding.searchText.text.clear()
            fetchData(1)
        }
    }



    private fun fetchData(startEpisode: Int) {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.fetchData(startEpisode)


    }

    private fun fetchSearch(tv:EditText) {
        val searchText = tv.text.toString()
        if(searchText.isNotEmpty()){
            viewModel.allEpisodes.clear()
            binding.returnSearch.visibility = View.VISIBLE
            binding.progressBar.visibility = View.VISIBLE
            viewModel.getEpisodeBySearch(searchText)
        }

    }



    override fun onEpisodeCLickListener(id: Int) {
        val intent = Intent(this,EpisodeActivity::class.java)
        intent.putExtra("id",id)
        startActivity(intent)
    }
}