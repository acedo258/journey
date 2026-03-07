package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
  Directory that manages user groups and their access permissions.
  Each group has a schedule, allowed actions, and authorized areas.
  Groups: admin (full access), manager, employee, blank (no access).
 */
public class DirectoryUserGroups {
  private static final Logger logger = LoggerFactory.getLogger(DirectoryUserGroups.class);

  private static final ArrayList<UserGroup> userGroups = new ArrayList<>();

  // Private constructor to prevent instantiation
  private DirectoryUserGroups() {
      super();
  }

  /*
    Creates user groups with their schedules, permissions and users.
    Must be called after DirectoryAreas is initialized.
   */
  public static void makeUserGroups() {
    // Define day sets
    final List<DayOfWeek> list = Arrays.asList(
        DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
    final ArrayList<DayOfWeek> weekdays = new ArrayList<>(list);
    final ArrayList<DayOfWeek> weekdaysPlusSaturday = new ArrayList<>(weekdays);
    weekdaysPlusSaturday.add(DayOfWeek.SATURDAY);
    final DayOfWeek[] values = DayOfWeek.values();
    final List<DayOfWeek> list1 = Arrays.asList(values);
    final ArrayList<DayOfWeek> allDays = new ArrayList<>(list1);

    // Define schedules
    final LocalDate startDate = LocalDate.of(2025, 1, 1);
    final Schedule adminSchedule = new Schedule(
        startDate, LocalDate.of(2100, 12, 31),
        allDays, LocalTime.of(0, 0), LocalTime.of(23, 59));
    final Schedule managerSchedule = new Schedule(
        LocalDate.of(2024, 9, 1), LocalDate.of(2025, 3, 1),
        weekdaysPlusSaturday, LocalTime.of(8, 0), LocalTime.of(20, 0));
    final Schedule employeeSchedule = new Schedule(
        LocalDate.of(2024, 9, 1), LocalDate.of(2025, 3, 1),
        weekdays, LocalTime.of(9, 0), LocalTime.of(17, 0));
    final Schedule blankSchedule = new Schedule(
        LocalDate.of(2099, 1, 1), LocalDate.of(2099, 1, 1),
        new ArrayList<>(), LocalTime.of(0, 0), LocalTime.of(0, 0));

    // Define action sets
    final ArrayList<String> allActions = new ArrayList<>(Arrays.asList(
        Actions.OPEN, Actions.CLOSE, Actions.LOCK,
        Actions.UNLOCK, Actions.UNLOCK_SHORTLY));
    final ArrayList<String> employeeActions = new ArrayList<>(Arrays.asList(
        Actions.OPEN, Actions.CLOSE, Actions.UNLOCK_SHORTLY));
    final ArrayList<String> noActions = new ArrayList<>();

    // Get areas for permissions
    final Area building = DirectoryAreas.findAreaById("building");
    final Area groundFloor = DirectoryAreas.findAreaById("ground_floor");
    final Area floor1 = DirectoryAreas.findAreaById("floor1");
    final Area exterior = DirectoryAreas.findAreaById("exterior");
    final Area stairs = DirectoryAreas.findAreaById("stairs");

    final ArrayList<Area> allAreas = new ArrayList<>(Arrays.asList(building));
    final ArrayList<Area> employeeAreas = new ArrayList<>(Arrays.asList(
        groundFloor, floor1, exterior, stairs));
    final ArrayList<Area> noAreas = new ArrayList<>();

    // Create groups
    final UserGroup admin = new UserGroup("admin", adminSchedule, allActions, allAreas);
    final UserGroup manager = new UserGroup("manager", managerSchedule, allActions, allAreas);
    final UserGroup employee = new UserGroup("employee", employeeSchedule,
        employeeActions, employeeAreas);
    final UserGroup blank = new UserGroup("blank", blankSchedule, noActions, noAreas);

    // Create and assign users
    final User bernat = new User("Bernat", "12345");
    final User blai = new User("Blai", "77532");
    blank.addUser(bernat);
    blank.addUser(blai);

    final User ernest = new User("Ernest", "74984");
    final User eulalia = new User("Eulalia", "43295");
    employee.addUser(ernest);
    employee.addUser(eulalia);

    final User manel = new User("Manel", "95783");
    final User marta = new User("Marta", "05827");
    manager.addUser(manel);
    manager.addUser(marta);

    final User ana = new User("Ana", "11343");
    admin.addUser(ana);

      DirectoryUserGroups.userGroups.add(admin);
      DirectoryUserGroups.userGroups.add(manager);
      DirectoryUserGroups.userGroups.add(employee);
      DirectoryUserGroups.userGroups.add(blank);

    final int size = DirectoryUserGroups.userGroups.size();
      DirectoryUserGroups.logger.info("Created {} user groups with {} total users",
        size, 7);
  }

  public static User findUserByCredential(final String credential) {
    for (final UserGroup group : DirectoryUserGroups.userGroups) {
      for (final User user : group.getUsers()) {
        final String credential1 = user.getCredential();
        if (credential1.equals(credential)) {
          return user;
        }
      }
    }
      DirectoryUserGroups.logger.warn("User with credential {} not found", credential);
    return null;
  }

  public static UserGroup findUserGroupByName(final String name) {
    for (final UserGroup group : DirectoryUserGroups.userGroups) {
      final String name1 = group.getName();
      if (name1.equals(name)) {
        return group;
      }
    }
      DirectoryUserGroups.logger.warn("User group {} not found", name);
    return null;
  }

  public static ArrayList<UserGroup> getUserGroups() {
    return DirectoryUserGroups.userGroups;
  }
}
