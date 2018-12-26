package com.idelcano.moneycontrol.moneycontrol.fragments

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.idelcano.moneycontrol.moneycontrol.R

class MoneyBagDialogFragment : DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Do all the stuff to initialize your custom view

        return inflater.inflate(R.layout.create_money_bag_dialog_layout, container, false)
    }
}
