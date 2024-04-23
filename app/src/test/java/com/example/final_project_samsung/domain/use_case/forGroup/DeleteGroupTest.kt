package com.example.final_project_samsung.domain.use_case.forGroup

import com.example.final_project_samsung.app.domain.model.Group
import com.example.final_project_samsung.app.domain.use_case.forGroup.DeleteGroup
import com.example.final_project_samsung.app.domain.use_case.forGroup.GetGroups
import com.example.final_project_samsung.data.repository.FakeGroupRepo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteGroupTest {

    private lateinit var fakeGroupRepo: FakeGroupRepo

    private lateinit var deleteGroup: DeleteGroup

    private lateinit var getGroups: GetGroups

    private val fakeGroups = mutableListOf<Group>()

    @Before
    fun setUp() {
        fakeGroupRepo = FakeGroupRepo()
        deleteGroup = DeleteGroup(fakeGroupRepo)

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
    fun deleteGroupTest() = runBlocking {
        val group = fakeGroups[1]
        deleteGroup(group)
        fakeGroups.remove(group)
        assert(fakeGroups.size == getGroups().first().size)

    }
}