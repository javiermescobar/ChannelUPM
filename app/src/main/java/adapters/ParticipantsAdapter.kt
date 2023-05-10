package adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.javier.channelupm.databinding.HolderParticipantBinding
import holders.ParticipantsViewHolder
import models.UserInGroup

class ParticipantsAdapter(
    private val items: List<UserInGroup>,
    private val onClick: (item: UserInGroup) -> Unit
): RecyclerView.Adapter<ParticipantsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantsViewHolder {
        return ParticipantsViewHolder(HolderParticipantBinding.inflate(LayoutInflater.from(parent.context), parent, false), onClick)
    }

    override fun onBindViewHolder(holder: ParticipantsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}