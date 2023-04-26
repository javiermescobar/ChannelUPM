package holders

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.javier.channelupm.databinding.HolderContactGroupBinding
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
            binding.contactImage.setImageURI(Uri.parse(item.AvatarImage))
        }
        binding.usernameText.text = item.Name
    }
}