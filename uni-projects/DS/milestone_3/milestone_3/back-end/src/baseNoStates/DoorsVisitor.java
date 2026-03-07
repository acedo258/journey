package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;

/*
  Visitor implementation to collect all doors giving access to spaces within an area.
  Used for area requests that affect multiple doors at once.
 */
public class DoorsVisitor implements AreaVisitor {
  private static final Logger logger = LoggerFactory.getLogger(DoorsVisitor.class);

  private final ArrayList<Door> doors = new ArrayList<>();

  public ArrayList<Door> getDoors() {
    return this.doors;
  }

  @Override
  public void visitPartition(final Partition partition) {
    // Partitions don't have doors directly, only traversed
    final String id = partition.getId();
      DoorsVisitor.logger.debug("Visiting partition for doors: {}",
        id);
  }

  @Override
  public void visitSpace(final Space space) {
    final ArrayList<Door> doors1 = space.getDoors();
      this.doors.addAll(doors1);
    final int size = doors1.size();
    final String id = space.getId();
      DoorsVisitor.logger.debug("Collected {} doors from space {}", size, id);
  }
}
