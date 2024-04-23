package com.example.final_project_samsung.domain.use_case.forEvent

import com.example.final_project_samsung.app.domain.model.Event
import com.example.final_project_samsung.app.domain.use_case.forEvent.GetEventById
import com.example.final_project_samsung.data.repository.FakeEventRepo
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class GetEventByIdTest {

    private lateinit var fakeEventRepo: FakeEventRepo

    private lateinit var getEventsById: GetEventById

    private val fakeEvents = mutableListOf<Event>()

    @Before
    fun setUp() {
        fakeEventRepo = FakeEventRepo()
        getEventsById = GetEventById(fakeEventRepo)

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
    fun getEventByIdTest() = runBlocking {
        (1..fakeEvents.size).forEach { index ->
            assert(getEventsById(index) == fakeEvents.find { it.eventId == index })
        }
    }
}