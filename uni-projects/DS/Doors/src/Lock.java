public class Lock extends CodeHandler {
    private static final int MAX_TRIALS = 3;

    public Lock(CodeHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    public void handleCode(String code, Door door) {
        // Only lock if not already locked + trials exceeded
        if (!door.isLocked() && door.getNumTrials() >= MAX_TRIALS) {
            door.lock();
            System.out.println("[LOCK] Door " + door.getId() + " failed attempts.");
        } else {
            super.handleCode(code, door);
        }
    }
}