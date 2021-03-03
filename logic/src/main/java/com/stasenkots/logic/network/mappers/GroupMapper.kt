package com.stasenkots.logic.network.mappers

import com.stasenkots.logic.entity.Group
import com.stasenkots.logic.network.dto.group.response.GroupResponse
import com.stasenkots.logic.utils.toDate
import java.time.LocalDate
import javax.inject.Inject

class GroupMapper @Inject constructor() {
    fun map(from: GroupResponse?): Group {
        return Group(
            groupId = from?.groupId.orEmpty(),
            semStartDate = from?.date?.toDate() ?: LocalDate.now()
        )
    }
}