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
                Language.LATIN to "Ulna | Os ulnae",
                Language.ENGLISH to "Ulna | Ulnae",
                Language.FINNISH to "Kyynärluu | Kyynärluut"
            ),
            baseDrawableRes = R.drawable.hand_bones_base,
            highlightMaskRes = R.drawable.hand_bones_ulna
        ),
        Bone(
            id = "radius",
            names = mapOf(
                Language.LATIN to "Radius | Os radii",
                Language.ENGLISH to "Radius | Radii",
                Language.FINNISH to "Värttinäluu | Värttinäluut"
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
                Language.LATIN to "Ossa metacarpi | Os metacarpi | Ossa metacarpalia",
                Language.ENGLISH to "Metacarpals | Metacarpal",
                Language.FINNISH to "Kämmenluut | Kämmenluu"
            ),
            baseDrawableRes = R.drawable.hand_bones_base,
            highlightMaskRes = R.drawable.hand_bones_metacarpalia
        ),
        Bone(
            id = "phalages_proximalis",
            names = mapOf(
                Language.LATIN to "Phalanges proximales | Phalanx proximalis",
                Language.ENGLISH to "Proximal phalanges | Proximal phalanx",
                Language.FINNISH to "Tyviluut | Tyviluu | Sormien tyviluut"
            ),
            baseDrawableRes = R.drawable.hand_bones_base,
            highlightMaskRes = R.drawable.hand_bones_phalages_proximalis
        ),
        Bone(
            id = "phalages_mediae",
            names = mapOf(
                Language.LATIN to "Phalanges mediae | Phalanx media",
                Language.ENGLISH to "Middle phalanges | Middle phalanx",
                Language.FINNISH to "Keskiluut | Keskiluu | Sormien keskiluut"
            ),
            baseDrawableRes = R.drawable.hand_bones_base,
            highlightMaskRes = R.drawable.hand_bones_phalages_mediae
        ),
        Bone(
            id = "phalages_distales",
            names = mapOf(
                Language.LATIN to "Phalanges distales | Phalanx distalis",
                Language.ENGLISH to "Distal phalanges | Distal phalanx",
                Language.FINNISH to "Kärkiluut | Kärkiluu | Sormien kärkiluut"
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
                Language.ENGLISH to "Talus | Tali",
                Language.FINNISH to "Telaluu | Telaluut"
            ),
            baseDrawableRes = R.drawable.foot_bones_base,
            highlightMaskRes = R.drawable.foot_bones_talus
        ),
        Bone(
            id = "calcaneus",
            names = mapOf(
                Language.LATIN to "Calcaneus | Os calcis",
                Language.ENGLISH to "Calcaneus | Calcanei",
                Language.FINNISH to "Kantaluu | Kantaluut"
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
                Language.LATIN to "Ossa metatarsi | Ossa metatarsalia | Os metatarsi",
                Language.ENGLISH to "Metatarsals | Metatarsal",
                Language.FINNISH to "Jalkapöytäluut | Jalkapöytäluu"
            ),
            baseDrawableRes = R.drawable.foot_bones_base,
            highlightMaskRes = R.drawable.foot_bones_metatarsalia
        ),
        Bone(
            id = "phalages_proximalis",
            names = mapOf(
                Language.LATIN to "Phalanges proximales | Phalanx proximalis",
                Language.ENGLISH to "Proximal phalanges | Proximal phalanx",
                Language.FINNISH to "Tyviluut | Tyviluu | Varpaiden tyviluut"
            ),
            baseDrawableRes = R.drawable.foot_bones_base,
            highlightMaskRes = R.drawable.foot_bones_phalages_proximalis
        ),
        Bone(
            id = "phalages_mediae",
            names = mapOf(
                Language.LATIN to "Phalanges mediae | Phalanx media",
                Language.ENGLISH to "Middle phalanges | Middle phalanx",
                Language.FINNISH to "Keskiluut | Keskiluu | Varpaiden keskiluut"
            ),
            baseDrawableRes = R.drawable.foot_bones_base,
            highlightMaskRes = R.drawable.foot_bones_phalages_mediae
        ),
        Bone(
            id = "phalages_distales",
            names = mapOf(
                Language.LATIN to "Phalanges distales | Phalanx distalis",
                Language.ENGLISH to "Distal phalanges | Distal phalanx",
                Language.FINNISH to "Kärkiluut | Kärkiluu | Varpaiden kärkiluut"
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
                Language.LATIN to "Os nasale | Ossa nasalia",
                Language.ENGLISH to "Nasal bone | Nasal bones",
                Language.FINNISH to "Nenäluu | Nenäluut"
            ),
            baseDrawableRes = R.drawable.skull_bones_base,
            highlightMaskRes = R.drawable.skull_bones_nasale
        ),
        Bone(
            id = "lacrimale",
            names = mapOf(
                Language.LATIN to "Os lacrimale | Ossa lacrimalia",
                Language.ENGLISH to "Lacrimal bone | Lacrimal bones",
                Language.FINNISH to "Kyynelluu | Kyynelluut"
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
            id = "parietale",
            names = mapOf(
                Language.LATIN to "Os parietale | Ossa parietalia",
                Language.ENGLISH to "Parietal bone | Parietal bones",
                Language.FINNISH to "Päälaenluu | Päälaenluut"
            ),
            baseDrawableRes = R.drawable.skull_bones_base,
            highlightMaskRes = R.drawable.skull_bones_parietale
        ),
        Bone(
            id = "zygomaticum",
            names = mapOf(
                Language.LATIN to "Os zygomaticum | Ossa zygomatica",
                Language.ENGLISH to "Zygomatic bone | Zygomatic bones",
                Language.FINNISH to "Poskiluu | Poskiluut"
            ),
            baseDrawableRes = R.drawable.skull_bones_base,
            highlightMaskRes = R.drawable.skull_bones_zygomaticum
        ),
        Bone(
            id = "maxilla",
            names = mapOf(
                Language.LATIN to "Maxilla | Os maxillae | Os maxillare",
                Language.ENGLISH to "Maxilla | Upper jaw",
                Language.FINNISH to "Yläleukaluu | Yläleuka"
            ),
            baseDrawableRes = R.drawable.skull_bones_base,
            highlightMaskRes = R.drawable.skull_bones_maxilla
        ),
        Bone(
            id = "mandibula",
            names = mapOf(
                Language.LATIN to "Mandibula | Os mandibulae | Os mandibulare",
                Language.ENGLISH to "Mandible | Jawbone",
                Language.FINNISH to "Alaleukaluu | Alaleuka"
            ),
            baseDrawableRes = R.drawable.skull_bones_base,
            highlightMaskRes = R.drawable.skull_bones_mandibula
        ),
        Bone(
            id = "temporale",
            names = mapOf(
                Language.LATIN to "Os temporale | Ossa temporalia",
                Language.ENGLISH to "Temporal bone | Temporal bones",
                Language.FINNISH to "Ohimoluu | Ohimoluut"
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
                Language.LATIN to "Clavicula | Os claviculae",
                Language.ENGLISH to "Clavicle | Collarbone | Clavicles",
                Language.FINNISH to "Solisluu | Solisluut"
            ),
            baseDrawableRes = R.drawable.upper_bones_base,
            highlightMaskRes = R.drawable.upper_bones_clavicula
        ),
        Bone(
            id = "scapula",
            names = mapOf(
                Language.LATIN to "Scapula | Os scapulae",
                Language.ENGLISH to "Scapula | Shoulder blade | Scapulae",
                Language.FINNISH to "Lapaluu | Lapaluut"
            ),
            baseDrawableRes = R.drawable.upper_bones_base,
            highlightMaskRes = R.drawable.upper_bones_scapula
        ),
        Bone(
            id = "sternum",
            names = mapOf(
                Language.LATIN to "Sternum",
                Language.ENGLISH to "Sternum | Breastbone",
                Language.FINNISH to "Rintalasta"
            ),
            baseDrawableRes = R.drawable.upper_bones_base,
            highlightMaskRes = R.drawable.upper_bones_sternum
        ),
        Bone(
            id = "manubrium",
            names = mapOf(
                Language.LATIN to "Manubrium sterni",
                Language.ENGLISH to "Manubrium",
                Language.FINNISH to "Rintalastan kahva"
            ),
            baseDrawableRes = R.drawable.upper_bones_base,
            highlightMaskRes = R.drawable.upper_bones_manubrium
        ),
        Bone(
            id = "gladiolus",
            names = mapOf(
                Language.LATIN to "Corpus sterni",
                Language.ENGLISH to "Body of sternum",
                Language.FINNISH to "Rintalastan runko"
            ),
            baseDrawableRes = R.drawable.upper_bones_base,
            highlightMaskRes = R.drawable.upper_bones_gladiolus
        ),
        Bone(
            id = "processus_xiphoideus",
            names = mapOf(
                Language.LATIN to "Processus xiphoideus",
                Language.ENGLISH to "Xiphoid process",
                Language.FINNISH to "Miekkalisäke"
            ),
            baseDrawableRes = R.drawable.upper_bones_base,
            highlightMaskRes = R.drawable.upper_bones_processus_xiphoideus
        ),
        Bone(
            id = "humerus",
            names = mapOf(
                Language.LATIN to "Humerus | Os humeri",
                Language.ENGLISH to "Humerus | Arm bone | Humeri",
                Language.FINNISH to "Olkaluu | Olkaluut"
            ),
            baseDrawableRes = R.drawable.upper_bones_base,
            highlightMaskRes = R.drawable.upper_bones_humerus
        ),
        Bone(
            id = "radius",
            names = mapOf(
                Language.LATIN to "Radius | Os radii",
                Language.ENGLISH to "Radius | Radii",
                Language.FINNISH to "Värttinäluu | Värttinäluut"
            ),
            baseDrawableRes = R.drawable.upper_bones_base,
            highlightMaskRes = R.drawable.upper_bones_radius
        ),
        Bone(
            id = "ulna",
            names = mapOf(
                Language.LATIN to "Ulna | Os ulnae",
                Language.ENGLISH to "Ulna | Ulnae",
                Language.FINNISH to "Kyynärluu | Kyynärluut"
            ),
            baseDrawableRes = R.drawable.upper_bones_base,
            highlightMaskRes = R.drawable.upper_bones_ulna
        ),
        Bone(
            id = "costae",
            names = mapOf(
                Language.LATIN to "Costae | Costa",
                Language.ENGLISH to "Ribs | Rib",
                Language.FINNISH to "Kylkiluut | Kylkiluu"
            ),
            baseDrawableRes = R.drawable.upper_bones_base,
            highlightMaskRes = R.drawable.upper_bones_costae
        ),
        Bone(
            id = "vertebrae_cervicales",
            names = mapOf(
                Language.LATIN to "Vertebrae cervicales | Vertebra cervicalis",
                Language.ENGLISH to "Cervical vertebrae | Cervical vertebra",
                Language.FINNISH to "Kaulanikamat | Kaulanikama"
            ),
            baseDrawableRes = R.drawable.upper_bones_base,
            highlightMaskRes = R.drawable.upper_bones_vertebrae_cervicales
        ),
        Bone(
            id = "vertebrae_thoracicae",
            names = mapOf(
                Language.LATIN to "Vertebrae thoracicae | Vertebra thoracica",
                Language.ENGLISH to "Thoracic vertebrae | Thoracic vertebra",
                Language.FINNISH to "Rintanikamat | Rintanikama"
            ),
            baseDrawableRes = R.drawable.upper_bones_base,
            highlightMaskRes = R.drawable.upper_bones_vertebrae_thoracicae
        ),
        Bone(
            id = "vertebrae_lumbales",
            names = mapOf(
                Language.LATIN to "Vertebrae lumbales | Vertebra lumbalis",
                Language.ENGLISH to "Lumbar vertebrae | Lumbar vertebra",
                Language.FINNISH to "Lannenikamat | Lannenikama"
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
                Language.ENGLISH to "Hip bone | Hip bones",
                Language.FINNISH to "Lonkkaluu | Lonkkaluut"
            ),
            baseDrawableRes = R.drawable.lower_bones_base,
            highlightMaskRes = R.drawable.lower_bones_coxae
        ),
        Bone(
            id = "ilium",
            names = mapOf(
                Language.LATIN to "Os ilium | Ossa ilia",
                Language.ENGLISH to "Ilium | Ilia",
                Language.FINNISH to "Suoliluu | Suoliluut"
            ),
            baseDrawableRes = R.drawable.lower_bones_base,
            highlightMaskRes = R.drawable.lower_bones_ilium
        ),
        Bone(
            id = "ischium",
            names = mapOf(
                Language.LATIN to "Os ischii | Ossa ischia",
                Language.ENGLISH to "Ischium | Ischia",
                Language.FINNISH to "Istuinluu | Istuinluut"
            ),
            baseDrawableRes = R.drawable.lower_bones_base,
            highlightMaskRes = R.drawable.lower_bones_ischii
        ),
        Bone(
            id = "pubis",
            names = mapOf(
                Language.LATIN to "Os pubis | Ossa pubes",
                Language.ENGLISH to "Pubis | Pubes",
                Language.FINNISH to "Häpyluu | Häpyluut"
            ),
            baseDrawableRes = R.drawable.lower_bones_base,
            highlightMaskRes = R.drawable.lower_bones_pubis
        ),
        Bone(
            id = "femur",
            names = mapOf(
                Language.LATIN to "Femur | Os femoris",
                Language.ENGLISH to "Femur | Femurs",
                Language.FINNISH to "Reisiluu | Reisiluut"
            ),
            baseDrawableRes = R.drawable.lower_bones_base,
            highlightMaskRes = R.drawable.lower_bones_femur
        ),
        Bone(
            id = "patella",
            names = mapOf(
                Language.LATIN to "Patella | Os patellae",
                Language.ENGLISH to "Patella | Kneecap | Patellae",
                Language.FINNISH to "Polvilumpio | Lumpio | Polvilumpiot"
            ),
            baseDrawableRes = R.drawable.lower_bones_base,
            highlightMaskRes = R.drawable.lower_bones_patella
        ),
        Bone(
            id = "tibia",
            names = mapOf(
                Language.LATIN to "Tibia | Os tibiae",
                Language.ENGLISH to "Tibia | Shinbone | Tibiae",
                Language.FINNISH to "Sääriluu | Sääriluut"
            ),
            baseDrawableRes = R.drawable.lower_bones_base,
            highlightMaskRes = R.drawable.lower_bones_tibia
        ),
        Bone(
            id = "fibula",
            names = mapOf(
                Language.LATIN to "Fibula | Os fibulae",
                Language.ENGLISH to "Fibula | Calf bone | Fibulae",
                Language.FINNISH to "Pohjeluu | Pohjeluut"
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
