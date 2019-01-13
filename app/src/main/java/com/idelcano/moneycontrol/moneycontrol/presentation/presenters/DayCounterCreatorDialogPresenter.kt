package com.idelcano.moneycontrol.moneycontrol.presentation.presenters

import android.support.v4.app.DialogFragment
import com.idelcano.moneycontrol.moneycontrol.domain.entity.DayCounter
import com.idelcano.moneycontrol.moneycontrol.domain.usecase.SaveDayCounterUseCase
import com.idelcano.moneycontrol.moneycontrol.presentation.views.fragments.DayCounterCreatorDialogFragment
import kotlinx.android.synthetic.main.create_day_counter_dialog_layout.edit_day_counter_name
import kotlinx.android.synthetic.main.create_day_counter_dialog_layout.priority_day_counter_seek_bar
import java.util.Date

class DayCounterCreatorDialogPresenter {
    lateinit var saveDayCounterUseCase: SaveDayCounterUseCase
    var view: DayCounterCreatorDialogFragment? = null

    fun initPresenter(view: DayCounterCreatorDialogFragment, saveDayCounterUseCase: SaveDayCounterUseCase) {
        this.view = view
        this.saveDayCounterUseCase = saveDayCounterUseCase
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

    fun saveDayCounter() {
        val name: String = view!!.edit_day_counter_name.text.toString()
        if (name.length == 0) {
            view!!.showNameError()
            return
        }
        val priority: Int = view!!.priority_day_counter_seek_bar.progress + 1
        saveDayCounterUseCase.execute(DayCounter(name = name, createdDate = Date(), priority = priority, iconPath = ""))
        close()
    }

    fun destroyView() {
        view = null
    }

    interface View {
        fun showNameError()

        fun saveDayCounter()

        fun cancel()
    }
}