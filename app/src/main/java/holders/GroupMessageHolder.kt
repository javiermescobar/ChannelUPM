package holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.javier.channelupm.R
import com.javier.channelupm.databinding.HolderGroupMessageBinding
import com.squareup.picasso.Picasso
import models.GroupMessage
import utils.Constants
import utils.DateUtils.Companion.getDateString
import utils.DateUtils.Companion.obtainDateMessageString

class GroupMessageHolder(
    private val binding: HolderGroupMessageBinding
): RecyclerView.ViewHolder(binding.root){

    fun bind(item: GroupMessage) {
        binding.apply {

            if(item.GroupMessageId != -1) {
                if(item.SenderId == Constants.currentUser.UserId) {
                    messageLayoutSender.visibility = View.GONE
                    messageTextReceiver.text = item.Text
                    dateTextReceiver.text = obtainDateMessageString(item.SendDate)
                } else {
                    messageLayoutReceiver.visibility = View.GONE
                    if(item.AvatarImage.isNotEmpty()) {
                        Picasso.with(root.context)
                            .load(item.AvatarImage)
                            .placeholder(R.drawable.user_default)
                            .resize(avatarMessage.layoutParams.width, avatarMessage.layoutParams.height)
                            .into(avatarMessage)
                    }
                    usernameMessage.text = item.SenderName
                    messageTextSender.text = item.Text
                    messageDateSender.text = obtainDateMessageString(item.SendDate)
                }
            } else {
                messageLayoutSender.visibility = View.GONE
                messageLayoutReceiver.visibility = View.GONE
                dateMessageLayout.visibility = View.VISIBLE
                dateText.text = item.SendDate
            }
        }
    }
}