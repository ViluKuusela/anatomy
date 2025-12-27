package com.example.anatomy.data

import com.example.anatomy.R
import com.example.anatomy.ui.language.Language

object BoneRepository {

    val handBones = listOf(
        Bone(
            id = "ulna",
            names = mapOf(
                Language.LATIN to "Ulna",
                Language.ENGLISH to "Ulna",
                Language.FINNISH to "Kyynärluu"
            ),
            highlightDrawableRes = R.drawable.hand_bones_ulna
        ),
        Bone(
            id = "radius",
            names = mapOf(
                Language.LATIN to "Radius",
                Language.ENGLISH to "Radius",
                Language.FINNISH to "Värttinäluu"
            ),
            highlightDrawableRes = R.drawable.hand_bones_radius
        ),
        Bone(
            id = "scaphoideum",
            names = mapOf(
                Language.LATIN to "Os scaphoideum",
                Language.ENGLISH to "Scaphoid",
                Language.FINNISH to "Veneluu"
            ),
            highlightDrawableRes = R.drawable.hand_bones_scaphoideum
        ),
        Bone(
            id = "lunatum",
            names = mapOf(
                Language.LATIN to "Os lunatum",
                Language.ENGLISH to "Lunate",
                Language.FINNISH to "Puolikuuluu"
            ),
            highlightDrawableRes = R.drawable.hand_bones_lunatum
        )
    )
}
