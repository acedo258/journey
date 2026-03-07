package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.util.ArrayList;



/*
  Represents a group of users with shared access permissions (RBAC pattern).
  Each group defines: schedule (when), allowed actions (what), and areas (where).
  Users inherit permissions from their group.
 */

public class UserGroup {
  private static final Logger logger = LoggerFactory.getLogger(UserGroup.class);

  private final String name;
  private final Schedule schedule;
  private final ArrayList<String> allowedActions;
  private final ArrayList<? extends Area> authorizedAreas;
  private final ArrayList<User> users;

  public UserGroup(final String name, final Schedule schedule, final ArrayList<String> allowedActions, final ArrayList<? extends Area> authorizedAreas) {
      super();
      this.name = name;
      this.schedule = schedule;
      this.allowedActions = allowedActions;
      this.authorizedAreas = authorizedAreas;
      this.users = new ArrayList<>();
  }

  public String getName() {
    return this.name;
  }

  public void addUser(final User user) {
      this.users.add(user);
    user.setUserGroup(this);
  }

  public ArrayList<User> getUsers() {
    return this.users;
  }

  public boolean canSendRequests(final LocalDateTime dateTime) {
    return this.schedule.isWithinSchedule(dateTime);
  }

  public boolean canDoAction(final String action) {
    return this.allowedActions.contains(action);
  }

  public boolean canBeInSpace(final Space space) {
    for (final Area area : this.authorizedAreas) {
      final ArrayList<Space> spaces = area.getSpaces();
      if (spaces.contains(space)) {
        return true;
      }
    }
    return false;
  }
}