package com.istudio.distancetracker.main

import com.demo.core_permission.domain.PermissionFeature
import com.istudio.core_common.base.BaseViewModel
import com.istudio.core_logger.domain.LoggerFeature
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainVm @Inject constructor(
    private var log: LoggerFeature
) : BaseViewModel()  {

}