package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;


/*
  Represents a time schedule for access control.
  Defines valid date range, days of week, and time range for access.
  Used by UserGroup to restrict when users can send requests.
 */
public class Schedule {
  private static final Logger logger = LoggerFactory.getLogger(Schedule.class);

  private final LocalDate startDate;
  private final LocalDate endDate;
  private final ArrayList<DayOfWeek> daysOfWeek;
  private final LocalTime startTime;
  private final LocalTime endTime;

  public Schedule(final LocalDate startDate, final LocalDate endDate,
                  final ArrayList<DayOfWeek> daysOfWeek,
                  final LocalTime startTime, final LocalTime endTime) {
      super();
      this.startDate = startDate;
      this.endDate = endDate;
      this.daysOfWeek = daysOfWeek;
      this.startTime = startTime;
      this.endTime = endTime;
  }


    //Checks if the given date/time falls within this schedule.

  public boolean isWithinSchedule(final LocalDateTime dateTime) {
    final LocalDate date = dateTime.toLocalDate();
    final LocalTime time = dateTime.toLocalTime();
    final DayOfWeek dayOfWeek = dateTime.getDayOfWeek();

    // Check date range
    if (date.isBefore(this.startDate) || date.isAfter(this.endDate)) {
        Schedule.logger.debug("Date {} outside range [{}, {}]", date, this.startDate, this.endDate);
      return false;
    }

    // Check day of week
    if (!this.daysOfWeek.contains(dayOfWeek)) {
        Schedule.logger.debug("Day {} not in allowed days", dayOfWeek);
      return false;
    }

    // Check time range
    if (time.isBefore(this.startTime) || time.isAfter(this.endTime)) {
        Schedule.logger.debug("Time {} outside range [{}, {}]", time, this.startTime, this.endTime);
      return false;
    }

    return true;
  }
}