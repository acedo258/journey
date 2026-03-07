package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;


/*
 Visitor implementation to collect all spaces within an area.
  Used to get all leaf spaces under a partition for authorization checks.
 */

public class SpacesVisitor implements AreaVisitor {
  private static final Logger logger = LoggerFactory.getLogger(SpacesVisitor.class);
  private final ArrayList<Space> spaces = new ArrayList<>();

  public ArrayList<Space> getSpaces() {
    return this.spaces;
  }

  @Override
  public void visitPartition(final Partition partition) {
    // Partitions are not collected, only traversed
    final String id = partition.getId();
      SpacesVisitor.logger.debug("Visiting partition: {}", id);
  }

  @Override
  public void visitSpace(final Space space) {
      this.spaces.add(space);
  }
}