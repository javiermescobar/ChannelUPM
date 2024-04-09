package holders

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.javier.channelupm.R
import com.javier.channelupm.databinding.HolderPrivateMessageBinding
import models.PrivateMessage
import utils.Constants
import utils.DateUtils

class PrivateMessageViewHolder(
    val binding: HolderPrivateMessageBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: PrivateMessage) {
        binding.apply {

            if(item.MessageId != -1) {
                if(item.SenderId != Constants.currentUser.UserId) {
                    messageLayoutReceiver.visibility = View.GONE
                    messageTextSender.text = item.Text
                    dateTextSender.text = DateUtils.obtainDateMessageString(item.SendDate)
                } else {
                    messageLayoutSender.visibility = View.GONE
                    messageTextReceiver.text = item.Text
                    dateTextReceiver.text = DateUtils.obtainDateMessageString(item.SendDate)
                }
            } else {
                messageLayoutReceiver.visibility = View.GONE
                messageLayoutSender.visibility = View.GONE
                dateMessageLayout.visibility = View.VISIBLE

                dateText.text = item.SendDate
            }
        }
    }
}