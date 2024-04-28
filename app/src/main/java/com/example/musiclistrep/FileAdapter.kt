import android.app.AlertDialog
import android.media.MediaPlayer
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.example.musiclistrep.databinding.ItemBinding
import com.example.musiclistrep.databinding.VideodialogBinding
import java.io.File

class FileAdapter(private val files: List<File>) :
    RecyclerView.Adapter<FileAdapter.ViewHolder>() {

    private var mediaPlayer: MediaPlayer? = null
    private var isPaused: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(files[position])
    }

    override fun getItemCount() = files.size

    inner class ViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(file: File) {
            binding.title.text = file.name
            val isVideo = file.name.endsWith(".mp4") // Check if the file is a video

            binding.play.setOnClickListener {
                if (isVideo) {
                    playVideo(file)
                } else {
                    if (isPaused) {
                        mediaPlayer?.start()
                        isPaused = false
                    } else {
                        mediaPlayer?.release()
                        mediaPlayer = MediaPlayer.create(itemView.context, Uri.fromFile(file))
                        mediaPlayer?.start()
                    }
                }
            }

            binding.pause.setOnClickListener {
                if (!isVideo) {
                    mediaPlayer?.pause()
                    isPaused = true
                }
            }

            binding.stop.setOnClickListener {
                if (!isVideo) {
                    mediaPlayer?.stop()
                    mediaPlayer?.release()
                }
            }
        }

        private fun playVideo(file: File) {
            val dialogBinding = VideodialogBinding.inflate(LayoutInflater.from(itemView.context))
            val dialog = AlertDialog.Builder(itemView.context)
                .setView(dialogBinding.root)
                .create()

            dialogBinding.videoView.apply {
                setVideoURI(Uri.fromFile(file))
                start()
                setOnCompletionListener {
                    dialog.dismiss()
                }
            }

            dialog.show()
        }
    }
}
