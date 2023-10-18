package com.tc.midtermproject.data.repository.service

import com.tc.midtermproject.data.model.sat.SatItemModel
import com.tc.midtermproject.data.model.schools.SchoolItemModel
import com.tc.midtermproject.data.remote.ApiEndpoint
import javax.inject.Inject


class RepositoryImpl @Inject constructor(
    private val service: ApiEndpoint
): Repository {
    override suspend fun getSchools(): List<SchoolItemModel> {
        return service.getSchools()
    }

    override suspend fun getSat(): List<SatItemModel> {
        return service.getSat()
    }
}