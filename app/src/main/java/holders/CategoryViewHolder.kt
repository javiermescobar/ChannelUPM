package holders

import androidx.recyclerview.widget.RecyclerView
import com.javier.channelupm.R
import com.javier.channelupm.databinding.HolderCategoryBinding
import models.Category
import models.InteractableCategory

class CategoryViewHolder(
    private val binding: HolderCategoryBinding,
    private val onClick: (item: InteractableCategory) -> Unit
): RecyclerView.ViewHolder(binding.root){

    fun bind(item: InteractableCategory) {
        binding.titleText.text = item.categoryTitle
        binding.categoryButton.setOnClickListener {
            onClick.invoke(item)
        }
        binding.layoutBackground.background = if(item.selected) {
            binding.root.resources.getDrawable(R.drawable.rounded_category_selected)
        } else {
            binding.root.resources.getDrawable(R.drawable.rounded_category_unselected)
        }
    }
}