package nz.ac.canterbury.cosc680.plantplus.app.presentation.utils

import kotlin.random.Random

val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
fun randomStringByKotlinCollectionRandom(string_len:Int = 4) = List(string_len) { charPool.random() }.joinToString("")
