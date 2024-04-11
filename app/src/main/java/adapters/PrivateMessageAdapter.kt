package adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.javier.channelupm.databinding.HolderPrivateMessageBinding
import holders.PrivateMessageViewHolder
import models.PrivateMessage

class PrivateMessageAdapter(
    private var items: List<PrivateMessage>
): RecyclerView.Adapter<PrivateMessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrivateMessageViewHolder {
        var holder = PrivateMessageViewHolder(HolderPrivateMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        holder.setIsRecyclable(false)
        return holder
    }

    override fun onBindViewHolder(holder: PrivateMessageViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateMessages(items: List<PrivateMessage>) {
        this.items = items
    }
}