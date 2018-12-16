package com.idelcano.moneycontrol.moneycontrol.domain.entity

import java.util.*
data class MoneyBag (val uid:String, val name:String, val amount:Long,
                     val dateLimit: Date, val createdDate:Date, val iconUId:String, val priority:Int){

    companion object{
        const val MAXIMUN_RANGE = 5
        const val MINIMUN_RANGE = 0
    }

    init {
        if (!(priority in MINIMUN_RANGE .. MAXIMUN_RANGE)) {
            throw IllegalArgumentException("${priority} is not a valid priority range")
        }
    }
}
