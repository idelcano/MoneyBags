package com.idelcano.moneycontrol.moneycontrol.fragments

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.idelcano.moneycontrol.moneycontrol.R
import com.idelcano.moneycontrol.moneycontrol.domain.entity.MoneyBag
import com.idelcano.moneycontrol.moneycontrol.presentation.presenters.MoneyBagEditorDialogPresenter
import kotlinx.android.synthetic.main.edit_money_bag_dialog_layout.*
import kotlinx.android.synthetic.main.edit_money_bag_dialog_layout.view.*

class MoneyBagEditorDialogFragment : BaseFragment(), MoneyBagEditorDialogPresenter.View {

    lateinit var presenterCreator : MoneyBagEditorDialogPresenter
    lateinit var moneyBag : MoneyBag

    val TAG_DIALOG: String = "MoneyBagEditorDialog"

    override fun showNameError() {
        edit_name.setError(getString(R.string.name_error))
    }

    override fun showAmountError() {
        edit_amount.setError(getString(R.string.amount_error))
    }
    override fun cancel() {
        presenterCreator.close()
    }

    override fun remove() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveMoneyAmount() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.WideDialog);
        moneyBag = getArguments()!!.getSerializable(MoneyBag.javaClass.canonicalName) as MoneyBag
    }

    override fun onDestroy() {
        presenterCreator.destroyView()
        super.onDestroy()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view : View = inflater.inflate(R.layout.edit_money_bag_dialog_layout, container, false)
        presenterCreator = MoneyBagEditorDialogPresenter()
        //presenterCreator.initPresenter(this, SaveMoneyBagUseCase(MoneyBagRepository(), CoroutinesExecutor()))

        view.save.setOnClickListener { view ->
            saveMoneyAmount()
        }

        view.remove.setOnClickListener { view ->
            remove()
        }

        view.cancel.setOnClickListener { view ->
            cancel()
        }
        setCancelable(false)

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
