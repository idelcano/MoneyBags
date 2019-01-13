package com.idelcano.moneycontrol.moneycontrol.presentation.views.fragments

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.idelcano.moneycontrol.moneycontrol.R
import com.idelcano.moneycontrol.moneycontrol.presentation.presenters.Actions
import com.idelcano.moneycontrol.moneycontrol.presentation.presenters.MenuDialogPresenter
import kotlinx.android.synthetic.main.menu_dialog_layout.view.cancel_menu
import kotlinx.android.synthetic.main.menu_dialog_layout.view.create_day_counter
import kotlinx.android.synthetic.main.menu_dialog_layout.view.create_money_bag

class MenuDialogFragment : BaseFragment(), MenuDialogPresenter.View {

    lateinit var presenter: MenuDialogPresenter

    companion object {
        const val TAG_DIALOG: String = "MenuDialog"
    }

    var companion = Companion

    lateinit var doActions:(Actions) -> Unit

    fun addActions(doAction: (Actions) -> Unit){
        doActions = doAction
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.WideDialog)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.menu_dialog_layout, container, false)
        presenter = MenuDialogPresenter()

        presenter.initPresenter(this)

        view.create_money_bag.setOnClickListener { view ->
            cancel()
            doActions(Actions.CREATE_MONEY_BAG)
        }

        view.create_day_counter.setOnClickListener { view ->
            cancel()
            doActions(Actions.CREATE_DAY_COUNTER)
        }

        view.cancel_menu.setOnClickListener { view ->
            cancel()
        }

        setCancelable(false)

        return view
    }

    override fun onDestroy() {
        presenter.destroyView()
        super.onDestroy()
    }

    override fun cancel() {
        presenter.close()
    }
}
