package islam.muslim.app.utils.data

import islam.muslim.app.R
import islam.muslim.app.models.PrayerGuideModel

object PrayerGuideProvider {

    fun getPrayerGuideMaleData(): ArrayList<PrayerGuideModel> {
        return arrayListOf(
            PrayerGuideModel(
                "How to Pray",
                R.drawable.ic_bismillah,
                "Prayer (salah; plural salawat) is one of the five pillars of Islam. \n  \nIt is incumbent upon all mature Muslims, and highly " +
                        "recommended for children aged ten and over, to complete their five daily prayers according to the way the Prophet (PBUH) " +
                        "performed it in order for them to be valid. It is believed that communication with Allah will bring life to the prayerful " +
                        "and bring them courage. \n \nMuslims believe that Allah speaks to us through the Qur'an, and salaah is our means of " +
                        "connecting to Allah. \n \nWhether you're just curious as to how Muslims pray or if you're looking to learn for yourself, " +
                        "it's never too late to start.",
                1
            ),
            PrayerGuideModel(
                "Make your intention known in your heart.",
                R.drawable.prayer_guide_one,
                "Before initiating the salat, it is important that you have the intention to pray.",
                2
            ),
            PrayerGuideModel(
                "Raise your hands up next to your ears and shoulders.",
                R.drawable.prayer_guide_two,
                "Raise your hands up next to your ears and shoulders, then say Allāhu akbar (الله أَكْبَر). This translates to \"Allah is the " +
                        "greatest.\" Do this while standing (or sitting if you can't stand). \n \nThe fingers should be spaced when the hands are" +
                        " raised.",
                2
            ),
            PrayerGuideModel(
                "Place your right hand over your left hand.",
                R.drawable.prayer_guide_three,
                "Place your hands either on your navel, on your chest, or between the two; there's a difference of opinion between Islamic schools " +
                        "of thoughts. Keep your eyes focused on the ground. It helps to keep your sight fixed on where your head will rest when " +
                        "prostrating later. Do not let your eyes wander. \n \nRecite the optional Istiftah Du'a (opening prayer): \n \nFollow it " +
                        "with " +
                        "Surah Al-Fatiha (this Surah is recited in each Rak'ah): \n \nRecite any other surah or any part of the Qu'ran, such as " +
                        "Surah Ikhlas. \n \nReciting a surah after Al-Fatiha is only done in the first 2 units (Rakaa's) of fardh salat while in " +
                        "sunnah mu'akkadah it is done in all units(Rakaa's)",
                2
            ),
            PrayerGuideModel(
                "Say \"Allahu Akbar\" and bend down.",
                R.drawable.prayer_guide_four,
                "Bend your body so that your back and neck are as straight as possible and level with the ground, keeping your eyes focused on the ground and keep your fingers and palm on the knees. Your back and head should be at a 90° angle with your legs. You don't need to make a perfect angle; your body should be relaxed. This position is called ruku.",
                2
            ),
            PrayerGuideModel(
                "Stand back up (raise from ruku).",
                R.drawable.prayer_guide_five,
                "While rising up with your arms beside you.",
                2
            ),
            PrayerGuideModel(
                "Say Allahu Akbar and prostrate.",
                R.drawable.prayer_guide_six,
                "Place your head, knees and hands on the floor. While \"sujood\" (prostration) make sure that your forehead and nose touch the ground. This is the position called \"sajdah.\"",
                2
            ),
            PrayerGuideModel(
                "Rise from sajdah and sit on your knees.",
                R.drawable.prayer_guide_seven,
                "Place your left foot from ball to heel on the floor. Your right foot should be toes on the floor only. Place your hands flat on your knees.",
                2
            ),
            PrayerGuideModel(
                "Say Allahu akbar and stand up.",
                R.drawable.prayer_guide_eight,
                "You have completed one rak'ah. Depending on the salah, you may need to complete up to three more. \n \nIn every second rak'ah, " +
                        "after the second sajdah, sit on your knee again. Recite: \n \nTashahhud \nDurood",
                2
            ),
            PrayerGuideModel(
                "Finish the prayer with tasleem.",
                R.drawable.prayer_guide_nine,
                "After the tashahhud, durood, and any du'a you wish to recite, end the prayer by: \n \nTurning your head to the right and saying, " +
                        "As-salamu alaykum wa Rahmatullahi. The angel who records your good deeds is to this side. \n \nTurning your head to the " +
                        "left and saying, As-salamu alaykum wa Rahmatullahi. The angel who records your wrongful deeds is to this side. The prayers have ended!",
                2
            ),

            )
    }

    fun getPrayerGuideFemaleData(): ArrayList<PrayerGuideModel> {
        return arrayListOf(
            PrayerGuideModel(
                "How to Pray",
                R.drawable.ic_bismillah,
                "Prayer (salah; plural salawat) is one of the five pillars of Islam.\nIt is incumbent upon all mature Muslims, and highly " +
                        "recommended for children aged ten and over, to complete their five daily prayers according to the way the Prophet (PBUH) " +
                        "performed it in order for them to be valid. It is believed that communication with Allah will bring life to the prayerful " +
                        "and bring them courage.\nMuslims believe that Allah speaks to us through the Qur'an, and salaah is our means of connecting" +
                        " to Allah.\nWhether you're just curious as to how Muslims pray or if you're looking to learn for yourself, it's never too" +
                        " late to start.",
                1
            ),
            PrayerGuideModel(
                "Make your intention known in your heart.",
                R.drawable.prayer_guide_female_one,
                "Before initiating the salat, it is important that you have the intention to pray.",
                2
            ),
            PrayerGuideModel(
                "Raise your hands up next to your ears and shoulders.",
                R.drawable.prayer_guide_female_two,
                "Stand with one's feet close together, raising one's hands to the level of the shoulders, palms facing the qibla , fingers naturally outspread, with one's wrists and forearms covered",
                2
            ),
            PrayerGuideModel(
                "Place your right hand over your left hand.",
                R.drawable.prayer_guide_female_three,
                "Stand with one's feet close together, raising one's hands to the level of the shoulders, palms facing the qibla , fingers " +
                        "naturally outspread, with one's wrists and forearms covered. There's a difference of opinion between Islamic schools of " +
                        "thoughts. Keep your eyes focused on the ground. It helps to keep your sight fixed on where your head will rest when prostrating later. Do not let your eyes wander.\n" +
                        "Recite the optional Istiftah Du'a (opening prayer):\nFollow it with Surah Al-Fatiha (this Surah is recited in each Rak'ah)" +
                        "\n" +
                        ": Recite any other surah or any part of the Qu'ran, such as Surah Ikhlas.\nReciting a surah after Al-Fatiha is only done " +
                        "in the first 2 units (Rakaa's) of fardh salat while in sunnah mu'akkadah it is done in all units(Rakaa's)",
                2
            ),
            PrayerGuideModel(
                "Say \"Allahu Akbar\" and bend down.",
                R.drawable.prayer_guide_female_four,
                "Bend your body so that your back and neck are as straight as possible and level with the ground, keeping your eyes focused on the " +
                        "ground and keep your fingers and palm on the knees. Your back and head should be at a 90° angle with your legs. You don't need to make a perfect angle; your body should be relaxed. This position is called ruku.",
                2
            ),
            PrayerGuideModel(
                "Stand back up (raise from ruku).",
                R.drawable.prayer_guide_female_five,
                "Stand up completely. Say this supplication in a lowered voice while in the standing position with one's gaze fixed on the place of prostration.",
                2
            ),
            PrayerGuideModel(
                "Say Allahu Akbar and prostrate.",
                R.drawable.prayer_guide_female_six,
                "Place your head, knees and hands on the floor. While \"sujood\" (prostration) make sure that your forehead and nose touch the ground. This is the position called \"sajdah.\"",
                2
            ),
            PrayerGuideModel(
                "Rise from sajdah and sit on your knees.",
                R.drawable.prayer_guide_female_seven,
                "Draw both legs to the right side, thighs on top of each other, sitting on the left buttocks, hands on top of thighs such that the " +
                        "tips of the fingers reach the knees, fingers together, and eyes gazing at one's lap.",
                2
            ),
            PrayerGuideModel(
                "Say Allahu akbar and stand up.",
                R.drawable.prayer_guide_female_eight,
                "You have completed one rak'ah. Depending on the salah, you may need to complete up to three more.\n" +
                        "In every second rak'ah, after " +
                        "the second sajdah, sit on your knee again. Recite:\nTashahhud Durood",
                2
            ),
            PrayerGuideModel(
                "Finish the prayer with tasleem.",
                R.drawable.prayer_guide_female_nine,
                "After the tashahhud, durood, and any du'a you wish to recite, end the prayer by:\nTurning your head to the right and saying, " +
                        "As-salamu alaykum wa Rahmatullahi. The angel who records your good deeds is to this side.\nTurning your head to the left " +
                        "and saying, As-salamu alaykum wa Rahmatullahi. The angel who records your wrongful deeds is to this side. The prayers have ended!",
                2
            ),

            )

    }

}