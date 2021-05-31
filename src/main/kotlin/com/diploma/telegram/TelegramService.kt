package com.diploma.telegram

import com.diploma.repository.StudentRepository
import com.elbekD.bot.Bot
import com.elbekD.bot.feature.chain.chain
import com.elbekD.bot.feature.chain.jumpTo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class TelegramService {
    @Value(value = "\${telegram.bot.token}")
    private lateinit var token: String

    @Value(value = "\${telegram.bot.name}")
    private lateinit var name: String

    private lateinit var bot: Bot

    @Autowired
    private lateinit var studentRepository: StudentRepository

    @EventListener(ApplicationReadyEvent::class)
    fun start() {
        val bot = Bot.createPolling(name, token)
        val contactMessage: Bot
        val chat_id: Long? = null
        val phone: String? = null
        bot.chain("/start") { msg ->
            bot.sendMessage(
                msg.chat.id,
                "Привет, ${msg.chat.username}! Пришли мне свой идентификатор студента"
            )
        }.then(label = "phone_number", action = {
            it.text.takeIf { !it.isNullOrEmpty() }?.apply {
                studentRepository.findByIdentifier(this).takeIf { it != null }?.apply {
                    this.telegramChatId = it.chat.id
                    studentRepository.save(this)
                    bot.sendMessage(
                        it.chat.id,
                        "Приветствую, ${this.account?.lastName} ${this.account?.firstName} ${this.account?.middleName}.\nТеперь ты будешь получать уведомления об отметках"
                    )
                } ?: run {
                    bot.sendMessage(it.chat.id, "Учащихся с идентификатором ${it.text} не нейден")
                    bot.jumpTo("phone_number", it)
                }
            } ?: run {
                bot.jumpTo("phone_number", it)
            }
        }).build()
        bot.start()
    }


    fun sendMessage(msg: TelegramMessage) {

    }
}

enum class TelegramMessage {
    START, MARK
}
