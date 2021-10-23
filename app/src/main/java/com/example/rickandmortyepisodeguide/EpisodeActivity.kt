package com.example.rickandmortyepisodeguide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
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


class EpisodeActivity : AppCompatActivity() {
    lateinit var binding:ActivityEpisodeBinding
    var isInitial = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEpisodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = getIntent()
        val episodeId = intent.getIntExtra("id",1)

        val rnmService = RetrofitHelper.getInstance().create(RnMService::class.java)
        val rnmRepository = RnMRepository(rnmService)

        val episodeViewModel = ViewModelProvider(this, EpisodeViewModelFactory(rnmRepository))[EpisodeViewModel::class.java]
        episodeViewModel.fetchEpisodeData(episodeId)
        binding.characterRV.layoutManager = LinearLayoutManager(this)

        episodeViewModel.episodeData.observe(this,  {
            binding.episodeNumber.setText(it.episode)
            binding.episodeTitle.setText(it.name)
            binding.releaseDate.setText(it.airDate)
            binding.characterCount.setText(""+it.characters.size)
            episodeViewModel.charactersOfEpisode.addAll(it.characters)
            episodeViewModel.characterUrlLiveData.value = it.characters

        })

        binding.progressBar2.visibility = View.VISIBLE
        binding.backButton.setOnClickListener {
            super.onBackPressed()
        }
        binding.genderSpinner.adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,episodeViewModel.genders)

        binding.genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(isInitial){
                    isInitial = false
                }else {
                    episodeViewModel.filterCharacters(
                        p0!!.getItemAtPosition(p2).toString(),
                        ""
                    )
                    binding.progressBar2.visibility = View.VISIBLE
                }
                    binding.searchText.text.clear()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        binding.searchText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(!p0.isNullOrEmpty()){
                    binding.progressBar2.visibility = View.VISIBLE
                    episodeViewModel.filterCharacters(binding.genderSpinner.selectedItem.toString(),binding.searchText.text.toString())

                }
                else{
                    episodeViewModel.filterCharacters(binding.genderSpinner.selectedItem.toString(),binding.searchText.text.toString())

                }
            }

            override fun afterTextChanged(p0: Editable?) {}

        })
        episodeViewModel.characterUrlLiveData.observe(this,  {
            episodeViewModel.filterCharacters(binding.genderSpinner.selectedItem.toString(),binding.searchText.text.toString())

        })
        episodeViewModel.characterLiveData.observe(this, {
            if(it.results.isEmpty()){
                Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show()
            }
            binding.characterRV.adapter = RVCharacterAdapter(it.results)
            binding.progressBar2.visibility = View.GONE
        })
    }

}