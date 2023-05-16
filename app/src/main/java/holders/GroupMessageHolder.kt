package holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.javier.channelupm.R
import com.javier.channelupm.databinding.HolderGroupMessageBinding
import com.squareup.picasso.Picasso
import models.GroupMessage
import models.UserInGroup
import utils.Constants
import utils.DateUtils

class GroupMessageHolder(
    private val binding: HolderGroupMessageBinding,
    private val participants: List<UserInGroup>
): RecyclerView.ViewHolder(binding.root){

    fun bind(item: GroupMessage) {
        binding.apply {
            if(item.SenderId == Constants.currentUser.UserId) {
                messageLayoutSender.visibility = View.GONE
                messageTextReceiver.text = item.Text
                dateTextReceiver.text = DateUtils.obtainDateMessageString(item.SendDate)
            } else {
                messageLayoutReceiver.visibility = View.GONE
                val sender = participants.filter { participant -> participant.UserId == item.SenderId }[0]
                if(sender.AvatarImage.isNotEmpty()) {
                    Picasso.with(root.context)
                        .load(sender.AvatarImage)
                        .placeholder(R.drawable.user_default)
                        .resize(avatarMessage.layoutParams.width, avatarMessage.layoutParams.height)
                        .into(avatarMessage)
                }
                usernameMessage.text = sender.Name
                messageTextSender.text = item.Text
                messageDateSender.text = DateUtils.obtainDateMessageString(item.SendDate)
            }
        }
    }
}