public class FireAlarm extends CodeHandler {
    private String codeFireAlarm;

    public FireAlarm(String codeFireAlarm, CodeHandler nextHandler) {
        super(nextHandler);
        this.codeFireAlarm = codeFireAlarm;
    }

    @Override
    public void handleCode(String code, Door door) {
        if (!door.isLocked() && code.equals(codeFireAlarm)) {
            door.resetState();
            System.out.println("[ALARM]" + door.getId() + "!");
            System.out.println("[SURVEILLANCE] Alert sent to private station");
            door.open();
        } else {
            super.handleCode(code, door);
        }
    }
}