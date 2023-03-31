package com.stripe.aod.sampleapp.listener

import android.util.Log
import com.stripe.aod.sampleapp.Config
import com.stripe.stripeterminal.external.callable.TerminalListener
import com.stripe.stripeterminal.external.models.ConnectionStatus
import com.stripe.stripeterminal.external.models.PaymentStatus
import com.stripe.stripeterminal.external.models.Reader

/**
 * The `TerminalEventListener` implements the [TerminalListener] interface and will
 * forward along any events to other parts of the app that register for updates.
 *
 */
class TerminalEventListener : TerminalListener {

    override fun onUnexpectedReaderDisconnect(reader: Reader) {
        Log.i(Config.TAG, "Reader serial number: ${reader.serialNumber}")
    }

    override fun onConnectionStatusChange(status: ConnectionStatus) {
        Log.i(Config.TAG, "onConnectionStatusChange: $status")
    }

    override fun onPaymentStatusChange(status: PaymentStatus) {
        Log.i(Config.TAG, "onPaymentStatusChange: $status")
    }
}
