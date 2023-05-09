package holders

import androidx.recyclerview.widget.RecyclerView
import com.javier.channelupm.databinding.HolderContactGroupBinding
import com.squareup.picasso.Picasso
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
                Picasso.with(root.context).load(item.AvatarImage)
                    .resize(200,200)
                    .centerCrop().into(contactImage)
            }
            usernameText.text = item.GroupName
        }
    }
}