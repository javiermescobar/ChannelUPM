package holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.javier.channelupm.databinding.HolderNewsBinding
import models.NewsItem
import utils.Constants

class NewsItemViewHolder(
    private val binding: HolderNewsBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: NewsItem) {
        binding.titleText.text = item.Title
        binding.descriptionText.text = item.Description
        binding.dateText.text = item.SendDate.toString()
        binding.editNewsButton.visibility = if(item.UserId == Constants.currentUser.UserId) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}