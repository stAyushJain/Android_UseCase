package com.target.targetcasestudy.deals.presentation.payment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.target.targetcasestudy.R
import com.target.targetcasestudy.util.validateCreditCard

/**
 * Dialog that displays a minimal credit card entry form.
 *
 * Your task here is to enable the "submit" button when the credit card number is valid and
 * disable the button when the credit card number is not valid.
 *
 * You do not need to input any of your actual credit card information. See `CardValidators.kt` for
 * info to help you get fake credit card numbers.
 *
 * You do not need to make any changes to the layout of this screen (though you are welcome to do
 * so if you wish).
 */
class PaymentDialogFragment : DialogFragment() {

  private lateinit var submitButton: Button
  private lateinit var creditCardInput: EditText

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val root = inflater.inflate(R.layout.dialog_payment, container, false)

    submitButton = root.findViewById(R.id.submit)
    creditCardInput = root.findViewById(R.id.card_number)
    val cancelButton: Button = root.findViewById(R.id.cancel)

    cancelButton.setOnClickListener { dismiss() }
    submitButton.setOnClickListener { dismiss() }

    creditCardInput.addTextChangedListener(object: TextWatcher {
      override fun afterTextChanged(s: Editable?) {}

      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        submitButton.isEnabled = validateCreditCard(s.toString())
      }
    })

    return root
  }

}