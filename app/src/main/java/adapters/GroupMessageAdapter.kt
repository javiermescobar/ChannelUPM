package adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.javier.channelupm.databinding.HolderGroupMessageBinding
import holders.GroupMessageHolder
import models.GroupMessage

class GroupMessageAdapter(
    private var items: List<GroupMessage>
): RecyclerView.Adapter<GroupMessageHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupMessageHolder {
        var holder = GroupMessageHolder(HolderGroupMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        holder.setIsRecyclable(false)
        return holder
    }

    override fun onBindViewHolder(holder: GroupMessageHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(items: List<GroupMessage>) {
        this.items = items
    }
}