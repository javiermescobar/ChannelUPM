package adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.javier.channelupm.databinding.HolderNewsBinding
import holders.NewsViewHolder


class NewsAdapter(
    private val layoutInflater: LayoutInflater
): RecyclerView.Adapter<NewsViewHolder>() {

    private lateinit var binding: HolderNewsBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        binding = HolderNewsBinding.inflate(layoutInflater)
        return NewsViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {}

    override fun getItemCount(): Int { return 10 }
}