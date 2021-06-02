package com.diploma.telegram

import com.diploma.entity.JournalInfoEntity
import com.diploma.exception.StudentIdNotFoundedException
import com.diploma.repository.JournalRepository
import com.diploma.repository.StudentRepository
import com.elbekD.bot.Bot
import com.elbekD.bot.feature.chain.chain
import com.elbekD.bot.feature.chain.jumpTo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat

@Service
class TelegramService {
    @Value(value = "\${telegram.bot.token}")
    private lateinit var token: String

    @Value(value = "\${telegram.bot.name}")
    private lateinit var name: String

    private lateinit var bot: Bot

    @Autowired
    private lateinit var studentRepository: StudentRepository

    @Autowired
    private lateinit var journalRepository: JournalRepository

    @EventListener(ApplicationReadyEvent::class)
    fun start() {
        this.bot = Bot.createPolling(name, token)
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


    fun sendMessage(id: Long, info: JournalInfoEntity) {
        this.studentRepository.findById(id).map {
            info.journalId?.let { it1 ->
                journalRepository.findById(it1).map { j ->
                    it.telegramChatId?.let { chat_id ->
                        bot.sendMessage(
                            chat_id,
                            "${j.subject?.name} ${SimpleDateFormat("yyyy-MM-dd").format(info.dateMarks)}\nОтметка: ${info.mark} (${info.markType})\nПреподаватель: ${j.account?.lastName} ${j.account?.firstName} ${j.account?.middleName}\nСообщение от преподавателя: ${info.description}"
                        )
                    }
                }
            }
        }.orElseThrow {
            StudentIdNotFoundedException("Учащийся не найден")
        }
    }
}
