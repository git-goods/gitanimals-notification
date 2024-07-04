package org.gitanimals.notification.app

import org.gitanimals.notification.app.event.NewUserCreated
import org.gitanimals.notification.domain.Notification
import org.rooftop.netx.api.SagaStartEvent
import org.rooftop.netx.api.SagaStartListener
import org.rooftop.netx.api.SuccessWith
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class SlackNotificationHandler(
    @Qualifier("gitAnimalsMarketSlackNotification")
    private val notification: Notification,
) {

    @SagaStartListener(NewUserCreated::class, successWith = SuccessWith.END)
    fun handleNewUserCreatedEvent(sagaStartEvent: SagaStartEvent) {
        val newUserCreated = sagaStartEvent.decodeEvent(NewUserCreated::class)
        notification.notify(
            """
            새로운 유저가 가입했어요!
            userId: ${newUserCreated.userId}
            username: ${newUserCreated.username}
            """.trimIndent()
        )
    }
}
