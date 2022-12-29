package com.istudio.core_ui.customviews.button

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.res.ResourcesCompat
import com.istudio.core_ui.R

class ButtonViewBold : AppCompatButton {

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, style: Int) : super(context, attrs, style) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val myTypeface = ResourcesCompat.getFont(context, R.font.roboto_bold)
            this.typeface = myTypeface
        }
    }
}