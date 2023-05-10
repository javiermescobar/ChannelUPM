package holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.javier.channelupm.R
import com.javier.channelupm.databinding.HolderParticipantBinding
import com.squareup.picasso.Picasso
import models.UserInGroup

class ParticipantsViewHolder(
    private val binding: HolderParticipantBinding,
    private val onClick: (item: UserInGroup) -> Unit
): RecyclerView.ViewHolder(binding.root){

    fun bind(item: UserInGroup) {
        binding.apply {
            root.setOnClickListener {
                onClick.invoke(item)
            }

            if(item.AvatarImage.isNotEmpty()) {
                Picasso.with(root.context)
                    .load(item.AvatarImage)
                    .resize(contactImage.layoutParams.width, contactImage.layoutParams.height)
                    .placeholder(R.drawable.user_default)
                    .into(contactImage)
            }

            usernameText.text = item.Name

            if(item.Administrator == 1) {
                adminText.visibility = View.VISIBLE
            }
        }
    }
}