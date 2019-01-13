package com.idelcano.moneycontrol.moneycontrol.domain.usecase

import com.idelcano.moneycontrol.moneycontrol.data.repositories.MoneyBagRepository
import com.idelcano.moneycontrol.moneycontrol.domain.boundary.Executor
import com.idelcano.moneycontrol.moneycontrol.domain.entity.MoneyBag

class GetMoneyBagsUseCase(
    private val moneyBagRepository: MoneyBagRepository,
    private val executor: Executor
) : UseCase(executor) {

        fun execute(onResult: (List<MoneyBag?>) -> Unit) {
            asyncExecute {
                val moneyBags = moneyBagRepository.getAll()

                uiExecute {
                    onResult(moneyBags)
                }
            }
        }
    }