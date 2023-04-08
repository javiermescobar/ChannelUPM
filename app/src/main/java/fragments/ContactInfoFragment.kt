package fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.javier.channelupm.R
import com.javier.channelupm.databinding.FragmentContactInfoBinding
import utils.Constants

class ContactInfoFragment: BaseFragment() {

    private lateinit var binding: FragmentContactInfoBinding

    private var placeholderName = ""
    private var placeholderMail = ""
    private var placeholderDescription = ""
    private var placeholderAvatar = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactInfoBinding.inflate(layoutInflater)

        arguments?.let {
            placeholderName = it.getString(Constants.CONTACT_INFO_NAME, "")
            placeholderMail = it.getString(Constants.CONTACT_INFO_MAIL, "")
            placeholderDescription = it.getString(Constants.CONTACT_INFO_DESCRIPTION, "")
            placeholderAvatar = it.getString(Constants.CONTACT_INFO_AVATAR, "")
        }

        return binding.root
    }

    override fun initializeView() {
        binding.apply {
            backButton.setOnClickListener {
                findNavController().popBackStack()
            }
            contactName.text = placeholderName
            contactMail.text = placeholderMail
            if(placeholderDescription.isNotEmpty()) {
                contactDescription.text = placeholderDescription
            } else {
                contactDescription.text = resources.getText(R.string.contact_info_no_description)
            }
            if(placeholderAvatar.isNotEmpty()) {
                contactImage.setImageURI(Uri.parse(placeholderAvatar))
            }
        }
    }
}