package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.javier.channelupm.R
import com.javier.channelupm.databinding.FragmentContactInfoBinding
import com.squareup.picasso.Picasso
import repositories.ContactsRepository
import repositories.LoginRepository
import utils.AppState
import utils.Constants
import viewModels.ContactsViewModel
import viewModels.LoginViewModel

class ContactInfoFragment: BaseFragment() {

    private lateinit var binding: FragmentContactInfoBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var contactsViewModel: ContactsViewModel

    private var contactId = -1
    private var placeholderName = ""
    private var placeholderMail = ""
    private var placeholderDescription = ""
    private var placeholderAvatar = ""
    private var removingContact = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactInfoBinding.inflate(layoutInflater)

        arguments?.let {
            contactId = it.getInt(Constants.CONTACT_ID, -1)
            placeholderName = it.getString(Constants.CONTACT_INFO_NAME, "")
            placeholderMail = it.getString(Constants.CONTACT_INFO_MAIL, "")
            placeholderDescription = it.getString(Constants.CONTACT_INFO_DESCRIPTION, "")
            placeholderAvatar = it.getString(Constants.CONTACT_INFO_AVATAR, "")
        }

        return binding.root
    }

    override fun initializeView() {
        contactsViewModel = ContactsViewModel(ContactsRepository(), baseViewModel)

        binding.apply {
            backButton.setOnClickListener {
                findNavController().popBackStack()
            }
            if(contactId == -1) {
                contactName.text = placeholderName
                contactMail.text = placeholderMail
                if(placeholderDescription.isNotEmpty()) {
                    contactDescription.setText(placeholderDescription)
                } else {
                    contactDescription.setText(resources.getText(R.string.contact_info_no_description))
                }
                if(placeholderAvatar.isNotEmpty()) {
                    Picasso.with(root.context)
                        .load(placeholderAvatar)
                        .resize(contactImage.layoutParams.width, contactImage.layoutParams.height)
                        .placeholder(R.drawable.user_default)
                        .into(contactImage)
                }
            } else {
                loginViewModel = LoginViewModel(LoginRepository(), baseViewModel)
                loginViewModel.getUserById(contactId)
                toChatButton.visibility = View.GONE
                removeContactButton.visibility = View.GONE
            }

            toChatButton.setOnClickListener {
                goToPrivateChat()
            }

            removeContactButton.setOnClickListener {
                removingContact = true
                contactsViewModel.removeContact(Constants.currentUser.UserId, contactId)
            }
        }
    }

    override fun subscribe() {
        loginViewModel.currentUser.observe(this, Observer {
            binding.apply {
                contactName.text = it.Name
                placeholderName = it.Name
                contactMail.text = if(it.Mail.isNotEmpty()) {
                    it.Mail
                } else {
                    resources.getText(R.string.contact_info_no_mail)
                }
                contactDescription.keyListener = null
                contactDescription.setText(if(it.Description.isNotEmpty()) {
                    it.Description
                } else {
                    resources.getText(R.string.contact_info_no_description)
                })
                if(it.AvatarImage.isNotEmpty()) {
                    placeholderAvatar = it.AvatarImage
                    Picasso.with(root.context)
                        .load(it.AvatarImage)
                        .resize(contactImage.layoutParams.width, contactImage.layoutParams.height)
                        .placeholder(R.drawable.user_default)
                        .into(contactImage)
                }
                if(it.UserId != Constants.currentUser.UserId) {
                    toChatButton.visibility = View.VISIBLE
                    removeContactButton.visibility = View.VISIBLE
                }
            }
        })

        baseViewModel.appState.observe(this, Observer {
            if(it == AppState.SUCCESS && removingContact) {
                removingContact = false
                showInformationDialog(R.string.contact_removed, false)
                findNavController().popBackStack()
            }
        })
    }

    private fun goToPrivateChat() {
        arguments?.let {
            val navBundle = Bundle()
            navBundle.putSerializable(Constants.CONTACT_ID, it.getSerializable(Constants.CONTACT_ID))
            navBundle.putSerializable(Constants.CONTACT_INFO_AVATAR, placeholderAvatar)
            navBundle.putSerializable(Constants.CONTACT_INFO_NAME, placeholderName)
            findNavController().navigate(R.id.action_contact_info_fragment_to_private_chat_fragment, navBundle)
        }
    }
}