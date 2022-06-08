package com.notarmaso.beeritupcompose.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.notarmaso.beeritupcompose.R


val BeerItUpFont = FontFamily(
    Font(R.font.cafe_regular),
    Font(R.font.cafe_bold, FontWeight.Bold),
    Font(R.font.cafe_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.cafe_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.cafe_light, FontWeight.Light, FontStyle.Italic),
    Font(R.font.cafe_light_italic, FontWeight.Light)

)

// Set of Material typography styles to start with
val Typography = Typography(


    h1 = TextStyle(
        fontFamily = BeerItUpFont,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    ),
    h2 = TextStyle(
        fontFamily = BeerItUpFont,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp

    ),
    h3 = TextStyle(
        fontFamily = BeerItUpFont,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    h4 = TextStyle(
        fontFamily = BeerItUpFont,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp

    ),
    h5 = TextStyle(
        fontFamily = BeerItUpFont,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp

    ),
    h6 = TextStyle(
        fontFamily = BeerItUpFont,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp

    ),
    body1 = TextStyle(
        fontFamily = BeerItUpFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = BeerItUpFont,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp

    ),
    button =  TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        letterSpacing = 1.sp

    ),
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)