package com.example.final_project_samsung.domain.use_case.forEvent

import com.example.final_project_samsung.app.domain.model.Event
import com.example.final_project_samsung.app.domain.use_case.forEvent.DeleteEvent
import com.example.final_project_samsung.app.domain.use_case.forEvent.GetEvents
import com.example.final_project_samsung.data.repository.FakeEventRepo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class DeleteEventTest {

    private lateinit var fakeEventRepo: FakeEventRepo

    private lateinit var deleteEvent: DeleteEvent

    private lateinit var getEvents: GetEvents

    private val fakeEvents = mutableListOf<Event>()

    @Before
    fun setUp() {
        fakeEventRepo = FakeEventRepo()
        deleteEvent = DeleteEvent(fakeEventRepo)

        getEvents = GetEvents(fakeEventRepo)

        ('a'..'z').forEachIndexed { index, c ->
            fakeEvents.add(
                Event(
                    eventName = c.toString(),
                    startTime = LocalDateTime.parse("2024-04-23T17:15:24.050238")
                        .plusHours(index.toLong()),
                    endTime = LocalDateTime.parse("2024-04-23T17:15:24.050238")
                        .plusHours(index.toLong() + 1),
                    eventId = index + 1,
                    eventTags = listOf(c.toString()),
                    groupId = index,
                )
            )
        }
        runBlocking {
            fakeEvents.forEach { fakeEventRepo.insertEvent(it) }
        }
    }

    @Test
    fun deleteEventTest() = runBlocking {
        val event = fakeEvents[1]
        deleteEvent(event)
        fakeEvents.remove(event)
        assert(fakeEvents.size == getEvents().first().size)

    }
}