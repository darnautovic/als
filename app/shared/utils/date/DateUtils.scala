package shared.utils.date

import java.util.Date

import org.joda.time._
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}

object DateUtils
{
  final val dateTimeFormatter             : DateTimeFormatter = DateTimeFormat.forPattern("dd MMMM YYYY");
  final val dateTimeFormatterWithMinutes  : DateTimeFormatter = DateTimeFormat.forPattern("dd MMMM YYYY, HH:mm");
  final val dateTimeFormatterWithSeconds  : DateTimeFormatter = DateTimeFormat.forPattern("dd MMMM YYYY HH:mm:ss");
  final val DD_MMM_YYYY__dateFormatter    : DateTimeFormatter = DateTimeFormat.forPattern("dd MMM YYYY");
  final val POSTGRES_DATE_FORMAT          : DateTimeFormatter = DateTimeFormat.forPattern("YYYY-MM-dd")
  final val HH_mm_DATE_FORMATTER          : DateTimeFormatter = DateTimeFormat.forPattern("HH:mm")
  final val YYYY_MM_dd__dateFormatter     : DateTimeFormatter = DateTimeFormat.forPattern("YYYY-MM-dd");

  def nowDateTimeUTC(): DateTime =
  {
    DateTime.now(DateTimeZone.UTC)
  }
  def nowLocalDateUTC(): LocalDate =
  {
    LocalDate.now(DateTimeZone.UTC)
  }
  def jodaDateTimeToJavaDate(value: DateTime): Date =
  {
    value.toDate
  }
  def javaDateToJodaDateTime(value: Date): DateTime =
  {
    new DateTime(value).withZone(DateTimeZone.UTC)
  }
}