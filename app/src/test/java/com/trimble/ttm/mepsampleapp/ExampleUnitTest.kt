package com.trimble.ttm.mepsampleapp

import com.trimble.ttm.backbone.api.BackboneKeys
import com.trimble.ttm.backbone.api.RxBackbone
import com.trimble.ttm.mepsampleapp.viewmodels.MainActivityViewModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

class MainActivityViewModelUnitTest {
    @Test(expected = Exception::class)
    fun `getGpsLocation should throw exception if no result`() {

        val backbone: RxBackbone = mockk()
        every { backbone.fetch(BackboneKeys.GPS_DEGREES_KEY) } returns null

        val mainActivityViewModel = MainActivityViewModel()
        mainActivityViewModel.getGpsLocation(backbone)
    }
}