package holders

import androidx.recyclerview.widget.RecyclerView
import com.javier.channelupm.R
import com.javier.channelupm.databinding.HolderContactMessageBinding
import com.squareup.picasso.Picasso
import models.MessageContact

class ContactMessageViewHolder(
    val binding: HolderContactMessageBinding,
    val onClick: (item: MessageContact) -> Unit
): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: MessageContact) {
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

        binding.usernameText.text = item.Name

        if(item.LastMessage.isNotEmpty()) {
            binding.contactLastMessage.text = item.LastMessage
        }
    }
}