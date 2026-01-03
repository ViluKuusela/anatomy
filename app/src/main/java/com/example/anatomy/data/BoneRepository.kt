package com.example.anatomy.data

import com.example.anatomy.R
import com.example.anatomy.ui.language.Language

/**
 * A repository object that provides access to the anatomical bone data.
 * It acts as a singleton data source for the quiz questions.
 */
object BoneRepository {

    // A list of bones found in the hand.
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

    // A list of bones found in the foot.
    val footBones = listOf(
        Bone(
            id = "talus",
            names = mapOf(
                Language.LATIN to "Talus",
                Language.ENGLISH to "Talus",
                Language.FINNISH to "Telaluu"
            ),
            highlightDrawableRes = R.drawable.foot_bones_talus
        ),
        Bone(
            id = "calcaneus",
            names = mapOf(
                Language.LATIN to "Calcaneus",
                Language.ENGLISH to "Calcaneus",
                Language.FINNISH to "Kantaluu"
            ),
            highlightDrawableRes = R.drawable.foot_bones_calcaneus
        ),
        Bone(
            id = "navicular",
            names = mapOf(
                Language.LATIN to "Os naviculare",
                Language.ENGLISH to "Navicular",
                Language.FINNISH to "Veneluu"
            ),
            highlightDrawableRes = R.drawable.foot_bones_naviculare
        ),
        Bone(
            id = "cuboideum",
            names = mapOf(
                Language.LATIN to "Os cuboideum",
                Language.ENGLISH to "Cuboid",
                Language.FINNISH to "Kuutioluu"
            ),
            highlightDrawableRes = R.drawable.foot_bones_cuboideum
        )
    )

    /**
     * Returns a list of bones based on the specified anatomy area.
     *
     * @param anatomyArea The name of the anatomy area (e.g., "Hand", "Foot").
     * @return A list of [Bone] objects for the given area, or an empty list if the area is not found.
     */
    fun getBones(anatomyArea: String): List<Bone> {
        return when (anatomyArea) {
            "Hand" -> handBones
            "Foot" -> footBones
            else -> emptyList() // Return an empty list for other areas for now.
        }
    }
}
