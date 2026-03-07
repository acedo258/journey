package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONArray;

/*
  Represents a space (leaf) in the building hierarchy.
  A space is a room or area that can contain doors giving access to it.
  Part of the Composite pattern as the leaf node.
 */

public class Space extends Area {
  private static final Logger logger = LoggerFactory.getLogger(Space.class);

  private final ArrayList<Door> doors;

  public Space(final String id, final String description, final Partition parent) {
    super(id, description, parent);
      this.doors = new ArrayList<>();
  }

  public void addDoor(final Door door) {
      this.doors.add(door);
    final String id1 = door.getId();
      Space.logger.debug("Added door {} to space {}", id1, this.id);
  }

   //Accepts a visitor (Visitor pattern). Spaces are leaf nodes.
  @Override
  public void accept(final AreaVisitor visitor) {
    visitor.visitSpace(this);
  }

  public ArrayList<Door> getDoors() {
    return this.doors;
  }

    @Override
    public JSONObject toJson(int depth) {
        JSONObject json = new JSONObject();
        json.put("class", "space");
        json.put("id", this.id);
        JSONArray jsonDoors = new JSONArray();
        for (Door d : this.doors) {
            jsonDoors.put(d.toJson());
        }
        json.put("access_doors", jsonDoors);
        return json;
    }

}
