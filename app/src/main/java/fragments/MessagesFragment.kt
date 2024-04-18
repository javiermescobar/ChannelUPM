package fragments

import adapters.PrivateMessageContactAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.javier.channelupm.R
import com.javier.channelupm.databinding.FragmentMessagesBinding
import models.PrivateMessageContact
import repositories.ContactsRepository
import repositories.MessagesRepository
import utils.Constants
import utils.ItemDecorator
import viewModels.ContactsViewModel
import viewModels.MessagesViewModel

class MessagesFragment: BaseFragment() {

    private lateinit var binding: FragmentMessagesBinding
    private lateinit var contactsViewModel: ContactsViewModel
    private lateinit var messagesViewModel: MessagesViewModel

    private var contacts = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessagesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initializeView() {
        contactsViewModel = ContactsViewModel(ContactsRepository(), baseViewModel)
        messagesViewModel = MessagesViewModel(MessagesRepository(), baseViewModel)
        contactsViewModel.getContacts(Constants.currentUser.UserId)

        binding.contactsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.contactsRecyclerView.addItemDecoration(ItemDecorator(0))

        binding.searchInput.isEnabled = false
        binding.searchInput.addTextChangedListener {
            it?.let {
                if(it.isNotEmpty()) {
                    contactsViewModel.searchContacts(Constants.currentUser.UserId, it.toString())
                } else {
                    contactsViewModel.getContacts(Constants.currentUser.UserId)
                }
            }
        }
    }

    override fun subscribe() {
        contactsViewModel.mutableContacts.observe(this, Observer { users ->
            contacts = users.size
            binding.searchInput.isEnabled = contacts > 1
            users.forEach { messagesViewModel.getLastPrivateMessage(Constants.currentUser.UserId, it)}
        })

        messagesViewModel.mutableMessagesContact.observe(this, Observer { messagesContactMap ->
            if(messagesContactMap.size == contacts) {
                val messagesContact = mutableListOf<PrivateMessageContact>()
                messagesContactMap.forEach { (user, messageInfo) ->
                    messagesContact.add(PrivateMessageContact(user.UserId, messageInfo.SenderId, user.AvatarImage, user.Name, messageInfo.Text))
                }

                binding.contactsRecyclerView.adapter = PrivateMessageContactAdapter(
                    messagesContact.toList().sortedBy { user -> user.Name }) { user ->
                    val navBundle = Bundle()
                    navBundle.putSerializable(Constants.CONTACT_ID, user.UserId)
                    navBundle.putSerializable(Constants.CONTACT_INFO_AVATAR, user.AvatarImage)
                    navBundle.putSerializable(Constants.CONTACT_INFO_NAME, user.Name)
                    findNavController().navigate(R.id.action_messages_fragment_to_private_chat_fragment, navBundle)
                }
            }
        })
    }
}