package com.idelcano.moneycontrol.moneycontrol.presentation.views.fragments

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.idelcano.moneycontrol.moneycontrol.R
import com.idelcano.moneycontrol.moneycontrol.data.repositories.MoneyAmountRepository
import com.idelcano.moneycontrol.moneycontrol.data.repositories.MoneyBagRepository
import com.idelcano.moneycontrol.moneycontrol.domain.entity.MoneyAmount
import com.idelcano.moneycontrol.moneycontrol.domain.entity.MoneyBag
import com.idelcano.moneycontrol.moneycontrol.domain.usecase.DeleteMoneyAmountUseCase
import com.idelcano.moneycontrol.moneycontrol.domain.usecase.GetMoneyBagUseCase
import com.idelcano.moneycontrol.moneycontrol.presentation.executers.CoroutinesExecutor
import com.idelcano.moneycontrol.moneycontrol.presentation.presenters.MoneyAmountLogDialogPresenter
import com.idelcano.moneycontrol.moneycontrol.presentation.presenters.adapters.MoneyAmountAdapter
import kotlinx.android.synthetic.main.money_bag_log_dialog.view.*

class MoneyAmountLogDialogFragment : BaseFragment(), MoneyAmountLogDialogPresenter.View {
    lateinit var presenter: MoneyAmountLogDialogPresenter
    lateinit var adapter: MoneyAmountAdapter

    val TAG_DIALOG: String = "MoneyAmountLogDialog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.WideDialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.money_bag_log_dialog, container, false)
        initializeRecyclerView(view)
        initPresenter()
        val uid: String = getArguments()!!.getString(MoneyBag.javaClass.canonicalName)

        presenter.loadMoneyBag(uid)

        view.cancel_money_log_dialog.setOnClickListener { view ->
            cancel()
        }
        setCancelable(false)
        return view
    }

    override fun showDialog(func: () -> Unit, message: Int) {
        createDialog(func, message)
    }

    private fun initializeRecyclerView(view: View) {
        this.adapter = MoneyAmountAdapter() { item ->
            presenter.onMoneyAmountTouch(item)
        }
        view.recycler_amount_bags.adapter = adapter
    }

    private fun initPresenter() {
        presenter = MoneyAmountLogDialogPresenter()
        val coroutinesExecutor = CoroutinesExecutor()

        presenter.initPresenter(this,
            GetMoneyBagUseCase(MoneyBagRepository(), coroutinesExecutor),
            DeleteMoneyAmountUseCase(MoneyAmountRepository(), coroutinesExecutor)
        )
    }

    override fun onDestroy() {
        presenter.destroyView()
        super.onDestroy()
    }

    override fun cancel() {
        presenter.close()
    }

    override fun showMoneyAmounts(moneyAmounts: List<MoneyAmount?>) {
        adapter.setMoneyAmounts(moneyAmounts)
    }

    override fun clearMoneyAmounts() {
        adapter.clearMoneyAmounts()
    }
}
