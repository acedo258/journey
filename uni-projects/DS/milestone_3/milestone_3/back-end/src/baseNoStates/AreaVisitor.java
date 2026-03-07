package baseNoStates;

/*
  Interface for the Visitor pattern applied to the Area hierarchy.
  Allows defining new operations on areas without modifying their classes.
  Implementations: FindAreaVisitor, SpacesVisitor, DoorsVisitor.
 */
public interface AreaVisitor {
  void visitPartition(Partition partition);

  void visitSpace(Space space);
}
