package com.istudio.core_logger.data.implementaion

import com.istudio.core_logger.ApplicationLoggerConfig.Companion.isLoggerEnabled
import com.istudio.core_logger.domain.LoggerFeature
import timber.log.Timber

class LoggerFeatureImpl() : LoggerFeature {
    override fun d(featureName: String?, msg: String?) {
        if (isLoggerEnabled && featureName != null && msg != null) {
            Timber.tag(featureName).d(msg)
        }
    }

    override fun e(featureName: String?, msg: String?) {
        if (isLoggerEnabled && featureName != null && msg != null) {
            Timber.tag(featureName).e(msg)
        }
    }

    override fun w(featureName: String?, msg: String?) {
        if (isLoggerEnabled && featureName != null && msg != null) {
            Timber.tag(featureName).w(msg)
        }
    }

    override fun v(featureName: String?, msg: String?) {
        if (isLoggerEnabled && featureName != null && msg != null) {
            Timber.tag(featureName).v(msg)
        }
    }

    override fun i(featureName: String?, msg: String?) {
        if (isLoggerEnabled && featureName != null && msg != null) {
            Timber.tag(featureName).i(msg)
        }
    }
}
