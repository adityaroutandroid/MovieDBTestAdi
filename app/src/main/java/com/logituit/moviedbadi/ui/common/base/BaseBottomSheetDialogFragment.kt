package com.logituit.moviedbadi.ui.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.logituit.moviedbadi.utils.ViewLifecycleDelegateMutable
/**
 * Created by Aditya.
 * Logituit
 * aditya.rout@logituit.com
 */

abstract class BaseBottomSheetDialogFragment<T : ViewBinding> : BottomSheetDialogFragment() {

    protected var binding: T by ViewLifecycleDelegateMutable()

    abstract fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = inflateBinding(inflater, container).also { binding = it; }.root
}