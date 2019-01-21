package com.idelcano.moneycontrol.moneycontrol.data.database.model

import com.idelcano.moneycontrol.moneycontrol.data.database.MoneyHelperDatabase
import com.idelcano.moneycontrol.moneycontrol.utils.DhisCodeGenerator
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.ForeignKey
import com.raizlabs.android.dbflow.annotation.ForeignKeyAction
import com.raizlabs.android.dbflow.annotation.ForeignKeyReference
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel
import java.util.Date

@Table(database = MoneyHelperDatabase::class)
data class MoneyAmountDB(
    @PrimaryKey var amountUid: String = DhisCodeGenerator.generateCode(),
    @Column var name: String? = null,
    @Column var amount: Long? = null,
    @Column var creationDate: Date = Date(),
    @Column var isPositive: Boolean = false,
    @ForeignKey(
        tableClass = MoneyBagDB::class,
        references = arrayOf<ForeignKeyReference>(
            ForeignKeyReference(columnName = "moneyBagUid", foreignKeyColumnName = "uid")
        ),
        stubbedRelationship = true,
        onDelete = ForeignKeyAction.CASCADE
    )
    var moneyBagUid: String? = null
) : BaseModel()