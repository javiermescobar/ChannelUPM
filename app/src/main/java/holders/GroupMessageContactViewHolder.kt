package holders

import android.text.TextUtils
import androidx.recyclerview.widget.RecyclerView
import com.javier.channelupm.R
import com.javier.channelupm.databinding.HolderContactMessageBinding
import com.squareup.picasso.Picasso
import models.GroupMessageContact
import models.PrivateMessageContact
import utils.Constants

class GroupMessageContactViewHolder(
    val binding: HolderContactMessageBinding,
    val onClick: (item: GroupMessageContact) -> Unit
): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: GroupMessageContact) {
        binding.root.setOnClickListener {
            onClick.invoke(item)
        }

        if(item.AvatarImage.isNotEmpty()) {
            Picasso.with(binding.root.context)
                .load(item.AvatarImage)
                .placeholder(R.drawable.user_default)
                .resize(binding.contactImage.layoutParams.width, binding.contactImage.layoutParams.height)
                .into(binding.contactImage)
        }
        binding.usernameText.text = item.GroupName


        if(item.LastMessage.isNotEmpty()) {
            if(item.UserName == Constants.currentUser.Name) {
                val lastText = "Tú: ${item.LastMessage}"
                binding.contactLastMessage.text = lastText
            } else {
                val lastMessage = "${item.UserName}: ${item.LastMessage}"
                binding.contactLastMessage.text = lastMessage
            }
        }
    }
}