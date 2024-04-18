package fragments

import adapters.ContactsAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.javier.channelupm.R
import com.javier.channelupm.databinding.FragmentAddContactBinding
import models.User
import repositories.ContactsRepository
import utils.AppState
import utils.Constants
import utils.ItemDecorator
import viewModels.ContactsViewModel

class AddContactFragment: BaseFragment() {
    private lateinit var binding: FragmentAddContactBinding
    private lateinit var contactsViewModel: ContactsViewModel
    private lateinit var contactsAdapter: ContactsAdapter
    private lateinit var availableContacts: MutableList<User>
    private lateinit var currentContacts: List<User>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddContactBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initializeView() {
        contactsViewModel = ContactsViewModel(ContactsRepository(), baseViewModel)
        contactsViewModel.getContacts(Constants.currentUser.UserId)
        contactsViewModel.getUsers()

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.contactsRecyclerView.layoutManager = LinearLayoutManager(this.activity, LinearLayoutManager.VERTICAL, false)
        binding.contactsRecyclerView.addItemDecoration(ItemDecorator(0))

        binding.searchInput.addTextChangedListener {
            it?.let {
                if(it.isNotEmpty()) {
                    contactsViewModel.searchUser(it.toString())
                } else {
                    contactsViewModel.getContacts(Constants.currentUser.UserId)
                }
            }
        }
    }

    override fun subscribe() {
        contactsViewModel.mutableUsers.observe(this, Observer {
            availableContacts = it as MutableList<User>
            if(this::currentContacts.isInitialized) {
                currentContacts.forEach { contact ->
                    if(availableContacts.contains(contact)) {
                        availableContacts.remove(contact)
                    }
                }

                contactsAdapter = ContactsAdapter(availableContacts.toList().filter {
                    contact -> contact.UserId != Constants.currentUser.UserId
                }) { contact ->
                    val navBundle = Bundle()
                    navBundle.putSerializable(Constants.CONTACT_INFO_NAME, contact.Name)
                    navBundle.putSerializable(Constants.CONTACT_INFO_MAIL, contact.Mail)
                    navBundle.putSerializable(Constants.CONTACT_INFO_DESCRIPTION, contact.Description)
                    navBundle.putSerializable(Constants.CONTACT_INFO_AVATAR, contact.AvatarImage)
                    navBundle.putSerializable(Constants.CONTACT_ID, contact.UserId)
                    findNavController().navigate(R.id.action_add_contact_fragment_to_contact_info_fragment, navBundle)
                }
                binding.contactsRecyclerView.adapter = contactsAdapter
            }
        })

        contactsViewModel.mutableContacts.observe(this, Observer {
            currentContacts = it
            if(this::availableContacts.isInitialized) {
                currentContacts.forEach { contact ->
                    if(availableContacts.contains(contact)) {
                        availableContacts.remove(contact)
                    }
                }

                contactsAdapter = ContactsAdapter(availableContacts.toList().filter {
                        contact -> contact.UserId != Constants.currentUser.UserId
                }) { contact ->
                    val navBundle = Bundle()
                    navBundle.putSerializable(Constants.CONTACT_INFO_NAME, contact.Name)
                    navBundle.putSerializable(Constants.CONTACT_INFO_MAIL, contact.Mail)
                    navBundle.putSerializable(Constants.CONTACT_INFO_DESCRIPTION, contact.Description)
                    navBundle.putSerializable(Constants.CONTACT_INFO_AVATAR, contact.AvatarImage)
                    navBundle.putSerializable(Constants.CONTACT_ID, contact.UserId)
                    findNavController().navigate(R.id.action_add_contact_fragment_to_contact_info_fragment, navBundle)
                }
                binding.contactsRecyclerView.adapter = contactsAdapter
            }
        })
    }
}