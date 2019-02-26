package com.trimble.ttm.mepsampleapp

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.trimble.ttm.backbone.api.Backbone
import com.trimble.ttm.backbone.api.BackboneKeys
import com.trimble.ttm.mepsampleapp.viewmodels.MainActivityViewModel
import org.junit.Test


class MainActivityViewModelUnitTest {
    @Test(expected = Exception::class)
    fun `getGpsLocation should throw exception if no result`() {

        val backbone: Backbone = mock()

        val mainActivityViewModel = MainActivityViewModel()
        whenever(backbone.fetch(BackboneKeys.GPS_DEGREES_KEY))
            .thenReturn(null)

        mainActivityViewModel.getGpsLocation(backbone)
    }
}