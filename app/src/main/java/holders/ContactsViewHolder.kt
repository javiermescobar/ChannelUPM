package holders

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.javier.channelupm.R
import com.javier.channelupm.databinding.HolderContactGroupBinding
import com.squareup.picasso.Picasso
import models.User

class ContactsViewHolder(
    val binding: HolderContactGroupBinding,
    val onClick: (item: User) -> Unit
): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: User) {
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
    }
}