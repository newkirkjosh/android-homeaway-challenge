package com.newkirkj.seattlesearch.networking.foursquare.models

/**
 * Created by Joshua Newkirk on 5/21/2019.
 *
 */
object FoursquareUserlessAuth {

    // Making the assumption that this repository, under normal circumstances would be private
    private const val clientId: String = "MUNBQ1ZTMRGRDPY5VQSIR3T1BPUXYHCFC4HMHH4ZYB2JGPRE"
    private const val clientSecret: String = "AQVN2AHZQJJQJW1FJXDW4402KR1DS2BOBCYHWFGRSWCY5CQC"
    private const val appDate: String = "20190520"

    fun authMap(): Map<String, String> {
        return mapOf(
            "client_id" to clientId,
            "client_secret" to clientSecret,
            "v" to appDate
        )
    }
}