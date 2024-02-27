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

        val rawResources = mutableListOf<Int>()
        val fields = R.raw::class.java.fields
        for (field in fields) {
            try {
                rawResources.add(field.getInt(null))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val musicList = rawResources.map { resourceId ->
            val resourceName = resources.getResourceEntryName(resourceId)
            Music(resourceName, resourceId)
        }

        binding.recycler.adapter = MusicAdapter(musicList)
    }
}