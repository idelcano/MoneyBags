package com.idelcano.moneycontrol.moneycontrol.presentation.presenters

import android.os.Bundle
import com.idelcano.moneycontrol.moneycontrol.domain.entity.MoneyBag
import com.idelcano.moneycontrol.moneycontrol.domain.usecase.GetMoneyBagsUseCase
import com.idelcano.moneycontrol.moneycontrol.presentation.views.MainActivity
import com.idelcano.moneycontrol.moneycontrol.presentation.views.fragments.BaseFragment
import com.idelcano.moneycontrol.moneycontrol.presentation.views.fragments.MoneyAmountCreatorDialogFragment
import com.idelcano.moneycontrol.moneycontrol.presentation.views.fragments.MoneyAmountLogDialogFragment
import com.idelcano.moneycontrol.moneycontrol.presentation.views.fragments.MoneyBagCreatorDialogFragment




class MainActivityPresenter{
    lateinit var getMoneyBagListUseCase : GetMoneyBagsUseCase
    var view : MainActivity? = null

    fun initPresenter(view : MainActivity, getMoneyBagListUseCase : GetMoneyBagsUseCase) {
        this.view = view
        this.getMoneyBagListUseCase = getMoneyBagListUseCase
    }

    fun loadData(){
        loadingMoneyBags()
        loadMoneyBags()
    }

    private fun loadMoneyBags() {
        getMoneyBagListUseCase.execute {
            showMoneyBags(it)
        }
    }

    private fun loadingMoneyBags() {
        view?.showLoading()
        view?.clearMoneyBags()
    }

    private fun showMoneyBags(moneyBags: List<MoneyBag?>) {
        view?.hideLoading()
        view?.showMoneyBags(moneyBags)
        view?.showTotalMoneyBags(moneyBags.size)
    }

    fun detachView() {
        view = null
    }

    fun onAddButtonClicked(item: MoneyBag) {
        openFragment(item, MoneyAmountCreatorDialogFragment(), MoneyAmountCreatorDialogFragment().TAG_DIALOG)
    }

    fun onLogButtonClicked(item: MoneyBag) {
        openFragment(item, MoneyAmountLogDialogFragment(), MoneyAmountLogDialogFragment().TAG_DIALOG)
    }

    fun openMoneyBagCreatorFragment() {
        val ft = view!!.supportFragmentManager
        val dialogFragment = MoneyBagCreatorDialogFragment()
        dialogFragment.show(ft, dialogFragment.TAG_DIALOG)
    }

    fun openFragment(item: MoneyBag, fragment: BaseFragment, tag: String) {
        val ft = view!!.supportFragmentManager
        val dialogFragment = fragment
        val args = Bundle()
        args.putString(MoneyBag.javaClass.canonicalName, item.uid)
        dialogFragment.setArguments(args)
        dialogFragment.show(ft, tag)
    }

    interface View {
        fun showMoneyBags(moneyBags: List<MoneyBag?>)
        fun clearMoneyBags()
        fun showLoading()
        fun hideLoading()
        fun showTotalMoneyBags(count: Int)
    }
}
