public class Door {
    private String id;
    private boolean locked;
    private int numTrials;
    private final String codeOpen;
    private final String codeFireAlarm;
    private final String codeUnlock;
    private CodeHandler codeHandler;

    public Door(String id, String codeOpen, String codeFireAlarm, String codeUnlock, CodeHandler codeHandler) {
        this.id = id;
        this.codeOpen = codeOpen;
        this.codeFireAlarm = codeFireAlarm;
        this.codeUnlock = codeUnlock;
        this.codeHandler = codeHandler;
        this.locked = false;
        this.numTrials = 0;
    }

    //getters
    public String getId() { return id; }
    public boolean isLocked() { return locked; }
    public int getNumTrials() { return numTrials; }
    public String getCodeOpen() { return codeOpen; }
    public String getCodeUnlock() { return codeUnlock; }
    public String getCodeFireAlarm() { return codeFireAlarm; }

    // actions
    public void open() { System.out.println("[OPEN] Door " + id + " opened"); }
    public void lock() { locked = true; System.out.println("[LOCK] Door " + id + " locked"); }
    public void unlock() { locked = false; numTrials = 0; System.out.println("[UNLOCK] Door " + id + " unlocked"); }
    public void incrementNumTrials() { numTrials++; }
    public void resetState() { numTrials = 0; locked = false; }

    // Runtime
    public void setCodeHandler(CodeHandler handler) { this.codeHandler = handler; }

    // 4 digit numeric codes → validate here before processing
    public void processCode(String code) {
        if (code == null || !code.matches("\\d{4}")) {
            System.out.println("[ERROR] Invalid code for door " + id + ": must be 4 digits.");
            return;
        }
        codeHandler.handleCode(code, this);  // Delegate to chain
    }
}