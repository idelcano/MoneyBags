package com.idelcano.moneycontrol.moneycontrol.presentation.views

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.idelcano.moneycontrol.moneycontrol.R
import com.idelcano.moneycontrol.moneycontrol.data.repositories.MoneyBagRepository
import com.idelcano.moneycontrol.moneycontrol.domain.entity.MoneyBag
import com.idelcano.moneycontrol.moneycontrol.domain.usecase.DeleteMoneyBagUseCase
import com.idelcano.moneycontrol.moneycontrol.domain.usecase.GetMoneyBagsUseCase
import com.idelcano.moneycontrol.moneycontrol.presentation.executers.CoroutinesExecutor
import com.idelcano.moneycontrol.moneycontrol.presentation.presenters.MainActivityPresenter
import com.idelcano.moneycontrol.moneycontrol.presentation.presenters.adapters.MoneyBagAdapter
import com.idelcano.moneycontrol.moneycontrol.presentation.views.fragments.BaseFragment
import com.idelcano.moneycontrol.moneycontrol.presentation.views.fragments.MoneyAmountCreatorDialogFragment
import com.idelcano.moneycontrol.moneycontrol.presentation.views.fragments.MoneyBagCreatorDialogFragment
import kotlinx.android.synthetic.main.activity_main.fab
import kotlinx.android.synthetic.main.content_main.header_text
import kotlinx.android.synthetic.main.content_main.progress_bar
import kotlinx.android.synthetic.main.content_main.recycler

class MainActivity : AppCompatActivity(), MainActivityPresenter.View {

    var presenter: MainActivityPresenter = MainActivityPresenter()
    lateinit var adapter: MoneyBagAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeFabAction()
        initializeRecyclerView()
        initializePresenter()
    }

    private fun initializeFabAction() {
        fab.setOnClickListener { view ->
            Snackbar.make(view, R.string.create_money, Snackbar.LENGTH_LONG)
                .setAction(R.string.add, View.OnClickListener {
                    presenter.openMoneyBagCreatorFragment()
                }).show()
        }
    }

    override fun showMoneyBags(moneyBags: List<MoneyBag?>) {
        adapter.setMoneyBags(moneyBags)
    }

    override fun clearMoneyBags() {
        adapter.clearMoneyBags()
    }

    override fun showLoading() {
        progress_bar.visibility = View.VISIBLE
        header_text.text = getString(R.string.loading_list_text)
    }

    override fun hideLoading() {
        progress_bar.visibility = View.GONE
    }

    override fun showTotalMoneyBags(count: Int) {
        header_text.text = String.format(getString(R.string.header_text), count)
    }

    private fun initializeRecyclerView() {
        this.adapter = MoneyBagAdapter(
            { item: MoneyBag -> presenter.onAddButtonClicked(item) },
            { item: MoneyBag -> presenter.onLogButtonClicked(item) },
            { item: MoneyBag -> presenter.onRemoveButtonClicked(item) })
        recycler.adapter = adapter
    }

    private fun initializePresenter() {
        var coroutinesExecutor = CoroutinesExecutor()
        presenter.initPresenter(this,
            GetMoneyBagsUseCase(MoneyBagRepository(), coroutinesExecutor),
            DeleteMoneyBagUseCase(MoneyBagRepository(), coroutinesExecutor)
            )
        presenter.loadData()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun onAttachFragment(fragment: Fragment?) {
        super.onAttachFragment(fragment)
        if (fragment is BaseFragment) {
            fragment.setListener(mMyFragmentListener)
            fragment.addDialogCreatorFun(dialogCreator)
        }
    }

    private val mMyFragmentListener = object : BaseFragment.Listener {
        override fun onDetached(fragment: BaseFragment) {
            if (fragment is (MoneyBagCreatorDialogFragment) ||
                fragment is (MoneyAmountCreatorDialogFragment)) {
                presenter.loadData()
            }
            fragment.setListener(null)
        }
    }

    open fun showDialog(func: () -> Unit, messageId: Int) {
        dialogCreator(func, messageId)
    }

    val dialogCreator = fun (func: () -> Unit, messageId: Int) {
        var dialog: AlertDialog? = null

        val builder = AlertDialog.Builder(this)

        // Set a messageId for alert dialog
        builder.setMessage(messageId)

        // On click listener for dialog buttons
        val dialogClickListener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    func()
                    dialog!!.dismiss()
                }
                DialogInterface.BUTTON_NEGATIVE -> dialog!!.dismiss()
            }
        }

        // Set the alert dialog positive/yes button
        builder.setPositiveButton(R.string.yes, dialogClickListener)

        // Set the alert dialog negative/no button
        builder.setNegativeButton(R.string.no, dialogClickListener)

        // Initialize the AlertDialog using builder object
        dialog = builder.create()

        // Finally, display the alert dialog
        dialog.show()
    }
}
