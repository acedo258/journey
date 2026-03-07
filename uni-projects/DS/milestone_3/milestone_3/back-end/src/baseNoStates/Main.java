package baseNoStates;
// Before executing enable assertions :
// https://se-education.org/guides/tutorials/intellijUsefulSettings.html

public class Main {
  public static void main(final String[] args) {
      //DirectoryAreas.makeAreas();
      DirectoryDoors.makeDoors();
      DirectoryUserGroups.makeUserGroups();
      Clock.getInstance().start();
      new WebServer();
  }
}
