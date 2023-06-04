package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.javier.channelupm.R
import com.javier.channelupm.databinding.FragmentUserProfileBinding
import com.squareup.picasso.Picasso
import repositories.RegisterRepository
import utils.Constants
import viewModels.RegisterViewModel

class UserProfileFragment: BaseFragment() {

    private lateinit var binding: FragmentUserProfileBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initializeView() {
        registerViewModel = RegisterViewModel(RegisterRepository(), baseViewModel)

        binding.apply {
            backButton.setOnClickListener {
                findNavController().popBackStack()
            }

            profileName.setText(Constants.currentUser.Name)

            profileMail.text = Constants.currentUser.Mail
            profileMail.setOnClickListener {
                showInformationDialog(R.string.cant_edit_mail, true)
            }

            if(Constants.currentUser.AvatarImage.isNotEmpty()) {
                Picasso.with(root.context)
                    .load(Constants.currentUser.AvatarImage)
                    .placeholder(R.drawable.user_default)
                    .resize(200,200)
                    .centerCrop()
                    .into(profileImage)
            }

            if(Constants.currentUser.AvatarImage.isNotEmpty()) {
                urlInput.setText(Constants.currentUser.AvatarImage)
            }

            urlInput.doAfterTextChanged {
                if(!it.isNullOrEmpty()) {
                    try {
                        Picasso.with(root.context)
                            .load(it.toString())
                            .placeholder(R.drawable.user_default)
                            .resize(200,200)
                            .centerCrop()
                            .into(profileImage)
                    } catch (e: Exception) {
                        profileImage.setImageResource(R.drawable.user_default)
                    }
                }
            }

            if(Constants.currentUser.Description.isEmpty()) {
                profileDescription.hint = getString(R.string.description_is_empty)
            } else {
                profileDescription.setText(Constants.currentUser.Description)
            }

            confirmButton.setOnClickListener {
                if(profileName.text.toString().isEmpty()) {
                    showInformationDialog(R.string.name_cant_be_empty, true)
                } else {
                    registerViewModel.updateUserInformation(profileName.text.toString(),
                    profileDescription.text.toString(), urlInput.text.toString())
                }
            }
        }
    }

    override fun subscribe() {
        registerViewModel.mutableUserInformationUpdated.observe(this, Observer {
            if(it) {
                showInformationDialog(R.string.user_updated, false)
                findNavController().popBackStack()
            }
        })
    }
}