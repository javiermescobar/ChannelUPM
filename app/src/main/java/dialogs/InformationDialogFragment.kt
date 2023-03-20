package dialogs

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.javier.channelupm.R
import com.javier.channelupm.databinding.DialogInformationBinding

class InformationDialogFragment(
    private val activity: Activity,
    private val informationMessage: Int,
    private val isWarning: Boolean
): DialogFragment() {

    private lateinit var binding: DialogInformationBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogInformationBinding.inflate(layoutInflater)

        val builder = AlertDialog.Builder(activity)
        builder.setView(binding.root)
        val dialog = builder.create()

        binding.informaitonImage.setImageDrawable(
            if(isWarning) {
                resources.getDrawable(R.drawable.ic_warning)
            } else {
                resources.getDrawable(R.drawable.ic_check)
            }
        )

        binding.informationMessage.setText(informationMessage)
        binding.informationConfirmationButton.setOnClickListener {
            dialog.dismiss()
        }

        return dialog
    }
}