package com.idelcano.moneycontrol.moneycontrol.presentation.views.fragments

import android.support.v4.app.DialogFragment

open class BaseFragment : DialogFragment() {

    private lateinit var dialogCreator: (func: () -> Unit, message: Int) -> Unit
    private var mListener: Listener? = null

    fun setListener(listener: Listener?) {
        mListener = listener
    }

    interface Listener {
        fun onDetached(fragment: BaseFragment)
    }

    override fun onDetach() {
        super.onDetach()

        if (mListener != null) {
            mListener!!.onDetached(this)
        }
    }

    fun addDialogCreatorFun(dialogCreator: (func: () -> Unit, message: Int) -> Unit) {
        this.dialogCreator = dialogCreator
    }

    fun createDialog(func: () -> Unit, messageId: Int) {
        dialogCreator(func, messageId)
    }
}