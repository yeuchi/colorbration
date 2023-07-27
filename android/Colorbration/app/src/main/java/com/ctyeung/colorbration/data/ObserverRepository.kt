package com.ctyeung.colorbration.data

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ObserverRepository @Inject constructor(
    @ApplicationContext val context: Context
) {

}