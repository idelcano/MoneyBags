package com.idelcano.moneycontrol.moneycontrol.domain.usecase

import com.idelcano.moneycontrol.moneycontrol.data.repositories.DayCounterRepository
import com.idelcano.moneycontrol.moneycontrol.domain.boundary.Executor
import com.idelcano.moneycontrol.moneycontrol.domain.entity.DayCounter

class DeleteDayCounterUseCase(
    private val dayCounterUseCase: DayCounterRepository,
    private val executor: Executor
) : UseCase(executor) {

        fun execute(dayCounter: DayCounter) {
            asyncExecute {
                dayCounterUseCase.delete(dayCounter)

                uiExecute { dayCounter }
            }
        }
    }