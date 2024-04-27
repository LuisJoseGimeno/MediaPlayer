package com.example.musiclistrep

import MusicAdapter
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.musiclistrep.databinding.ActivityMainBinding
import com.example.musiclistrep.databinding.SelectModeBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val dialogBinding = SelectModeBinding.inflate(layoutInflater)
            builder.setView(dialogBinding.root)
            val dialog = builder.create()
            dialog.show()
            dialogBinding.btnCancel.setOnClickListener {
                dialog.dismiss()
            }
            dialogBinding.btnAudio.setOnClickListener {
                // Aquí puedes agregar la lógica para seleccionar audio
                // Por ejemplo, puedes abrir una actividad para grabar audio
                // Luego, cierra el diálogo
                dialog.dismiss()
            }
            dialogBinding.btnVideo.setOnClickListener {
                // Aquí puedes agregar la lógica para seleccionar video
                // Por ejemplo, puedes abrir una actividad para grabar video
                // Luego, cierra el diálogo
                dialog.dismiss()
            }
        }

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