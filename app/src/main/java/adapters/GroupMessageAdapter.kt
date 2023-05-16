package adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.javier.channelupm.databinding.HolderGroupMessageBinding
import holders.GroupMessageHolder
import models.GroupMessage
import models.UserInGroup

class GroupMessageAdapter(
    private val items: List<GroupMessage>,
    private val participants: List<UserInGroup>
): RecyclerView.Adapter<GroupMessageHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupMessageHolder {
        return GroupMessageHolder(HolderGroupMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false), participants)
    }

    override fun onBindViewHolder(holder: GroupMessageHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}