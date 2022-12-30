package com.istudio.core_common.extensions

// Forcing compiler to treat as expression
val <T> T.exhaustive: T
    get() = this
