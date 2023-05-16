package adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.javier.channelupm.databinding.HolderContactGroupBinding
import holders.ContactsViewHolder
import models.User

class ContactsAdapter(
    val items: List<User>,
    val onClick:(item: User) -> Unit
    ): RecyclerView.Adapter<ContactsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        return ContactsViewHolder(HolderContactGroupBinding.inflate(LayoutInflater.from(parent.context),parent, false), onClick)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}