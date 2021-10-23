package com.example.rickandmortyepisodeguide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmortyepisodeguide.ViewModels.EpisodeViewModel
import com.example.rickandmortyepisodeguide.ViewModels.EpisodeViewModelFactory
import com.example.rickandmortyepisodeguide.adapters.RVCharacterAdapter
import com.example.rickandmortyepisodeguide.data.api.RetrofitHelper
import com.example.rickandmortyepisodeguide.data.api.RnMService
import com.example.rickandmortyepisodeguide.data.repository.RnMRepository
import com.example.rickandmortyepisodeguide.databinding.ActivityEpisodeBinding
import com.example.rickandmortyepisodeguide.databinding.ActivityMainBinding


class EpisodeActivity : AppCompatActivity() {
    lateinit var binding:ActivityEpisodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEpisodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = getIntent()
        var episodeId = intent.getIntExtra("id",1)

        val rnmService = RetrofitHelper.getInstance().create(RnMService::class.java)
        val rnmRepository = RnMRepository(rnmService)

        val episodeViewModel = ViewModelProvider(this, EpisodeViewModelFactory(rnmRepository))[EpisodeViewModel::class.java]

        episodeViewModel.fetchEpisodeData(episodeId)
        binding.characterRV.layoutManager = LinearLayoutManager(this)

        episodeViewModel.episodeData.observe(this, Observer {
            binding.episodeNumber.setText(it.episode)
            binding.episodeTitle.setText(it.name)
            binding.releaseDate.setText(it.airDate)
            binding.characterCount.setText(""+it.characters.size)
        })
        episodeViewModel.CharacterLiveData.observe(this, Observer {
            binding.characterRV.adapter = RVCharacterAdapter(it.results)
        })
        binding.backButton.setOnClickListener {
            super.onBackPressed()
        }
        binding.genderSpinner.adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,episodeViewModel.genders)
        binding.genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
              episodeViewModel.filterCharacterByGender(p0?.selectedItem.toString())
                binding.searchText.text.clear()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        binding.searchText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(!p0.isNullOrEmpty()){
                    episodeViewModel.filterCharacterByNameAndGender(binding.genderSpinner.selectedItem.toString(), p0.toString()
                    )
                }
                else{
                    episodeViewModel.filterCharacterByGender(binding.genderSpinner.selectedItem.toString())
                }
            }

            override fun afterTextChanged(p0: Editable?) {}

        })
    }

}