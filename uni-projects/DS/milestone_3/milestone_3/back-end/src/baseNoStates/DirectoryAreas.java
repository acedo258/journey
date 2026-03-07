package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 Singleton directory that holds the building's area hierarchy.
 Creates and manages partitions and spaces representing the building structure.
 Uses the Composite pattern for the area tree.
 */
public class DirectoryAreas {
  private static final Logger logger = LoggerFactory.getLogger(DirectoryAreas.class);
  private static final DirectoryAreas instance = new DirectoryAreas();

  private Area rootArea;

  private DirectoryAreas() {
      super();
      this.makeAreas();
      DirectoryAreas.logger.info("DirectoryAreas initialized");
  }

  public static DirectoryAreas getInstance() {
    return DirectoryAreas.instance;
  }

  private void makeAreas() {
    // Building hierarchy: building -> floors -> rooms
    final Partition building = new Partition("building", "Building", null);
    final Partition basement = new Partition("basement", "Basement", building);
    final Partition groundFloor = new Partition("ground_floor", "Ground floor", building);
    final Partition floor1 = new Partition("floor1", "Floor 1", building);

    // Spaces that belong directly to building (shared areas)
    final Space exterior = new Space("exterior", "Exterior", building);
    final Space stairs = new Space("stairs", "Stairs", building);

    // Spaces in basement
    final Space parking = new Space("parking", "Parking", basement);

    // Spaces in ground floor
    final Space hall = new Space("hall", "Hall", groundFloor);
    final Space room1 = new Space("room1", "Room 1", groundFloor);
    final Space room2 = new Space("room2", "Room 2", groundFloor);

    // Spaces in floor 1
    final Space corridor = new Space("corridor", "Corridor", floor1);
    final Space room3 = new Space("room3", "Room 3", floor1);
    final Space it = new Space("IT", "IT", floor1);

      this.rootArea = building;
      DirectoryAreas.logger.debug("Created {} areas in the building hierarchy", 12);
  }

  /*
   Finds an area by its ID in the building hierarchy.
   */
  public static Area findAreaById(final String id) {
      if ("ROOT".equals(id)) {
          return DirectoryAreas.getInstance().rootArea;
      }
      if (null != DirectoryAreas.getInstance().rootArea) {
          return DirectoryAreas.getInstance().rootArea.findAreaById(id);
      }
      DirectoryAreas.logger.warn("Root area is null, cannot find area {}", id);
      return null;
  }


    public Area getRootArea() {
    return this.rootArea;
  }
}
