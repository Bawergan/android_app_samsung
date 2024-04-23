package com.example.final_project_samsung.domain.use_case.forGroup

import com.example.final_project_samsung.app.domain.model.Group
import com.example.final_project_samsung.app.domain.use_case.forGroup.GetGroupById
import com.example.final_project_samsung.data.repository.FakeGroupRepo
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetGroupByIdTest {

    private lateinit var fakeGroupRepo: FakeGroupRepo

    private lateinit var getGroupsById: GetGroupById

    private val fakeGroups = mutableListOf<Group>()

    @Before
    fun setUp() {
        fakeGroupRepo = FakeGroupRepo()
        getGroupsById = GetGroupById(fakeGroupRepo)

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
    fun getGroupByIdTest() = runBlocking {
        (1..fakeGroups.size).forEach { index ->
            assert(getGroupsById(index) == fakeGroups.find { it.id == index })
        }
    }
}