package com.example.musiclistrep

import MusicAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.musiclistrep.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.adapter = MusicAdapter(
            listOf(
                Music(resources.getResourceName(R.raw.dragonborn).substringAfterLast("/").substringBeforeLast("."),R.raw.dragonborn),
            )
        )
    }
}