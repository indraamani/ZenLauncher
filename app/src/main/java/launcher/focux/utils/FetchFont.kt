package launcher.focux.utils

import launcher.focux.R
import java.lang.reflect.Field
import java.util.Locale

data class FontResource(
    val name: String,
    val resource: Int,
)


fun fetchAllFont() : List<FontResource> {
    val fontList = mutableListOf<FontResource>()

    try {
        val fontField: Array<Field> = R.font::class.java.fields

        for (field in fontField) {
            try {
                val fontName = field.name.replace("_", " ").replaceFirstChar { if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()) else it.toString() }
                val resId = field.getInt(null)

                fontList.add(FontResource(fontName, resId))
            } catch (e : Exception) {
                e.printStackTrace()
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return fontList.toList()
}