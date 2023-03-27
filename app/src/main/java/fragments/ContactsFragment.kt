package fragments

import adapters.ContactsAdapter
import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.javier.channelupm.databinding.FragmentContactsBinding
import repositories.ContactsRepository
import utils.Constants
import utils.ItemDecorator
import viewModels.ContactsViewModel

class ContactsFragment: BaseFragment() {

    private var searching = false

    private lateinit var binding: FragmentContactsBinding
    private lateinit var contactsViewModel: ContactsViewModel
    private lateinit var contactsAdapter: ContactsAdapter

    companion object {
        private const val MIN_SEARCH_LENGHT = 3
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initializeView() {
        contactsViewModel = ContactsViewModel(ContactsRepository(), baseViewModel)
        contactsViewModel.getContacts(Constants.currentUser.UserId)

        binding.contactsRecyclerView.layoutManager = LinearLayoutManager(this.activity, LinearLayoutManager.VERTICAL, false)
        binding.contactsRecyclerView.addItemDecoration(ItemDecorator(0))

        binding.searchInput.addTextChangedListener {
            it?.let {
                if(it.length >= MIN_SEARCH_LENGHT) {
                    contactsViewModel.searchContacts(Constants.currentUser.UserId, it.toString())
                    searching = true
                } else {
                    if(searching) {
                        searching = false
                        contactsViewModel.getContacts(Constants.currentUser.UserId)
                    }
                }
            }
        }
    }

    override fun subscribe() {
        contactsViewModel.mutableContacts.observe(this, Observer {
            contactsAdapter = ContactsAdapter(it){}
            binding.contactsRecyclerView.adapter = contactsAdapter
        })
    }
}