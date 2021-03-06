package ir.awrhm.modules.extensions

import java.lang.Long
import java.math.BigDecimal

private val yekan =
    arrayOf("یک ", "دو ", "سه ", "چهار ", "پنج ", "شش ", "هفت ", "هشت ", "نه ")
private val dahgan =
    arrayOf("بیست ", "سی ", "چهل ", "پنجاه ", "شصت ", "هفتاد ", "هشتاد ", "نود ")
private val sadgan = arrayOf(
    "یکصد ",
    "دویست ",
    "سیصد ",
    "چهارصد ",
    "پانصد ",
    "ششصد ",
    "هفتصد ",
    "هشتصد ",
    "نهصد "
)
private val dah = arrayOf(
    "ده ",
    "یازده ",
    "دوازده ",
    "سیزده ",
    "چهارده ",
    "پانزده ",
    "شانزده ",
    "هفده ",
    "هیجده ",
    "نوزده "
)

fun toWord(num: BigDecimal): String {
    return onDo(num, 0)
}

private fun onDo(number: BigDecimal?, lvl: Int): String {
    var num = number
    var level = lvl
    if (num == null) {
        return ""
    }
    // convert negative number to positive and get wordify value
    if (num < BigDecimal(0)) {
        num = num.negate()
        return "منفی " + onDo(num, level)
    }
    if (num.compareTo(BigDecimal(0)) == 0) {
        return if (level == 0) {
            "صفر"
        } else {
            ""
        }
    }
    var result = ""
    if (level > 0) {
        result += "و "
        level -= 1
    }

    if (num < BigDecimal(10)) {
        result += yekan[num.add(BigDecimal(1).negate()).toInt()]
    } else if (num < BigDecimal(20)) {
        result += dah[num.add(BigDecimal(10).negate()).toInt()]
    } else if (num < BigDecimal(100)) {
        result += dahgan[num.divide(BigDecimal(10)).add(BigDecimal(2).negate()).toInt()] + onDo(
            num.remainder(BigDecimal(10)),
            level + 1
        )
    } else if (num < BigDecimal(1000)) {
        result += sadgan[num.divide(BigDecimal(100)).add(BigDecimal(1).negate()).toInt()] + onDo(
            num.remainder(BigDecimal(100)),
            level + 1
        )
    } else if (num < BigDecimal(1000000)) {
        result += onDo(
            num.divide(BigDecimal(1000)),
            level
        ) + "هزار " + onDo(
            num.remainder(
                BigDecimal(1000)
            ), level + 1
        )
    } else if (num < BigDecimal(1000000000)) {
        result += onDo(
            num.divide(BigDecimal(1000000)),
            level
        ) + "میلیون " + onDo(
            num.remainder(BigDecimal(1000000)),
            level + 1
        )
    } else if (num < BigDecimal(java.lang.Long.valueOf("1000000000000"))) {
        result += onDo(
            num.divide(BigDecimal(Long.parseLong("1000000000"))),
            level
        ) + "میلیارد " + onDo(
            num.remainder(BigDecimal(Long.parseLong("1000000000"))),
            level + 1
        )
    } else if (num.compareTo(BigDecimal(java.lang.Long.valueOf("1000000000000000"))) < 0) {
        result += onDo(
            num.divide(BigDecimal(Long.parseLong("1000000000000"))),
            level
        ) + "تریلیارد " + onDo(
            num.remainder(BigDecimal(Long.parseLong("1000000000000"))),
            level + 1
        )
    }
    return result
}