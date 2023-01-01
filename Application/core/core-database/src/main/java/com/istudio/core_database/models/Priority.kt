package com.istudio.feat_user_journey.data.models

import androidx.compose.ui.graphics.Color
import com.istudio.feat_user_journey.ui.theme.HighPriorityColor
import com.istudio.feat_user_journey.ui.theme.LowPriorityColor
import com.istudio.feat_user_journey.ui.theme.MediumPriorityColor
import com.istudio.feat_user_journey.ui.theme.NonePriorityColor

enum class Priority(val color : Color) {
    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(NonePriorityColor)
}