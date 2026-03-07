public class Unlock extends CodeHandler {
    private String codeUnlock;

    public Unlock(String codeUnlock, CodeHandler nextHandler) {
        super(nextHandler);
        this.codeUnlock = codeUnlock;
    }

    @Override
    public void handleCode(String code, Door door) {
        if (door.isLocked() && code.equals(codeUnlock)) {
            door.resetState();
            System.out.println("[UNLOCK] Door " + door.getId() + " unlocked successfully");
        } else {
            super.handleCode(code, door);
        }
    }
}