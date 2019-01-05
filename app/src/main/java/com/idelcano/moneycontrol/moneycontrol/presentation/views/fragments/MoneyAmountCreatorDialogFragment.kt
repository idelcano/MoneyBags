package com.idelcano.moneycontrol.moneycontrol.presentation.views.fragments

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.idelcano.moneycontrol.moneycontrol.R
import com.idelcano.moneycontrol.moneycontrol.data.repositories.MoneyAmountRepository
import com.idelcano.moneycontrol.moneycontrol.data.repositories.MoneyBagRepository
import com.idelcano.moneycontrol.moneycontrol.domain.entity.MoneyBag
import com.idelcano.moneycontrol.moneycontrol.domain.usecase.DeleteMoneyBagUseCase
import com.idelcano.moneycontrol.moneycontrol.domain.usecase.GetMoneyBagUseCase
import com.idelcano.moneycontrol.moneycontrol.domain.usecase.SaveMoneyAmountUseCase
import com.idelcano.moneycontrol.moneycontrol.presentation.executers.CoroutinesExecutor
import com.idelcano.moneycontrol.moneycontrol.presentation.presenters.MoneyBagEditorDialogPresenter
import kotlinx.android.synthetic.main.create_money_amount_dialog_layout.*
import kotlinx.android.synthetic.main.create_money_amount_dialog_layout.view.*

class MoneyBagEditorDialogFragment : BaseFragment(), MoneyBagEditorDialogPresenter.View {
    lateinit var presenter: MoneyBagEditorDialogPresenter

    val TAG_DIALOG: String = "MoneyBagEditorDialog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.WideDialog);
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.create_money_amount_dialog_layout, container, false)
        presenter = MoneyBagEditorDialogPresenter()
        val coroutinesExecutor = CoroutinesExecutor()
        val uid : String = getArguments()!!.getString(MoneyBag.javaClass.canonicalName)

        presenter.initPresenter(this,
            DeleteMoneyBagUseCase(MoneyBagRepository(), coroutinesExecutor),
            SaveMoneyAmountUseCase(MoneyAmountRepository(), coroutinesExecutor),
            GetMoneyBagUseCase(MoneyBagRepository(), coroutinesExecutor)
            )

        presenter.loadMoneyBag(uid)


        view.save_money_amount.setOnClickListener { view ->
            saveMoneyAmount()
        }

        view.remove_money_bag.setOnClickListener { view ->
            remove()
        }

        view.cancel_edit_dialog.setOnClickListener { view ->
            cancel()
        }

        setCancelable(false)

        view.edit_name.setOnClickListener({ v ->
            view.edit_name.setError(null)
        })

        view.edit_amount.setOnClickListener({ v ->
            view.edit_amount.setError(null)
        })

        return view
    }

    override fun onDestroy() {
        presenter.destroyView()
        super.onDestroy()
    }

    override fun showNameError() {
        edit_name.setError(getString(R.string.name_error))
    }

    override fun showAmountError() {
        edit_amount.setError(getString(R.string.amount_error))
    }

    override fun cancel() {
        presenter.close()
    }

    override fun remove() {
        presenter.remove()
    }

    override fun saveMoneyAmount() {
        presenter.saveMoneyAmont()
    }

    override fun showDialog(func: () -> Unit, messageId: Int) {
        super.showDialog(func, messageId)
    }

}
