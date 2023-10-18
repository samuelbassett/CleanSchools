package com.tc.midtermproject.data.remote

import com.tc.midtermproject.data.model.sat.SatItemModel
import com.tc.midtermproject.data.model.schools.SchoolItemModel
import retrofit2.http.GET

interface ApiEndpoint {
    @GET(ApiDetails.schoolsEndpoint)
    suspend fun getSchools(): List<SchoolItemModel>

    @GET(ApiDetails.satEndpoint)
    suspend fun getSat(): List<SatItemModel>
}