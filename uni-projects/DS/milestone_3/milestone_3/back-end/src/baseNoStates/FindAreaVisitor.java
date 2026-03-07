package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
  Visitor implementation to find an area by its ID.
  Traverses the area tree and stores the matching area if found.
 */
public class FindAreaVisitor implements AreaVisitor {
  private static final Logger logger = LoggerFactory.getLogger(FindAreaVisitor.class);

  private final String targetId;
  private Area found;

  public FindAreaVisitor(final String id) {
      super();
      this.targetId = id;
  }

  public Area getFound() {
    return this.found;
  }

  @Override
  public void visitPartition(final Partition partition) {
    final String id = partition.getId();
    if (id.equals(this.targetId)) {
        this.found = partition;
        FindAreaVisitor.logger.debug("Found partition: {}", this.targetId);
    }
  }

  @Override
  public void visitSpace(final Space space) {
    final String id = space.getId();
    if (id.equals(this.targetId)) {
        this.found = space;
        FindAreaVisitor.logger.debug("Found space: {}", this.targetId);
    }
  }
}
