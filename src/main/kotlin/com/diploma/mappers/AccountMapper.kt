package com.diploma.mappers

import com.diploma.dto.Account
import com.diploma.dto.AddAccount
import com.diploma.dto.User
import com.diploma.entity.AccountEntity
import org.springframework.stereotype.Service

@Service
class AccountMapper {
    fun toResponse(user: AccountEntity): Account {
        return Account(
            id = user.id,
            lastName = user.lastName,
            firstName = user.firstName,
            middleName = user.middleName,
            image = user.image,
            address = user.address,
            birthday = user.birthday
        )
    }

    fun toEntity(account: AddAccount): AccountEntity {
        return AccountEntity(
            lastName = account.lastName,
            firstName = account.firstName,
            middleName = account.middleName,
            image = account.image,
            address = account.address,
            birthday = account.birthday
        )
    }
}