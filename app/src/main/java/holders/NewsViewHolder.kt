package holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.javier.channelupm.databinding.HolderNewsBinding
import models.New
import utils.Constants

class NewsViewHolder(
    private val binding: HolderNewsBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: New) {
        binding.titleText.text = item.Title
        binding.descriptionText.text = item.Description
        binding.dateText.text = item.SendDate.toString()
        binding.editNewsButton.visibility = if(item.UserId == Constants.currentUserId) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}