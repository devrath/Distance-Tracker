package com.istudio.core_database.models

import androidx.compose.ui.graphics.Color
import com.istudio.core_ui.composeUi.theme.HighPriorityColor
import com.istudio.core_ui.composeUi.theme.LowPriorityColor
import com.istudio.core_ui.composeUi.theme.MediumPriorityColor
import com.istudio.core_ui.composeUi.theme.NonePriorityColor

enum class Priority(val color : Color) {
    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(NonePriorityColor)
}