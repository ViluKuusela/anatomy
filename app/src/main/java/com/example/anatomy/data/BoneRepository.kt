package com.example.anatomy.data

import com.example.anatomy.R
import com.example.anatomy.ui.language.Language

/**
 * A repository object that provides access to the anatomical bone data.
 * It acts as a singleton data source for the quiz questions.
 */
object BoneRepository {

    val handBones = listOf(
        Bone(
            id = "ulna",
            names = mapOf(
                Language.LATIN to "Ulna",
                Language.ENGLISH to "Ulna",
                Language.FINNISH to "Kyynärluu"
            ),
            baseDrawableRes = R.drawable.hand_bones_base,
            highlightMaskRes = R.drawable.hand_bones_ulna
        ),
        Bone(
            id = "radius",
            names = mapOf(
                Language.LATIN to "Radius",
                Language.ENGLISH to "Radius",
                Language.FINNISH to "Värttinäluu"
            ),
            baseDrawableRes = R.drawable.hand_bones_base,
            highlightMaskRes = R.drawable.hand_bones_radius
        ),
        Bone(
            id = "scaphoideum",
            names = mapOf(
                Language.LATIN to "Os scaphoideum",
                Language.ENGLISH to "Scaphoid",
                Language.FINNISH to "Veneluu"
            ),
            baseDrawableRes = R.drawable.hand_bones_base,
            highlightMaskRes = R.drawable.hand_bones_scaphoideum
        ),
        Bone(
            id = "lunatum",
            names = mapOf(
                Language.LATIN to "Os lunatum",
                Language.ENGLISH to "Lunate",
                Language.FINNISH to "Puolikuuluu"
            ),
            baseDrawableRes = R.drawable.hand_bones_base,
            highlightMaskRes = R.drawable.hand_bones_lunatum
        ),
        Bone(
            id = "triquetrum",
            names = mapOf(
                Language.LATIN to "Os triquetrum",
                Language.ENGLISH to "Triquetrum",
                Language.FINNISH to "Kolmioluu"
            ),
            baseDrawableRes = R.drawable.hand_bones_base,
            highlightMaskRes = R.drawable.hand_bones_triquetrum
        ),
        Bone(
            id = "pisiforme",
            names = mapOf(
                Language.LATIN to "Os pisiforme",
                Language.ENGLISH to "Pisiform",
                Language.FINNISH to "Herneluu"
            ),
            baseDrawableRes = R.drawable.hand_bones_base,
            highlightMaskRes = R.drawable.hand_bones_pisiforme
        ),
        Bone(
            id = "trapezium",
            names = mapOf(
                Language.LATIN to "Os trapezium",
                Language.ENGLISH to "Trapezium",
                Language.FINNISH to "Iso monikulmaluu"
            ),
            baseDrawableRes = R.drawable.hand_bones_base,
            highlightMaskRes = R.drawable.hand_bones_trapezium
        ),
        Bone(
            id = "trapezoideum",
            names = mapOf(
                Language.LATIN to "Os trapezoideum",
                Language.ENGLISH to "Trapezoid",
                Language.FINNISH to "Pieni monikulmaluu"
            ),
            baseDrawableRes = R.drawable.hand_bones_base,
            highlightMaskRes = R.drawable.hand_bones_trapezoideum
        ),
        Bone(
            id = "capitatum",
            names = mapOf(
                Language.LATIN to "Os capitatum",
                Language.ENGLISH to "Capitate",
                Language.FINNISH to "Iso ranneluu"
            ),
            baseDrawableRes = R.drawable.hand_bones_base,
            highlightMaskRes = R.drawable.hand_bones_capitatum
        ),
        Bone(
            id = "hamatum",
            names = mapOf(
                Language.LATIN to "Os hamatum",
                Language.ENGLISH to "Hamate",
                Language.FINNISH to "Hakaluu"
            ),
            baseDrawableRes = R.drawable.hand_bones_base,
            highlightMaskRes = R.drawable.hand_bones_hamatum
        ),
        Bone(
            id = "metacarpalia",
            names = mapOf(
                Language.LATIN to "Ossa metacarpi",
                Language.ENGLISH to "Metacarpals",
                Language.FINNISH to "Kämmenluut"
            ),
            baseDrawableRes = R.drawable.hand_bones_base,
            highlightMaskRes = R.drawable.hand_bones_metacarpalia
        ),
        Bone(
            id = "phalages_proximalis",
            names = mapOf(
                Language.LATIN to "Phalanges proximales",
                Language.ENGLISH to "Proximal phalanges",
                Language.FINNISH to "Tyviluut"
            ),
            baseDrawableRes = R.drawable.hand_bones_base,
            highlightMaskRes = R.drawable.hand_bones_phalages_proximalis
        ),
        Bone(
            id = "phalages_mediae",
            names = mapOf(
                Language.LATIN to "Phalanges mediae",
                Language.ENGLISH to "Middle phalanges",
                Language.FINNISH to "Keskiluut"
            ),
            baseDrawableRes = R.drawable.hand_bones_base,
            highlightMaskRes = R.drawable.hand_bones_phalages_mediae
        ),
        Bone(
            id = "phalages_distales",
            names = mapOf(
                Language.LATIN to "Phalanges distales",
                Language.ENGLISH to "Distal phalanges",
                Language.FINNISH to "Kärkiluut"
            ),
            baseDrawableRes = R.drawable.hand_bones_base,
            highlightMaskRes = R.drawable.hand_bones_phalages_distales
        )
    )

    val footBones = listOf(
        Bone(
            id = "talus",
            names = mapOf(
                Language.LATIN to "Talus",
                Language.ENGLISH to "Talus",
                Language.FINNISH to "Telaluu"
            ),
            baseDrawableRes = R.drawable.foot_bones_base,
            highlightMaskRes = R.drawable.foot_bones_talus
        ),
        Bone(
            id = "calcaneus",
            names = mapOf(
                Language.LATIN to "Calcaneus",
                Language.ENGLISH to "Calcaneus",
                Language.FINNISH to "Kantaluu"
            ),
            baseDrawableRes = R.drawable.foot_bones_base,
            highlightMaskRes = R.drawable.foot_bones_calcaneus
        ),
        Bone(
            id = "navicular",
            names = mapOf(
                Language.LATIN to "Os naviculare",
                Language.ENGLISH to "Navicular",
                Language.FINNISH to "Veneluu"
            ),
            baseDrawableRes = R.drawable.foot_bones_base,
            highlightMaskRes = R.drawable.foot_bones_naviculare
        ),
        Bone(
            id = "cuboideum",
            names = mapOf(
                Language.LATIN to "Os cuboideum",
                Language.ENGLISH to "Cuboid",
                Language.FINNISH to "Kuutioluu"
            ),
            baseDrawableRes = R.drawable.foot_bones_base,
            highlightMaskRes = R.drawable.foot_bones_cuboideum
        ),
        Bone(
            id = "cuneiforme_mediale",
            names = mapOf(
                Language.LATIN to "Os cuneiforme mediale",
                Language.ENGLISH to "Medial cuneiform",
                Language.FINNISH to "Sisin vaajaluu"
            ),
            baseDrawableRes = R.drawable.foot_bones_base,
            highlightMaskRes = R.drawable.foot_bones_cuneiforme_mediale
        ),
        Bone(
            id = "cuneiforme_intermedium",
            names = mapOf(
                Language.LATIN to "Os cuneiforme intermedium",
                Language.ENGLISH to "Intermediate cuneiform",
                Language.FINNISH to "Keskimmäinen vaajaluu"
            ),
            baseDrawableRes = R.drawable.foot_bones_base,
            highlightMaskRes = R.drawable.foot_bones_cuneiforme_intermedium
        ),
        Bone(
            id = "cuneiforme_laterale",
            names = mapOf(
                Language.LATIN to "Os cuneiforme laterale",
                Language.ENGLISH to "Lateral cuneiform",
                Language.FINNISH to "Uloin vaajaluu"
            ),
            baseDrawableRes = R.drawable.foot_bones_base,
            highlightMaskRes = R.drawable.foot_bones_cuneiforme_laterale
        ),
        Bone(
            id = "metatarsalia",
            names = mapOf(
                Language.LATIN to "Ossa metatarsi",
                Language.ENGLISH to "Metatarsals",
                Language.FINNISH to "Jalkapöytäluut"
            ),
            baseDrawableRes = R.drawable.foot_bones_base,
            highlightMaskRes = R.drawable.foot_bones_metatarsalia
        ),
        Bone(
            id = "phalages_proximalis",
            names = mapOf(
                Language.LATIN to "Phalanges proximales",
                Language.ENGLISH to "Proximal phalanges",
                Language.FINNISH to "Tyviluut"
            ),
            baseDrawableRes = R.drawable.foot_bones_base,
            highlightMaskRes = R.drawable.foot_bones_phalages_proximalis
        ),
        Bone(
            id = "phalages_mediae",
            names = mapOf(
                Language.LATIN to "Phalanges mediae",
                Language.ENGLISH to "Middle phalanges",
                Language.FINNISH to "Keskiluut"
            ),
            baseDrawableRes = R.drawable.foot_bones_base,
            highlightMaskRes = R.drawable.foot_bones_phalages_mediae
        ),
        Bone(
            id = "phalages_distales",
            names = mapOf(
                Language.LATIN to "Phalanges distales",
                Language.ENGLISH to "Distal phalanges",
                Language.FINNISH to "Kärkiluut"
            ),
            baseDrawableRes = R.drawable.foot_bones_base,
            highlightMaskRes = R.drawable.foot_bones_phalages_distales
        )
    )

    val skullBones = listOf(
        Bone(
            id = "frontale",
            names = mapOf(
                Language.LATIN to "Os frontale",
                Language.ENGLISH to "Frontal bone",
                Language.FINNISH to "Otsaluu"
            ),
            baseDrawableRes = R.drawable.skull_bones_base,
            highlightMaskRes = R.drawable.skull_bones_frontale
        ),
        Bone(
            id = "nasale",
            names = mapOf(
                Language.LATIN to "Os nasale",
                Language.ENGLISH to "Nasal bone",
                Language.FINNISH to "Nenäluu"
            ),
            baseDrawableRes = R.drawable.skull_bones_base,
            highlightMaskRes = R.drawable.skull_bones_nasale
        ),
        Bone(
            id = "lacrimale",
            names = mapOf(
                Language.LATIN to "Os lacrimale",
                Language.ENGLISH to "Lacrimal bone",
                Language.FINNISH to "Kyynelluu"
            ),
            baseDrawableRes = R.drawable.skull_bones_base,
            highlightMaskRes = R.drawable.skull_bones_lacrimale
        ),
        Bone(
            id = "ethmoidale",
            names = mapOf(
                Language.LATIN to "Os ethmoidale",
                Language.ENGLISH to "Ethmoid bone",
                Language.FINNISH to "Seulaluu"
            ),
            baseDrawableRes = R.drawable.skull_bones_base,
            highlightMaskRes = R.drawable.skull_bones_ethmoidale
        ),
        Bone(
            id = "sphenoidale",
            names = mapOf(
                Language.LATIN to "Os sphenoidale",
                Language.ENGLISH to "Sphenoid bone",
                Language.FINNISH to "Kitaluu"
            ),
            baseDrawableRes = R.drawable.skull_bones_base,
            highlightMaskRes = R.drawable.skull_bones_sphenoidale
        ),
        Bone(
            id = "zygomaticum",
            names = mapOf(
                Language.LATIN to "Os zygomaticum",
                Language.ENGLISH to "Zygomatic bone",
                Language.FINNISH to "Poskiluu"
            ),
            baseDrawableRes = R.drawable.skull_bones_base,
            highlightMaskRes = R.drawable.skull_bones_zygomaticum
        ),
        Bone(
            id = "maxilla",
            names = mapOf(
                Language.LATIN to "Maxilla",
                Language.ENGLISH to "Maxilla",
                Language.FINNISH to "Yläleukaluu"
            ),
            baseDrawableRes = R.drawable.skull_bones_base,
            highlightMaskRes = R.drawable.skull_bones_maxilla
        ),
        Bone(
            id = "mandibula",
            names = mapOf(
                Language.LATIN to "Mandibula",
                Language.ENGLISH to "Mandible",
                Language.FINNISH to "Alaleukaluu"
            ),
            baseDrawableRes = R.drawable.skull_bones_base,
            highlightMaskRes = R.drawable.skull_bones_mandibula
        ),
        Bone(
            id = "temporale",
            names = mapOf(
                Language.LATIN to "Os temporale",
                Language.ENGLISH to "Temporal bone",
                Language.FINNISH to "Ohimoluu"
            ),
            baseDrawableRes = R.drawable.skull_bones_base,
            highlightMaskRes = R.drawable.skull_bones_temporale
        ),
        Bone(
            id = "occipitale",
            names = mapOf(
                Language.LATIN to "Os occipitale",
                Language.ENGLISH to "Occipital bone",
                Language.FINNISH to "Niskaluu"
            ),
            baseDrawableRes = R.drawable.skull_bones_base,
            highlightMaskRes = R.drawable.skull_bones_occipitale
        )
    )

    val upperBodyBones = listOf(
        Bone(
            id = "clavicula",
            names = mapOf(
                Language.LATIN to "Clavicula",
                Language.ENGLISH to "Clavicle",
                Language.FINNISH to "Solisluu"
            ),
            baseDrawableRes = R.drawable.upper_bones_base,
            highlightMaskRes = R.drawable.upper_bones_clavicula
        ),
        Bone(
            id = "scapula",
            names = mapOf(
                Language.LATIN to "Scapula",
                Language.ENGLISH to "Scapula",
                Language.FINNISH to "Lapaluu"
            ),
            baseDrawableRes = R.drawable.upper_bones_base,
            highlightMaskRes = R.drawable.upper_bones_scapula
        ),
        Bone(
            id = "sternum",
            names = mapOf(
                Language.LATIN to "Sternum",
                Language.ENGLISH to "Sternum",
                Language.FINNISH to "Rintalasta"
            ),
            baseDrawableRes = R.drawable.upper_bones_base,
            highlightMaskRes = R.drawable.upper_bones_sternum
        ),
        Bone(
            id = "humerus",
            names = mapOf(
                Language.LATIN to "Humerus",
                Language.ENGLISH to "Humerus",
                Language.FINNISH to "Olkaluu"
            ),
            baseDrawableRes = R.drawable.upper_bones_base,
            highlightMaskRes = R.drawable.upper_bones_humerus
        ),
        Bone(
            id = "radius",
            names = mapOf(
                Language.LATIN to "Radius",
                Language.ENGLISH to "Radius",
                Language.FINNISH to "Värttinäluu"
            ),
            baseDrawableRes = R.drawable.upper_bones_base,
            highlightMaskRes = R.drawable.upper_bones_radius
        ),
        Bone(
            id = "ulna",
            names = mapOf(
                Language.LATIN to "Ulna",
                Language.ENGLISH to "Ulna",
                Language.FINNISH to "Kyynärluu"
            ),
            baseDrawableRes = R.drawable.upper_bones_base,
            highlightMaskRes = R.drawable.upper_bones_ulna
        ),
        Bone(
            id = "costae",
            names = mapOf(
                Language.LATIN to "Costae",
                Language.ENGLISH to "Ribs",
                Language.FINNISH to "Kylkiluut"
            ),
            baseDrawableRes = R.drawable.upper_bones_base,
            highlightMaskRes = R.drawable.upper_bones_costae
        ),
        Bone(
            id = "vertebrae_cervicales",
            names = mapOf(
                Language.LATIN to "Vertebrae cervicales",
                Language.ENGLISH to "Cervical vertebrae",
                Language.FINNISH to "Kaulanikamat"
            ),
            baseDrawableRes = R.drawable.upper_bones_base,
            highlightMaskRes = R.drawable.upper_bones_vertebrae_cervicales
        ),
        Bone(
            id = "vertebrae_thoracicae",
            names = mapOf(
                Language.LATIN to "Vertebrae thoracicae",
                Language.ENGLISH to "Thoracic vertebrae",
                Language.FINNISH to "Rintanikamat"
            ),
            baseDrawableRes = R.drawable.upper_bones_base,
            highlightMaskRes = R.drawable.upper_bones_vertebrae_thoracicae
        ),
        Bone(
            id = "vertebrae_lumbales",
            names = mapOf(
                Language.LATIN to "Vertebrae lumbales",
                Language.ENGLISH to "Lumbar vertebrae",
                Language.FINNISH to "Lannenikamat"
            ),
            baseDrawableRes = R.drawable.upper_bones_base,
            highlightMaskRes = R.drawable.upper_bones_vertebrae_lumbales
        )
    )

    val lowerBodyBones = listOf(
        Bone(
            id = "sacrum",
            names = mapOf(
                Language.LATIN to "Os sacrum",
                Language.ENGLISH to "Sacrum",
                Language.FINNISH to "Ristiluu"
            ),
            baseDrawableRes = R.drawable.lower_bones_base,
            highlightMaskRes = R.drawable.lower_bones_sacrum
        ),
        Bone(
            id = "coccygis",
            names = mapOf(
                Language.LATIN to "Os coccygis",
                Language.ENGLISH to "Coccyx",
                Language.FINNISH to "Häntäluu"
            ),
            baseDrawableRes = R.drawable.lower_bones_base,
            highlightMaskRes = R.drawable.lower_bones_coccygis
        ),
        Bone(
            id = "coxae",
            names = mapOf(
                Language.LATIN to "Os coxae",
                Language.ENGLISH to "Hip bone",
                Language.FINNISH to "Lonkkaluu"
            ),
            baseDrawableRes = R.drawable.lower_bones_base,
            highlightMaskRes = R.drawable.lower_bones_coxae
        ),
        Bone(
            id = "femur",
            names = mapOf(
                Language.LATIN to "Femur",
                Language.ENGLISH to "Femur",
                Language.FINNISH to "Reisiluu"
            ),
            baseDrawableRes = R.drawable.lower_bones_base,
            highlightMaskRes = R.drawable.lower_bones_femur
        ),
        Bone(
            id = "patella",
            names = mapOf(
                Language.LATIN to "Patella",
                Language.ENGLISH to "Patella",
                Language.FINNISH to "Polvilumpio"
            ),
            baseDrawableRes = R.drawable.lower_bones_base,
            highlightMaskRes = R.drawable.lower_bones_patella
        ),
        Bone(
            id = "tibia",
            names = mapOf(
                Language.LATIN to "Tibia",
                Language.ENGLISH to "Tibia",
                Language.FINNISH to "Sääriluu"
            ),
            baseDrawableRes = R.drawable.lower_bones_base,
            highlightMaskRes = R.drawable.lower_bones_tibia
        ),
        Bone(
            id = "fibula",
            names = mapOf(
                Language.LATIN to "Fibula",
                Language.ENGLISH to "Fibula",
                Language.FINNISH to "Pohjeluu"
            ),
            baseDrawableRes = R.drawable.lower_bones_base,
            highlightMaskRes = R.drawable.lower_bones_fibula
        )
    )

    /**
     * Returns a list of bones based on the specified anatomy area.
     *
     * @param anatomyArea The name of the anatomy area (e.g., "Hand", "Foot", "Skull", "Upper Body", "Lower Body").
     * @return A list of [Bone] objects for the given area, or an empty list if the area is not found.
     */
    fun getBones(anatomyArea: String): List<Bone> {
        return when (anatomyArea) {
            "Hand" -> handBones
            "Foot" -> footBones
            "Skull" -> skullBones
            "Upper Body" -> upperBodyBones
            "Lower Body" -> lowerBodyBones
            else -> emptyList()
        }
    }
}
