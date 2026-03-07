package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;

/*
 Directory that holds all doors in the access control system.
 Creates doors connecting spaces and provides lookup functionality.
 */
public final class DirectoryDoors {
  private static final Logger logger = LoggerFactory.getLogger(DirectoryDoors.class);

  private static ArrayList<Door> allDoors;

  // Private constructor to prevent instantiation
  private DirectoryDoors() {
      super();
  }

  /*
    Creates all doors connecting spaces in the building.
    Must be called after DirectoryAreas is initialized.
   */
  public static void makeDoors() {
      DirectoryDoors.allDoors = new ArrayList<>();

    // Get spaces from DirectoryAreas
    final Space exterior = (Space) DirectoryAreas.findAreaById("exterior");
    final Space stairs = (Space) DirectoryAreas.findAreaById("stairs");
    final Space parking = (Space) DirectoryAreas.findAreaById("parking");
    final Space hall = (Space) DirectoryAreas.findAreaById("hall");
    final Space room1 = (Space) DirectoryAreas.findAreaById("room1");
    final Space room2 = (Space) DirectoryAreas.findAreaById("room2");
    final Space corridor = (Space) DirectoryAreas.findAreaById("corridor");
    final Space room3 = (Space) DirectoryAreas.findAreaById("room3");
    final Space it = (Space) DirectoryAreas.findAreaById("IT");

    // Create doors connecting spaces (from -> to)
      DirectoryDoors.allDoors.add(new Door("D1", exterior, parking));
      DirectoryDoors.allDoors.add(new Door("D2", stairs, parking));
      DirectoryDoors.allDoors.add(new Door("D3", exterior, hall));
      DirectoryDoors.allDoors.add(new Door("D4", stairs, hall));
      DirectoryDoors.allDoors.add(new Door("D5", hall, room1));
      DirectoryDoors.allDoors.add(new Door("D6", hall, room2));
      DirectoryDoors.allDoors.add(new Door("D7", stairs, corridor));
      DirectoryDoors.allDoors.add(new Door("D8", corridor, room3));
      DirectoryDoors.allDoors.add(new Door("D9", corridor, it));

    final int size = DirectoryDoors.allDoors.size();
      DirectoryDoors.logger.info("Created {} doors", size);
  }

  /*
    Finds a door by its ID.
   */
  public static Door findDoorById(final String id) {
    if (null == DirectoryDoors.allDoors) {
        DirectoryDoors.logger.error("Doors not initialized, call makeDoors() first");
      return null;
    }
    for (final Door door : DirectoryDoors.allDoors) {
      final String id1 = door.getId();
      if (id1.equals(id)) {
        return door;
      }
    }
      DirectoryDoors.logger.warn("Door with id {} not found", id);
    return null;
  }

  public static ArrayList<Door> getAllDoors() {
    return DirectoryDoors.allDoors;
  }
}
