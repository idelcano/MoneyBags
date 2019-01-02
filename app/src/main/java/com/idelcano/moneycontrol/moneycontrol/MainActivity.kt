package com.idelcano.moneycontrol.moneycontrol

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.idelcano.moneycontrol.moneycontrol.data.repositories.MoneyBagRepository
import com.idelcano.moneycontrol.moneycontrol.domain.entity.MoneyBag
import com.idelcano.moneycontrol.moneycontrol.domain.usecase.GetMoneyBagsUseCase
import com.idelcano.moneycontrol.moneycontrol.fragments.BaseFragment
import com.idelcano.moneycontrol.moneycontrol.fragments.MoneyBagCreatorDialogFragment
import com.idelcano.moneycontrol.moneycontrol.presentation.executers.CoroutinesExecutor
import com.idelcano.moneycontrol.moneycontrol.presentation.presenters.MainActivityPresenter
import com.idelcano.moneycontrol.moneycontrol.presentation.presenters.adapters.MoneyBagAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity(), MainActivityPresenter.View {

    var presenter : MainActivityPresenter = MainActivityPresenter()
    lateinit var adapter : MoneyBagAdapter
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
        header_text.text =getString(R.string.loading_list_text);
    }

    override fun hideLoading() {
        progress_bar.visibility = View.GONE
    }

    override fun showTotalMoneyBags(count: Int) {
        header_text.text =  String.format(getString(R.string.header_text),count)
    }

    private fun initializeRecyclerView() {
        this.adapter = MoneyBagAdapter() { item ->
            presenter.onMoneyBagClicked(item)
        }
        recycler.adapter = adapter
    }

    private fun initializePresenter() {
        presenter.initPresenter(this, GetMoneyBagsUseCase(MoneyBagRepository(), CoroutinesExecutor()))
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
        }
    }

    private val mMyFragmentListener = object : BaseFragment.Listener {
        override fun onDetached(fragment: BaseFragment) {
            if(fragment is (MoneyBagCreatorDialogFragment)){
                presenter.loadData()
            }
            fragment.setListener(null)
        }
    }
}
