package com.example.rickandmortyepisodeguide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmortyepisodeguide.ViewModels.MainViewModel
import com.example.rickandmortyepisodeguide.ViewModels.MainViewModelFactory
import com.example.rickandmortyepisodeguide.data.api.RetrofitHelper
import com.example.rickandmortyepisodeguide.data.api.RnMService
import com.example.rickandmortyepisodeguide.data.pojo.EpisodeList
import com.example.rickandmortyepisodeguide.data.repository.RnMRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    lateinit var viewModel:MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val rnmService = RetrofitHelper.getInstance().create(RnMService::class.java)
//        val rnmRepository = RnMRepository(rnmService)
//        viewModel = ViewModelProvider(this,MainViewModelFactory(rnmRepository)).get(MainViewModel::class.java)



        //Log.d("APIRESULT",r.isSuccessful.toString())
        getEpisodes()
//        var result = viewModel.getAllEpisodes()
//        Log.d("APIRESULT",result.body().toString())


    }

    private fun getEpisodes() {
        val episodes = RetrofitHelper.episodeInstance.getAllEpisodes()
        episodes.enqueue(object:Callback<EpisodeList>{
            override fun onResponse(
                call: Call<EpisodeList>,
                response: Response<EpisodeList>
            ) {
                val allepisodes = response.body()
                if(allepisodes!=null) {
                    Log.d("APIRESULT",allepisodes.toString())
                }
            }

            override fun onFailure(call: Call<EpisodeList>, t: Throwable) {
                Log.d("APIRESULT","Error")
            }

        })
    }
}