package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import org.json.JSONObject;

/*
 Abstract base class representing an area in the building hierarchy.
 Part of the Composite pattern: Area is the component, Partition is the composite,
 and Space is the leaf. Also implements the Visitor pattern through accept() method
 to allow traversal operations without modifying the class hierarchy.
 */
public abstract class Area {
  private static final Logger logger = LoggerFactory.getLogger(Area.class);

  protected String id;
  protected String description;
  protected Partition parent;

  public Area(String id, String description, Partition parent) {
      this.id = id;
      this.description = description;
      this.parent = parent;
      if (null != parent) {
          parent.addArea(this);
      }
      Class<? extends Area> aClass = getClass();
      final String simpleName = aClass.getSimpleName();
      logger.debug("Created area: {} ({})", id, simpleName);
  }

  public String getId() {
    return this.id;
  }

  public Partition getParent() {
    return this.parent;
  }


   //Returns all doors giving access to spaces within this area.
   // Uses the Visitor pattern via DoorsVisitor.

  public final ArrayList<Door> getDoorsGivingAccess() {
    final DoorsVisitor visitor = new DoorsVisitor();
      this.accept(visitor);
    return visitor.getDoors();
  }


  public final Area findAreaById(final String targetId) {
    final FindAreaVisitor visitor = new FindAreaVisitor(targetId);
      this.accept(visitor);
    return visitor.getFound();
  }


   //Returns all spaces within this area.
   // Uses the Visitor pattern via SpacesVisitor.

  public final ArrayList<Space> getSpaces() {
    final SpacesVisitor visitor = new SpacesVisitor();
      this.accept(visitor);
    return visitor.getSpaces();
  }

    // Declaración abstracta de toJson
    public abstract JSONObject toJson(int depth);

  //Accepts a visitor for tree traversal (Visitor pattern).

  public abstract void accept(AreaVisitor visitor);
}
