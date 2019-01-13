package com.idelcano.moneycontrol.moneycontrol.presentation.views.fragments

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.idelcano.moneycontrol.moneycontrol.R
import com.idelcano.moneycontrol.moneycontrol.data.repositories.DayCounterRepository
import com.idelcano.moneycontrol.moneycontrol.domain.usecase.SaveDayCounterUseCase
import com.idelcano.moneycontrol.moneycontrol.presentation.executers.CoroutinesExecutor
import com.idelcano.moneycontrol.moneycontrol.presentation.presenters.DayCounterCreatorDialogPresenter
import kotlinx.android.synthetic.main.create_day_counter_dialog_layout.view.cancel_day_counter_dialog
import kotlinx.android.synthetic.main.create_day_counter_dialog_layout.view.edit_day_counter_name
import kotlinx.android.synthetic.main.create_day_counter_dialog_layout.view.save_day_counter
import kotlinx.android.synthetic.main.create_money_amount_dialog_layout.edit_name

class DayCounterCreatorDialogFragment() : BaseFragment(), DayCounterCreatorDialogPresenter.View {
    lateinit var presenter: DayCounterCreatorDialogPresenter

    companion object {
        const val TAG_DIALOG: String = "DayCounterCreator"
    }

    var companion = Companion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.WideDialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.create_day_counter_dialog_layout, container, false)
        presenter = DayCounterCreatorDialogPresenter()
        val coroutinesExecutor = CoroutinesExecutor()
        presenter.initPresenter(this, SaveDayCounterUseCase(DayCounterRepository(), coroutinesExecutor))

        view.save_day_counter.setOnClickListener { _ ->
            saveDayCounter()
        }

        view.cancel_day_counter_dialog.setOnClickListener { _ ->
            cancel()
        }

        setCancelable(false)

        view.edit_day_counter_name.setOnClickListener({ _ ->
            view.edit_day_counter_name.setError(null)
        })

        return view
    }

    override fun onDestroy() {
        presenter.destroyView()
        super.onDestroy()
    }

    override fun showNameError() {
        edit_name.setError(getString(R.string.name_error))
    }

    override fun cancel() {
        presenter.close()
    }

    override fun saveDayCounter() {
        presenter.saveDayCounter()
    }
}
