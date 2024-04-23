package com.example.final_project_samsung.domain.use_case.forGroup

import com.example.final_project_samsung.app.domain.model.Group
import com.example.final_project_samsung.app.domain.use_case.forGroup.GetGroups
import com.example.final_project_samsung.data.repository.FakeGroupRepo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetGroupsTest {

    private lateinit var fakeGroupRepo: FakeGroupRepo

    private lateinit var getGroups: GetGroups

    private val fakeGroups = mutableListOf<Group>()

    @Before
    fun setUp() {
        fakeGroupRepo = FakeGroupRepo()
        getGroups = GetGroups(fakeGroupRepo)
        ('a'..'z').forEachIndexed { index, c ->
            fakeGroups.add(
                Group(
                    groupName = c.toString(),
                    groupTags = listOf(c.toString()),
                    id = index,
                )
            )
        }
        runBlocking {
            fakeGroups.forEach { fakeGroupRepo.insertGroup(it) }
        }
    }

    @Test
    fun getGroupsTest(): Unit = runBlocking {
        assert(fakeGroups == getGroups().first())
    }
}
