package adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.javier.channelupm.databinding.HolderPrivateMessageBinding
import holders.PrivateMessageViewHolder
import models.PrivateMessage

class PrivateMessageAdapter(
    private val items: List<PrivateMessage>
): RecyclerView.Adapter<PrivateMessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrivateMessageViewHolder {
        return PrivateMessageViewHolder(HolderPrivateMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PrivateMessageViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}