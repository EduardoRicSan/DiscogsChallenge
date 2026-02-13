package com.tech.design_system.common.viewmodel

import androidx.lifecycle.ViewModel
import coil3.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoilViewModel @Inject constructor(
    val imageLoader: ImageLoader
) : ViewModel()
