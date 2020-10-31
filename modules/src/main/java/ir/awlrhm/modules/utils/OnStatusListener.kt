package ir.awlrhm.modules.utils

import ir.awlrhm.modules.enums.Status

interface OnStatusListener {
    fun onStatus(status: Status)
}