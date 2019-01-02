package com.idelcano.moneycontrol.moneycontrol.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.idelcano.moneycontrol.moneycontrol.R
import com.idelcano.moneycontrol.moneycontrol.data.repositories.MoneyBagRepository
import com.idelcano.moneycontrol.moneycontrol.domain.usecase.SaveMoneyBagUseCase
import com.idelcano.moneycontrol.moneycontrol.presentation.executers.CoroutinesExecutor
import com.idelcano.moneycontrol.moneycontrol.presentation.presenters.MoneyBagDialogPresenter
import kotlinx.android.synthetic.main.create_money_bag_dialog_layout.*
import kotlinx.android.synthetic.main.create_money_bag_dialog_layout.view.*
import java.util.*

class MoneyBagDialogFragment : BaseFragment(), MoneyBagDialogPresenter.View {

    lateinit var presenter : MoneyBagDialogPresenter

    val TAG_DIALOG: String = "MoneyBagDialog"

    override fun showNameError() {
        edit_name.setError(getString(R.string.name_error))
    }

    override fun showDateError() {
        edit_date.setError(getString(R.string.date_error))
    }

    override fun showAmountError() {
        edit_amount.setError(getString(R.string.amount_error))
    }
    override fun cancel() {
        presenter.close()
    }

    override fun saveMoneyBag() {
        presenter.saveMoneyBag()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.WideDialog);
    }

    override fun onDestroy() {
        presenter.destroyView()
        super.onDestroy()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view : View = inflater.inflate(R.layout.create_money_bag_dialog_layout, container, false)
        presenter = MoneyBagDialogPresenter()
        presenter.initPresenter(this, SaveMoneyBagUseCase(MoneyBagRepository(), CoroutinesExecutor()))

        view.save.setOnClickListener { view ->
            saveMoneyBag()
        }

        view.cancel.setOnClickListener { view ->
            cancel()
        }
        setCancelable(false)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(activity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            edit_date.setText(presenter.formatDateToUI(year, monthOfYear, dayOfMonth))
        }, year, month, day)

        view.edit_date.setOnClickListener({
                v ->
            view.edit_date.setError(null)
            dpd.show()
        })

        view.edit_name.setOnClickListener({
                v ->
            view.edit_name.setError(null)
        })

        view.edit_amount.setOnClickListener({
                v ->
            view.edit_amount.setError(null)
        })
        return view
    }
}
