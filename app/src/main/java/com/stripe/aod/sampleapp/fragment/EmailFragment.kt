package com.stripe.aod.sampleapp.fragment

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.stripe.aod.sampleapp.R
import com.stripe.aod.sampleapp.data.EmailReceiptParams
import com.stripe.aod.sampleapp.databinding.FragmentEmailBinding
import com.stripe.aod.sampleapp.model.CheckoutViewModel
import com.stripe.aod.sampleapp.utils.backToHome
import com.stripe.aod.sampleapp.utils.hideKeyboard
import com.stripe.aod.sampleapp.utils.setThrottleClickListener

class EmailFragment : Fragment(R.layout.fragment_email) {
    private val emailRegex = "^[A-Za-z\\d+_.-]+@[A-Za-z\\d.-]+\$"
    private val viewMode by viewModels<CheckoutViewModel>()
    private val args: EmailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewBinding = FragmentEmailBinding.bind(view)

        viewBinding.back.setThrottleClickListener {
            findNavController().navigateUp()
        }

        viewBinding.inputEdit.doAfterTextChanged {
            val isValidEmail = it?.toString()?.matches(emailRegex.toRegex()) ?: false
            viewBinding.emailSend.isEnabled = isValidEmail
            viewBinding.inputLayout.error = if (it.isNullOrEmpty() || isValidEmail) {
                ""
            } else {
                getString(R.string.invalid_email)
            }
        }

        viewBinding.emailSend.setThrottleClickListener {
            viewBinding.inputEdit.hideKeyboard()
            viewMode.updateReceiptEmailPaymentIntent(
                EmailReceiptParams(
                    paymentIntentId = args.paymentIntentID,
                    receiptEmail = viewBinding.inputEdit.text.toString().trim()
                ),
                successCallback = {
                    backToHome()
                },
                failCallback = { message ->
                    Snackbar.make(
                        viewBinding.emailSend,
                        if (message.isNullOrEmpty()) {
                            getString(R.string.error_fail_to_send_email_receipt)
                        } else {
                            message
                        },
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }
}
