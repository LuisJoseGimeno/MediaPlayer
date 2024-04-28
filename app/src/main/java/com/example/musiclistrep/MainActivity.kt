package com.example.musiclistrep

import FileAdapter
import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.musiclistrep.databinding.ActivityMainBinding
import com.example.musiclistrep.databinding.SelectModeBinding
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var videoDirectory = File("/storage/emulated/0/Android/data/com.example.musiclistrep/files/Movies/")
    private var audioDirectory = File("/storage/emulated/0/Android/data/com.example.musiclistrep/files/Audio/")

    companion object {
        private const val PERMISSION_REQUEST_CODE = 100
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ActivityCompat.requestPermissions(this, arrayOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        ), PERMISSION_REQUEST_CODE)

        if (!videoDirectory.exists()) {
            videoDirectory.mkdirs()
        }
        if (!audioDirectory.exists()) {
            audioDirectory.mkdirs()
        }

        binding.addButton.setOnClickListener {
            showDialog()
        }
        updateFileList()
    }
    override fun onResume() {
        super.onResume()
        updateFileList()
    }
    private fun updateFileList() {
        val videoFiles = videoDirectory.listFiles()
        val audioFiles = audioDirectory.listFiles()

        val videoList = videoFiles?.toList() ?: emptyList()
        val audioList = audioFiles?.toList() ?: emptyList()

        val fileList = mutableListOf<File>()
        fileList.addAll(videoList)
        fileList.addAll(audioList)

        binding.recycler.adapter = FileAdapter(fileList)
    }
    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
        val dialogBinding = SelectModeBinding.inflate(layoutInflater)
        builder.setView(dialogBinding.root)
        val dialog = builder.create()
        dialog.show()
        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.btnAudio.setOnClickListener {
            val intent = Intent(this, AudioRecorder::class.java)
            startActivity(intent)
            dialog.dismiss()
        }
        dialogBinding.btnVideo.setOnClickListener {
            val intent = Intent(this, VideoRecorder::class.java)
            startActivity(intent)
            dialog.dismiss()
        }
    }
}
