package fragments

import adapters.ContactsAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.javier.channelupm.R
import com.javier.channelupm.databinding.FragmentAddParticipantBinding
import models.User
import models.UserInGroup
import repositories.ContactsRepository
import repositories.LoginRepository
import repositories.MessagesRepository
import utils.AppState
import utils.Constants
import viewModels.ContactsViewModel
import viewModels.MessagesViewModel

class AddParticipantFragment: BaseFragment() {

    private lateinit var binding: FragmentAddParticipantBinding
    private lateinit var messagesViewModel: MessagesViewModel
    private lateinit var contactsViewModel: ContactsViewModel

    private lateinit var participants: List<UserInGroup>
    private lateinit var contacts: List<User>

    private var groupId = -1
    private var adding = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddParticipantBinding.inflate(layoutInflater)
        arguments?.let {
            groupId = it.getInt(Constants.GROUP_ID)
        }
        return binding.root
    }

    override fun initializeView() {
        messagesViewModel = MessagesViewModel(MessagesRepository(), LoginRepository(), baseViewModel)
        messagesViewModel.getGroupParticipants(groupId)

        contactsViewModel = ContactsViewModel(ContactsRepository(), baseViewModel)
        contactsViewModel.getContacts(Constants.currentUser.UserId)

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun subscribe() {
        messagesViewModel.mutableGroupParticipants.observe(this, Observer {
            participants = it
            if(this::contacts.isInitialized && contacts.isNotEmpty()) {
                showAddibleContacts()
            }
        })

        contactsViewModel.mutableContacts.observe(this, Observer {
            contacts = it
            if(this::participants.isInitialized && participants.isNotEmpty()) {
                showAddibleContacts()
            }
        })

        baseViewModel.appState.observe(this, Observer {
            if(it == AppState.SUCCESS && adding) {
                showInformationDialog(R.string.particiant_added, false)
                findNavController().popBackStack()
            }
        })
    }

    private fun showAddibleContacts() {
        val mutableAddibleContacts = contacts.toMutableList()

        contacts.forEach { contact ->
            if(contactInGroup(contact.UserId) || contact.UserId == 100) {
                mutableAddibleContacts.remove(contact)
            }
        }

        binding.contactsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = ContactsAdapter(mutableAddibleContacts.toList()){
                adding = true
                messagesViewModel.addUserGroup(groupId, it.UserId, 0)
            }
        }
    }

    private fun contactInGroup(contactId: Int): Boolean {
        var inGroup = false
        participants.forEach {
            if(it.UserId == contactId) inGroup = true
        }
        return inGroup
    }
}