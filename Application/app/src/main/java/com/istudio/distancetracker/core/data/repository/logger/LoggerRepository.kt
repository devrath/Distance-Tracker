package com.istudio.distancetracker.core.data.repository.logger

import com.istudio.distancetracker.core.domain.features.logger.LoggerFeature

open class LoggerRepository(
    private val loggerFeature: LoggerFeature
) {
    open fun d(featureName: String?, msg: String?) { loggerFeature.d(featureName, msg) }
    open fun e(featureName: String?, msg: String?) { loggerFeature.e(featureName, msg) }
    open fun w(featureName: String?, msg: String?) { loggerFeature.w(featureName, msg) }
    open fun v(featureName: String?, msg: String?) { loggerFeature.v(featureName, msg) }
    open fun i(featureName: String?, msg: String?) { loggerFeature.i(featureName, msg) }
}
