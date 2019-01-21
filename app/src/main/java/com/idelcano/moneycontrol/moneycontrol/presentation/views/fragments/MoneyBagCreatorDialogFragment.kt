package com.idelcano.moneycontrol.moneycontrol.presentation.views.fragments

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
import com.idelcano.moneycontrol.moneycontrol.presentation.presenters.MoneyBagCreatorDialogPresenter
import kotlinx.android.synthetic.main.create_money_bag_dialog_layout.edit_amount
import kotlinx.android.synthetic.main.create_money_bag_dialog_layout.edit_date
import kotlinx.android.synthetic.main.create_money_bag_dialog_layout.edit_name
import kotlinx.android.synthetic.main.create_money_bag_dialog_layout.view.cancel_money_creator_dialog
import kotlinx.android.synthetic.main.create_money_bag_dialog_layout.view.edit_amount
import kotlinx.android.synthetic.main.create_money_bag_dialog_layout.view.edit_date
import kotlinx.android.synthetic.main.create_money_bag_dialog_layout.view.edit_name
import kotlinx.android.synthetic.main.create_money_bag_dialog_layout.view.save_money_bag
import java.util.Calendar

class MoneyBagCreatorDialogFragment : BaseFragment(), MoneyBagCreatorDialogPresenter.View {

    lateinit var presenterCreator: MoneyBagCreatorDialogPresenter

    companion object {
        const val TAG_DIALOG: String = "MoneyBagCreatorDialog"
    }

    var companion = Companion

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
        presenterCreator.close()
    }

    override fun saveMoneyBag() {
        presenterCreator.saveMoneyBag()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.WideDialog)
    }

    override fun onDestroy() {
        presenterCreator.destroyView()
        super.onDestroy()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.create_money_bag_dialog_layout, container, false)
        presenterCreator = MoneyBagCreatorDialogPresenter()
        presenterCreator.initPresenter(this, SaveMoneyBagUseCase(MoneyBagRepository(), CoroutinesExecutor()))

        view.save_money_bag.setOnClickListener { _ ->
            saveMoneyBag()
        }

        view.cancel_money_creator_dialog.setOnClickListener { _ ->
            cancel()
        }
        setCancelable(false)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(activity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            edit_date.setText(presenterCreator.formatDateToUI(year, monthOfYear, dayOfMonth))
        }, year, month, day)

        view.edit_date.setOnClickListener({
                view ->
            view.edit_date.setError(null)
            dpd.show()
        })

        view.edit_name.setOnClickListener({
                view ->
            view.edit_name.setError(null)
        })

        view.edit_amount.setOnClickListener({
                view ->
            view.edit_amount.setError(null)
        })
        return view
    }
}
