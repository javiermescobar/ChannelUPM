package adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.javier.channelupm.databinding.HolderContactGroupBinding
import holders.GroupsViewHolder
import models.GroupChat

class GroupsAdapter(
    val items: List<GroupChat>,
    val onCLick: (item: GroupChat) -> Unit
): RecyclerView.Adapter<GroupsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupsViewHolder {
        return GroupsViewHolder(HolderContactGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false), onCLick)
    }

    override fun onBindViewHolder(holder: GroupsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}