package app.wakeoldhyphen.patches.shared

import app.morphe.patcher.patch.ApkFileType
import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility

object Constants {
    val COMPATIBILITY_X = Compatibility(
        name = "X",
        packageName = "com.twitter.android",
        apkFileType = ApkFileType.APK,
        appIconColor = 0x000000,
        targets = listOf(
            AppTarget(
                version = "12.2.1-release.0"
            )
        )
    )
}
