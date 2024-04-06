package adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.javier.channelupm.databinding.HolderContactMessageBinding
import holders.PrivateMessageContactViewHolder
import models.PrivateMessageContact

class PrivateMessageContactAdapter (
    val items: List<PrivateMessageContact>,
    val onClick:(item: PrivateMessageContact) -> Unit
): RecyclerView.Adapter<PrivateMessageContactViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrivateMessageContactViewHolder {
        return PrivateMessageContactViewHolder(HolderContactMessageBinding.inflate(LayoutInflater.from(parent.context),parent, false), onClick)
    }

    override fun onBindViewHolder(holder: PrivateMessageContactViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}