package com.sunagalab.smallutils

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter

object ViewUtils {

    private fun View.updateViewConstraintLayoutParamms(func: ConstraintLayout.LayoutParams.()->Unit) {
        val lp = layoutParams as ConstraintLayout.LayoutParams
        func(lp)
        layoutParams = lp
    }

    @JvmStatic
    @BindingAdapter("layout_constraintWidth_percent")
    fun View.setLayoutConstraintWidthPercent(percent: Float) = updateViewConstraintLayoutParamms {
        this.matchConstraintPercentWidth = percent
    }

    @JvmStatic
    @BindingAdapter("layout_constraintHeight_percent")
    fun View.setLayoutConstraintHeightPercent(percent: Float) = updateViewConstraintLayoutParamms {
        this.matchConstraintPercentHeight = percent
    }

    @JvmStatic
    @BindingAdapter("layout_constraintDimensionRatio")
    fun View.setLayoutConstrainDimensionRatio(ratio: String) = updateViewConstraintLayoutParamms {
        this.dimensionRatio = ratio
    }

    @JvmStatic
    @BindingAdapter("layout_constraintWidth_default")
    fun View.setLayoutConstraintWidthDefault(value: Int) = updateViewConstraintLayoutParamms {
        this.matchConstraintDefaultWidth = value
    }

    @JvmStatic
    @BindingAdapter("layout_constraintHeight_default")
    fun View.setLayoutConstraintHeightDefault(value: Int) = updateViewConstraintLayoutParamms {
        this.matchConstraintDefaultHeight = value
    }

}
