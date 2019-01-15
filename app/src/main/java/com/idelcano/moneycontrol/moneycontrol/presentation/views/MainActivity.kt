package com.idelcano.moneycontrol.moneycontrol.presentation.views

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.idelcano.moneycontrol.moneycontrol.R
import com.idelcano.moneycontrol.moneycontrol.data.repositories.DayCounterRepository
import com.idelcano.moneycontrol.moneycontrol.data.repositories.MoneyBagRepository
import com.idelcano.moneycontrol.moneycontrol.domain.entity.MoneyBag
import com.idelcano.moneycontrol.moneycontrol.domain.usecase.DeleteDayCounterUseCase
import com.idelcano.moneycontrol.moneycontrol.domain.usecase.DeleteMoneyBagUseCase
import com.idelcano.moneycontrol.moneycontrol.domain.usecase.GetDayCounterUseCase
import com.idelcano.moneycontrol.moneycontrol.domain.usecase.GetMoneyBagsUseCase
import com.idelcano.moneycontrol.moneycontrol.presentation.executers.CoroutinesExecutor
import com.idelcano.moneycontrol.moneycontrol.presentation.presenters.MainActivityPresenter
import com.idelcano.moneycontrol.moneycontrol.presentation.presenters.adapters.IListable
import com.idelcano.moneycontrol.moneycontrol.presentation.presenters.adapters.ListableItemAdapter
import com.idelcano.moneycontrol.moneycontrol.presentation.views.fragments.BaseFragment
import com.idelcano.moneycontrol.moneycontrol.presentation.views.fragments.DayCounterCreatorDialogFragment
import com.idelcano.moneycontrol.moneycontrol.presentation.views.fragments.MoneyAmountCreatorDialogFragment
import com.idelcano.moneycontrol.moneycontrol.presentation.views.fragments.MoneyBagCreatorDialogFragment
import kotlinx.android.synthetic.main.activity_main.fab
import kotlinx.android.synthetic.main.content_main.header_text
import kotlinx.android.synthetic.main.content_main.progress_bar
import kotlinx.android.synthetic.main.content_main.recycler

class MainActivity : AppCompatActivity(), MainActivityPresenter.View {

    var presenter: MainActivityPresenter = MainActivityPresenter()
    lateinit var adapter: ListableItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeFabAction()
        initializeRecyclerView()
        initializePresenter()
    }

    private fun initializeFabAction() {
        fab.setOnClickListener { view ->
            presenter.openMenuDialog()
        }
    }

    override fun addItems(items: List<IListable?>) {
        adapter.setItems(items)
    }

    override fun clearItems() {
        adapter.clearItems()
    }

    override fun showLoading() {
        progress_bar.visibility = View.VISIBLE
        header_text.text = getString(R.string.loading_list_text)
    }

    override fun hideLoading() {
        progress_bar.visibility = View.GONE
    }

    override fun showItems() {
        adapter.showItems()
    }

    override fun showTotalItems(count: Int) {
        header_text.text = String.format(getString(R.string.header_text), count)
    }

    private fun initializeRecyclerView() {
        this.adapter = ListableItemAdapter(
            { item: MoneyBag -> presenter.onAddButtonClicked(item) },
            { item: MoneyBag -> presenter.onLogButtonClicked(item) },
            { item: IListable -> presenter.onRemoveButtonClicked(item) })
        recycler.adapter = adapter
    }

    private fun initializePresenter() {
        var coroutinesExecutor = CoroutinesExecutor()
        var moneyRepository = MoneyBagRepository()
        var dayCounterRepository = DayCounterRepository()
        presenter.initPresenter(this,
            GetMoneyBagsUseCase(moneyRepository, coroutinesExecutor),
            GetDayCounterUseCase(dayCounterRepository, coroutinesExecutor),
            DeleteMoneyBagUseCase(moneyRepository, coroutinesExecutor),
            DeleteDayCounterUseCase(dayCounterRepository, coroutinesExecutor)
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
                fragment is (MoneyAmountCreatorDialogFragment) ||
                fragment is (DayCounterCreatorDialogFragment)) {
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
