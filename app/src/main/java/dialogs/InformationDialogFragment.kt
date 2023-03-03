package dialogs

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.javier.channelupm.databinding.DialogInformationBinding

class InformationDialogFragment(
    private val activity: Activity,
    private val informationMessage: Int
): DialogFragment() {

    private lateinit var binding: DialogInformationBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogInformationBinding.inflate(layoutInflater)

        val builder = AlertDialog.Builder(activity)
        builder.setView(binding.root)
        val dialog = builder.create()

        binding.informationMessage.setText(informationMessage)
        binding.informationConfirmationButton.setOnClickListener {
            dialog.dismiss()
        }

        return dialog
    }
}