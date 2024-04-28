package com.example.musiclistrep

import android.content.Intent
import android.media.MediaRecorder
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.musiclistrep.databinding.AudioRecordBinding
import com.example.musiclistrep.databinding.FilenameBinding
import java.io.File
import java.io.IOException

class AudioRecorder : AppCompatActivity() {

    private lateinit var binding: AudioRecordBinding
    private lateinit var mediaRecorder: MediaRecorder
    private var isRecording = false
    private var fileName = ""
    private lateinit var audioDirectory: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AudioRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        audioDirectory = getExternalFilesDir("Audio") ?: File(applicationContext.filesDir, "Audio")

        showDialog()

        binding.btnRecord.setOnClickListener {
            toggleRecording()
        }
        binding.btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
        val dialogBinding = FilenameBinding.inflate(layoutInflater)
        builder.setView(dialogBinding.root)
        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
            finish()
        }
        dialogBinding.finishButton.setOnClickListener {
            fileName = dialogBinding.path.text.toString().trim()
            if (fileName.isNotEmpty() && fileName != "default") {
                dialog.dismiss()
            } else {
                dialogBinding.path.error = "Please enter a valid filename"
            }
        }
    }

    private fun toggleRecording() {
        if (isRecording) {
            stopRecording()
        } else {
            startRecording()
        }
    }

    private fun startRecording() {
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(File(audioDirectory, "$fileName.mp3").absolutePath)
            try {
                prepare()
                start()
                isRecording = true
                binding.btnRecord.text = "Stop"
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun stopRecording() {
        mediaRecorder.stop()
        mediaRecorder.release()
        isRecording = false
        binding.btnRecord.text = "Start"
        val savedFilePath = File(audioDirectory, "$fileName.mp4").absolutePath
        Log.i("VideoRecorder", "Recorder released and video saved to: $savedFilePath")
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isRecording) {
            stopRecording()
        }
    }
}
