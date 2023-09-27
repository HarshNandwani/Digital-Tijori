package com.harshnandwani.digitaltijori

import android.app.Application
import com.harshnandwani.digitaltijori.presentation.util.UpdateCompaniesOnAppStart
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
//Test commit
@HiltAndroidApp
class DigitalTijoriApp : Application() {

    @Inject
    lateinit var updateCompanies: UpdateCompaniesOnAppStart

    override fun onCreate() {
        super.onCreate()

        /*
        * Called everytime app starts as icons and logos are drawable resources
        * and it cannot be hardcoded as they might change
        * */
        updateCompanies.execute()

    }

}
