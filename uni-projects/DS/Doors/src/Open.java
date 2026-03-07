public class Open extends CodeHandler {
    private String codeOpen;

    public Open(String codeOpen, CodeHandler nextHandler) {
        super(nextHandler);
        this.codeOpen = codeOpen;
    }

    @Override
    public void handleCode(String code, Door door) {
        if (!door.isLocked()) {
            if (code.equals(codeOpen)) {
                door.resetState();  // Reset four count
                door.open();
            } else {
                door.incrementNumTrials();  // Count failed open attempt
                super.handleCode(code, door);  // Continue chain
            }
        } else {
            // Door is locked → cannot open, but allow unlock/alarm
            super.handleCode(code, door);
        }
    }
}