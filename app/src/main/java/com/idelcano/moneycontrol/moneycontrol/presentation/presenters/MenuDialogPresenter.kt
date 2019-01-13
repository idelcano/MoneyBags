package com.idelcano.moneycontrol.moneycontrol.presentation.presenters

import android.support.v4.app.DialogFragment
import com.idelcano.moneycontrol.moneycontrol.presentation.views.fragments.MenuDialogFragment

class MenuDialogPresenter {
    var view: MenuDialogFragment? = null

    fun initPresenter(
        view: MenuDialogFragment
    ) {
        this.view = view
    }

    fun detachView() {
        view?.fragmentManager?.findFragmentByTag(view?.companion?.TAG_DIALOG)?.let {
            (it as DialogFragment).dismiss()
        }
        view?.onDetach()
        destroyView()
    }

    fun close() {
        detachView()
    }

    fun destroyView() {
        view = null
    }

    interface View {

        fun cancel()
    }
}