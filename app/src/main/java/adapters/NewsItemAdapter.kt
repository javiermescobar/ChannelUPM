package adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.javier.channelupm.databinding.HolderNewsBinding
import holders.NewsItemViewHolder
import models.NewsItem


class NewsItemAdapter(
    private val items: List<NewsItem>,
    private val onClickEdit: (item: NewsItem) -> Unit
): RecyclerView.Adapter<NewsItemViewHolder>() {

    private lateinit var binding: HolderNewsBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        return NewsItemViewHolder(HolderNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false), onClickEdit)
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}