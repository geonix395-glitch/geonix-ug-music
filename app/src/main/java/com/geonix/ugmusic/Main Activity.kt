package com.geonix.ugmusic

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.text.Editable
import android.text.TextWatcher

class MainActivity : Activity() {

    private val backgroundColor = Color.rgb(14, 16, 19)
    private val cardColor = Color.rgb(29, 32, 37)
    private val green = Color.rgb(35, 210, 105)
    private val yellow = Color.rgb(255, 205, 45)
    private val white = Color.WHITE
    private val muted = Color.rgb(165, 172, 180)

    private lateinit var songContainer: LinearLayout
    private lateinit var nowPlaying: TextView
    private lateinit var playButton: Button
    private var isPlaying = false
    private var selectedSong = "Choose a song"

    private val songs = listOf(
        Song("Wewe", "King Saha", "UG", green),
        Song("YoYo", "Joshua Baraka", "UG", yellow),
        Song("Stress Killer", "Sheebah Karungi", "UG", green),
        Song("Tobingamba", "Ava Peace", "UG", yellow),
        Song("Kabisi Ka Ndagala", "David Lutalo", "UG", green),
        Song("Nassanga Remix", "Winnie Nwagi", "UG", yellow),
        Song("Middle", "Vinka", "UG", green),
        Song("Bankuza", "Chosen Becky", "UG", yellow)
    )

    data class Song(
        val title: String,
        val artist: String,
        val country: String,
        val accent: Int
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = backgroundColor
        window.navigationBarColor = backgroundColor

        buildInterface()
    }

    private fun dp(value: Int): Int {
        return (value * resources.displayMetrics.density).toInt()
    }

    private fun roundedBackground(
        color: Int,
        radius: Int
    ): GradientDrawable {
        return GradientDrawable().apply {
            setColor(color)
            cornerRadius = dp(radius).toFloat()
        }
    }

    private fun makeText(
        value: String,
        size: Float,
        color: Int,
        bold: Boolean = false
    ): TextView {
        return TextView(this).apply {
            text = value
            textSize = size
            setTextColor(color)
            if (bold) {
                setTypeface(null, Typeface.BOLD)
            }
        }
    }

    private fun buildInterface() {

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(backgroundColor)
        }

        val scroll = ScrollView(this)

        val content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(18), dp(18), dp(18), dp(20))
        }

        // GEONIX BRAND HEADER

        val brand = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }

        val logo = TextView(this).apply {
            text = "G"
            textSize = 28f
            setTextColor(Color.BLACK)
            setTypeface(null, Typeface.BOLD)
            gravity = Gravity.CENTER
            background = roundedBackground(green, 14)
        }

        brand.addView(
            logo,
            LinearLayout.LayoutParams(dp(52), dp(52))
        )

        val brandText = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(12), 0, 0, 0)
        }

        brandText.addView(
            makeText("GEONIX", 25f, white, true)
        )

        brandText.addView(
            makeText("UG MUSIC  •  YOUR SOUND", 11f, green, true)
        )

        brand.addView(
            brandText,
            LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f
            )
        )

        val online = makeText("● ONLINE", 11f, green, true)
        brand.addView(online)

        content.addView(brand)

        // WELCOME BANNER

        val banner = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(18), dp(20), dp(18), dp(20))
            background = roundedBackground(cardColor, 20)
        }

        val welcome = makeText(
            "YOUR MUSIC. YOUR VIBE.",
            22f,
            white,
            true
        )

        banner.addView(welcome)

        val subtitle = makeText(
            "Discover the sound of Uganda 🇺🇬",
            14f,
            muted
        )

        val subParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        subParams.topMargin = dp(8)
        banner.addView(subtitle, subParams)

        val explore = Button(this).apply {
            text = "EXPLORE MUSIC"
            setTextColor(Color.BLACK)
            textSize = 13f
            setTypeface(null, Typeface.BOLD)
            background = roundedBackground(green, 12)
            isAllCaps = false
            setOnClickListener {
                Toast.makeText(
                    this@MainActivity,
                    "Welcome to Geonix UG Music!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        val exploreParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            dp(48)
        )

        exploreParams.topMargin = dp(16)
        banner.addView(explore, exploreParams)

        val bannerParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        bannerParams.topMargin = dp(24)
        content.addView(banner, bannerParams)

        // SEARCH BAR

        content.addView(
            makeText("Find your next favourite song", 18f, white, true)
        )

        val search = EditText(this).apply {
            hint = "Search songs or artists..."
            setHintTextColor(muted)
            setTextColor(white)
            textSize = 15f
            singleLine = true
            setPadding(dp(15), 0, dp(15), 0)
            background = roundedBackground(cardColor, 14)
        }

        val searchParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            dp(52)
        )

        searchParams.topMargin = dp(12)
        searchParams.bottomMargin = dp(22)

        content.addView(search, searchParams)

        // CATEGORY LABELS

        content.addView(
            makeText("EXPLORE CATEGORIES", 12f, yellow, true)
        )

        val categories = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }

        listOf("All Music", "Bongo", "Afrobeats").forEachIndexed {
                index, name ->

            val category = TextView(this).apply {
                text = name
                textSize = 12f
                setTextColor(if (index == 0) Color.BLACK else white)
                gravity = Gravity.CENTER
                setPadding(dp(13), dp(10), dp(13), dp(10))
                background = roundedBackground(
                    if (index == 0) green else cardColor,
                    18
                )
            }

            val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                dp(40)
            )

            params.rightMargin = dp(8)
            categories.addView(category, params)
        }

        val categoryParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        categoryParams.topMargin = dp(12)
        categoryParams.bottomMargin = dp(24)

        content.addView(categories, categoryParams)

        // SONG SECTION

        content.addView(
            makeText("🔥  Latest Ugandan Music", 20f, white, true)
        )

        val description = makeText(
            "Discover tracks from your favourite artists",
            13f,
            muted
        )

        val descriptionParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        descriptionParams.topMargin = dp(6)
        descriptionParams.bottomMargin = dp(16)

        content.addView(description, descriptionParams)

        songContainer = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        content.addView(songContainer)

        search.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {}

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                renderSongs(s?.toString().orEmpty())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        renderSongs()

        scroll.addView(content)

        root.addView(
            scroll,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        )

        // MINI PLAYER

        val player = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(dp(14), dp(10), dp(14), dp(10))
            setBackgroundColor(cardColor)
        }

        nowPlaying = makeText("Choose a song", 13f, white, true)

        player.addView(
            nowPlaying,
            LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f
            )
        )

        playButton = Button(this).apply {
            text = "▶"
            textSize = 18f
            setTextColor(Color.BLACK)
            background = roundedBackground(green, 50)
            minWidth = dp(52)
            isAllCaps = false

            setOnClickListener {
                if (selectedSong == "Choose a song") {
                    Toast.makeText(
                        this@MainActivity,
                        "Select a song first",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Audio playback will be connected next",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        player.addView(
            playButton,
            LinearLayout.LayoutParams(dp(58), dp(48))
        )

        root.addView(
            player,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )

        setContentView(root)
    }

    private fun renderSongs(query: String = "") {

        songContainer.removeAllViews()

        val filtered = songs.filter {
            it.title.contains(query, ignoreCase = true) ||
            it.artist.contains(query, ignoreCase = true)
        }

        if (filtered.isEmpty()) {
            val empty = makeText(
                "No matching songs found",
                15f,
                muted
            )

            empty.setPadding(0, dp(20), 0, dp(20))
            songContainer.addView(empty)
            return
        }

        filtered.forEach { song ->

            val row = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL
                setPadding(dp(10), dp(10), dp(10), dp(10))
                background = roundedBackground(cardColor, 15)
            }

            val cover = TextView(this).apply {
                text = "♫"
                textSize = 28f
                setTextColor(Color.BLACK)
                gravity = Gravity.CENTER
                background = roundedBackground(song.accent, 12)
            }

            row.addView(
                cover,
                LinearLayout.LayoutParams(dp(58), dp(58))
            )

            val details = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(dp(12), 0, dp(5), 0)
            }

            details.addView(
                makeText(song.title, 15f, white, true)
            )

            val artist = makeText(song.artist, 12f, muted)

            val artistParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            artistParams.topMargin = dp(5)
            details.addView(artist, artistParams)

            row.addView(
                details,
                LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1f
                )
            )

            val select = Button(this).apply {
                text = "▶"
                textSize = 15f
                setTextColor(green)
                background = roundedBackground(backgroundColor, 30)
                minWidth = dp(42)
                isAllCaps = false

                setOnClickListener {
                    selectedSong = song.title
                    nowPlaying.text = song.title + " • " + song.artist

                    Toast.makeText(
                        this@MainActivity,
                        song.title + " selected",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            row.addView(
                select,
                LinearLayout.LayoutParams(dp(48), dp(48))
            )

            val rowParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            rowParams.bottomMargin = dp(10)

            songContainer.addView(row, rowParams)
        }
    }
}
