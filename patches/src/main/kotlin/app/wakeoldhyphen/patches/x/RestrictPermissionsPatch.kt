package app.wakeoldhyphen.patches.x

import app.morphe.patcher.patch.resourcePatch
import app.wakeoldhyphen.patches.shared.Constants.COMPATIBILITY_X
import org.w3c.dom.Element

private const val ANDROID_NAMESPACE = "http://schemas.android.com/apk/res/android"

private val ALLOWED_PERMISSIONS = setOf(
    "android.permission.INTERNET",
    "android.permission.FOREGROUND_SERVICE",
    "android.permission.FOREGROUND_SERVICE_DATA_SYNC",
    "android.permission.ACCESS_NETWORK_STATE",
    "android.permission.ACCESS_WIFI_STATE",
)

@Suppress("unused")
val restrictPermissionsPatch = resourcePatch(
    name = "Restrict permissions",
    description = "Removes requested permissions that are not required by the allowlist.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_X)

    execute {
        document("AndroidManifest.xml").use { document ->
            val elements = document.getElementsByTagName("*").toList()

            elements
                .filterIsInstance<Element>()
                .filter { element ->
                    element.tagName == "uses-permission" ||
                        element.tagName.startsWith("uses-permission-")
                }
                .forEach { permissionElement ->
                    val permissionName =
                        permissionElement.getAttributeNS(
                            ANDROID_NAMESPACE,
                            "name",
                        ).ifEmpty {
                            permissionElement.getAttribute("android:name")
                        }

                    if (permissionName !in ALLOWED_PERMISSIONS) {
                        permissionElement.parentNode.removeChild(permissionElement)
                    }
                }
        }
    }
}

