package com.tc.midtermproject.data.repository.service

import com.tc.midtermproject.data.model.sat.SatItemModel
import com.tc.midtermproject.data.model.schools.SchoolItemModel

interface Repository {
    suspend fun getSchools(): List<SchoolItemModel>

    suspend fun getSat(): List<SatItemModel>
}