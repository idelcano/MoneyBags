package com.idelcano.moneycontrol.moneycontrol.data.database.model

import com.idelcano.moneycontrol.moneycontrol.data.database.MoneyHelperDatabase
import com.idelcano.moneycontrol.moneycontrol.utils.DhisCodeGenerator
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import java.util.*

@Table(database = MoneyHelperDatabase::class)
data class MoneyBagDB(@PrimaryKey var uid: String = DhisCodeGenerator.generateCode(),
                      @Column var name: String? = null,
                      @Column var amount: Long? = null,
                      @Column var dateLimit: Date? = null,
                      @Column var createdDate: Date? = null,
                      @Column var iconUId: String? = null,
                      @Column var priority: Int? = null)