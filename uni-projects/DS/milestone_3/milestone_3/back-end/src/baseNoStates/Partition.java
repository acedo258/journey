package baseNoStates;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;

/*
  Represents a partition (composite) in the building hierarchy.
  A partition groups other areas (partitions or spaces) allowing hierarchical
  organization like floors, departments, etc.
  Part of the Composite pattern as the composite node.
 */
public class Partition extends Area {
  private static final Logger logger = LoggerFactory.getLogger(Partition.class);
  private final ArrayList<Area> areas;

  public Partition(final String id, final String description, final Partition parent) {
    super(id, description, parent);
      this.areas = new ArrayList<>();
  }

  public void addArea(final Area area) {
      this.areas.add(area);
    final String id1 = area.getId();
      Partition.logger.debug("Added area {} to partition {}", id1, this.id);
  }

  /*
    Accepts a visitor and propagates to all child areas (Visitor pattern).
   */
  @Override
  public void accept(final AreaVisitor visitor) {
    visitor.visitPartition(this);
    for (final Area area : this.areas) {
      area.accept(visitor);
    }
  }

  public ArrayList<Area> getAreas() {
    return this.areas;
  }

  public String getId() {
    return super.getId();
  }

    @Override
    public JSONObject toJson(int depth) {
        JSONObject json = new JSONObject();
        json.put("class", "partition");
        json.put("id", this.id);
        JSONArray jsonAreas = new JSONArray();
        if (depth > 0) {
            for (Area a : this.areas) {
                jsonAreas.put(a.toJson(depth - 1));
            }
            json.put("areas", jsonAreas);
        }
        return json;
    }

}