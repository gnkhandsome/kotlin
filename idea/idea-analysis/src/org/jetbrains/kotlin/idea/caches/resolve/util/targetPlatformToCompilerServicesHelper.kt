/*
 * Copyright 2010-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.caches.resolve.util

import org.jetbrains.kotlin.analyzer.common.CommonPlatform
import org.jetbrains.kotlin.analyzer.common.CommonPlatformCompilerServices
import org.jetbrains.kotlin.js.resolve.JsPlatform
import org.jetbrains.kotlin.js.resolve.JsPlatformCompilerServices
import org.jetbrains.kotlin.resolve.PlatformDependentCompilerServices
import org.jetbrains.kotlin.resolve.TargetPlatform
import org.jetbrains.kotlin.resolve.jvm.platform.JvmPlatform
import org.jetbrains.kotlin.resolve.jvm.platform.JvmPlatformCompilerServices
import org.jetbrains.kotlin.resolve.konan.platform.KonanPlatform
import org.jetbrains.kotlin.resolve.konan.platform.NativePlatformCompilerServices
import java.lang.IllegalStateException

val TargetPlatform.compilerServices: PlatformDependentCompilerServices
    get() = when (this) {
        JvmPlatform -> JvmPlatformCompilerServices
        JsPlatform -> JsPlatformCompilerServices
        KonanPlatform -> NativePlatformCompilerServices
        CommonPlatform -> CommonPlatformCompilerServices
        else -> throw IllegalStateException("Unknown platform $this")
    }