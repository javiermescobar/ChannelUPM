package utils

class DateUtils {
    companion object {
        fun obtainDateMessageString(date: String): String {
            val split = date.split('T')
            val time = split[1].split(':')
            return time[0] + ":" + time[1]
        }
    }
}