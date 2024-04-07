package holders

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.javier.channelupm.databinding.HolderNewsBinding
import models.NewsItem
import utils.Constants

class NewsItemViewHolder(
    private val binding: HolderNewsBinding,
    private val onClickEdit: (item: NewsItem) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: NewsItem) {
        binding.titleText.text = item.Title
        binding.categoryText.text = item.CategoryName
        binding.descriptionText.text = item.Description
        binding.dateText.text = item.SendDate
        binding.editNewsButton.apply {
            visibility = if(item.UserId == Constants.currentUser.UserId) {
                View.VISIBLE
            } else {
                View.GONE
            }
            setOnClickListener{
                onClickEdit.invoke(item)
            }
        }
    }
}