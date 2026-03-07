package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;




/*
  Represents a user in the access control system.
  Each user belongs to a UserGroup which defines their permissions.
  Authorization checks are delegated to the user's group.
 */

public class User {
  private static final Logger logger = LoggerFactory.getLogger(User.class);

  private final String name;
  private final String credential;
  private UserGroup userGroup;

  public User(final String name, final String credential) {
      super();
      this.name = name;
      this.credential = credential;
  }

  public void setUserGroup(final UserGroup userGroup) {
    this.userGroup = userGroup;
  }

  public String getCredential() {
    return this.credential;
  }

  public boolean canSendRequests(final LocalDateTime dateTime) {
    return null != this.userGroup && this.userGroup.canSendRequests(dateTime);
  }

  public boolean canDoAction(final String action) {
    return null != this.userGroup && this.userGroup.canDoAction(action);
  }

  public boolean canBeInSpace(final Space space) {
    return null != this.userGroup && this.userGroup.canBeInSpace(space);
  }

  public String getName() {
    return this.name;
  }
}