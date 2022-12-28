package com.istudio.core_logger.domain

interface LoggerFeature {
    fun d(featureName: String?, msg: String?)
    fun e(featureName: String?, msg: String?)
    fun w(featureName: String?, msg: String?)
    fun v(featureName: String?, msg: String?)
    fun i(featureName: String?, msg: String?)
}
