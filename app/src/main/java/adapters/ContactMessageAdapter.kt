package adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.javier.channelupm.databinding.HolderContactMessageBinding
import holders.ContactMessageViewHolder
import models.MessageContact

class ContactMessageAdapter (
    val items: List<MessageContact>,
    val onClick:(item: MessageContact) -> Unit
): RecyclerView.Adapter<ContactMessageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactMessageViewHolder {
        return ContactMessageViewHolder(HolderContactMessageBinding.inflate(LayoutInflater.from(parent.context),parent, false), onClick)
    }

    override fun onBindViewHolder(holder: ContactMessageViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}