package holders

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.javier.channelupm.databinding.HolderContactGroupBinding
import models.GroupChat

class GroupsViewHolder(
    val binding: HolderContactGroupBinding,
    val onClick: (item: GroupChat) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: GroupChat) {
        binding.apply {
            root.setOnClickListener {
                onClick.invoke(item)
            }
            if(item.AvatarImage.isNotEmpty()) {
                contactImage.setImageURI(Uri.parse(item.AvatarImage))
            }
            usernameText.text = item.GroupName
        }
    }
}