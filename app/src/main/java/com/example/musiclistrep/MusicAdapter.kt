import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musiclistrep.Music
import com.example.musiclistrep.databinding.MusicItemBinding

class MusicAdapter(private val musicList: List<Music>) :
    RecyclerView.Adapter<MusicAdapter.ViewHolder>() {

    private var mediaPlayer: MediaPlayer? = null
    private var isPaused: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MusicItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(musicList[position])
    }

    override fun getItemCount() = musicList.size

    inner class ViewHolder(private val binding: MusicItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(music: Music) {
            binding.title.text = music.title

            binding.play.setOnClickListener {
                if (isPaused) {
                    mediaPlayer?.start()
                    isPaused = false
                } else {
                    mediaPlayer?.stop()
                    mediaPlayer = MediaPlayer.create(itemView.context, music.url)
                    mediaPlayer?.start()
                }
            }

            binding.pause.setOnClickListener {
                mediaPlayer?.pause()
                isPaused = true
            }

            binding.stop.setOnClickListener {
                mediaPlayer?.stop()
            }
        }
    }
}
