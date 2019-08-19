package com.groep4.mindfulness

import android.support.test.runner.AndroidJUnit4
import com.groep4.mindfulness.utils.LoginValidation
import org.junit.Assert

import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun isGeldigMailadres_MailadresBevatGeenAt_GeeftFalseTerug() {
        var gelukt = LoginValidation.isValidEmail("examplehotmail.com", false)
        Assert.assertFalse(gelukt)
    }

    @Test
    fun isGeldigMailadres_MailadresBevatGeenTekstNaAt_GeeftFalseTerug() {
        var gelukt = LoginValidation.isValidEmail("example@", false)
        Assert.assertFalse(gelukt)
    }

    @Test
    fun isGeldigMailadres_MailadresGeldig_GeeftTrueTerug() {
        var gelukt = LoginValidation.isValidEmail("example@hotmail.com", false)
        Assert.assertTrue(gelukt)
    }

    @Test
    fun isGeldidWachtwoord_WachtwoordZonderHoofdletter_GeeftFalseTerug() {
        var gelukt = LoginValidation.isValidPassword("example123", false)
        Assert.assertFalse(gelukt)
    }

    @Test
    fun isGeldigWachtwoord_WachtwoordZonderCijfers_GeeftFalseTerug() {
        var gelukt = LoginValidation.isValidPassword("Exampleeee", false)
        Assert.assertFalse(gelukt)
    }

    @Test
    fun isGeldigWachtwoord_WachtwoordKorterDanAchtTekens() {
        var gelukt = LoginValidation.isValidPassword("Ex1", false)
        Assert.assertFalse(gelukt)
    }

    @Test
    fun isGeldigWachtwoord_WachtwoordGeldig_GeeftTrueTerug() {
        var gelukt = LoginValidation.isValidPassword("Example123", false)
        Assert.assertTrue(gelukt)
    }
}
