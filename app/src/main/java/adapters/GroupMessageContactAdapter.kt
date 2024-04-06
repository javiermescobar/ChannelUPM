package adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.javier.channelupm.databinding.HolderContactMessageBinding
import holders.GroupMessageContactViewHolder
import holders.PrivateMessageContactViewHolder
import models.GroupMessageContact
import models.PrivateMessageContact

class GroupMessageContactAdapter (
    val items: List<GroupMessageContact>,
    val onClick:(item: GroupMessageContact) -> Unit
): RecyclerView.Adapter<GroupMessageContactViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupMessageContactViewHolder {
        return GroupMessageContactViewHolder(HolderContactMessageBinding.inflate(LayoutInflater.from(parent.context),parent, false), onClick)
    }

    override fun onBindViewHolder(holder: GroupMessageContactViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}