package utils

import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.Period
import java.util.Objects

class DateUtils {
    companion object {
        fun obtainDateMessageString(date: String): String {
            var date = OffsetDateTime.parse(date + "Z")
            var minuteString = if (date.minute < 10) {
                "0" + date.minute
            } else {
                date.minute
            }
            return date.hour.toString() + ":" + minuteString
        }

        fun haveSameDates(previousDate: String, actualDate: String): Boolean {
            return Objects.equals(OffsetDateTime.parse(previousDate+ "Z").toLocalDate(),
                OffsetDateTime.parse(actualDate + "Z").toLocalDate())
        }

        fun getDateString(date: String): String {
            var localDate = OffsetDateTime.parse(date + "Z").toLocalDate()
            var currentDate = LocalDate.now()
            var daysBetween = Period.between(localDate, currentDate)

            return when(daysBetween.days) {
                0 -> "Hoy"
                1 -> "Ayer"
                2,3,4,5,6 -> getTranslatedDayOfWeek(localDate.dayOfWeek.value)
                else -> getTranslatedDayOfWeek(localDate.dayOfWeek.value) + " " +
                        localDate.dayOfMonth + " de " +
                        getTranslatedMonth(localDate.month.value) + " de " + localDate.year
            }
        }

        private fun getTranslatedDayOfWeek(day: Int):String {
            return when(day) {
                1 -> "Lunes"
                2 -> "Martes"
                3 -> "Miércoles"
                4 -> "Jueves"
                5 -> "Viernes"
                6 -> "Sábado"
                7 -> "Domingo"
                else -> "Undefined day"
            }
        }

        private fun getTranslatedMonth(month: Int): String {
            return when(month) {
                1 -> "enero"
                2 -> "febrero"
                3 -> "marzo"
                4 -> "abril"
                5 -> "mayo"
                6 -> "junio"
                7 -> "julio"
                8 -> "agosto"
                9 -> "septiembre"
                10 -> "octubre"
                11 -> "noviembre"
                12 -> "diciembre"
                else -> "Undefined month"
            }
        }
    }
}