public class Client {
    public static void main(String[] args) {
        String codeOpen = "1234";
        String codeFireAlarm = "9999";
        String codeUnlock = "0000";

        // CHAIN 1: Full behavior
        CodeHandler chain1 = new Log(new Unlock(codeUnlock, new FireAlarm(codeFireAlarm, new Open(codeOpen, new Lock(null)))));

        Door door1 = new Door("D1", codeOpen, codeFireAlarm, codeUnlock, chain1);
        System.out.println("TEST CHAIN 1 ");
        door1.processCode("1234");   // Open
        door1.processCode("5678");   // Fail 1
        door1.processCode("8888");   // Fail 2
        door1.processCode("1111");   // Fail 3 → lock
        door1.processCode("9999");   // Alarm (blocked → ignored)
        door1.processCode("1234");   // Try open (blocked)
        door1.processCode("0000");   // Unlock
        door1.processCode("9999");   // Fire alarm → open

        // CHAIN 2: Simple (no lock)
        CodeHandler chain2 = new Log(new Open(codeOpen, null));
        Door door2 = new Door("D2", codeOpen, codeFireAlarm, codeUnlock, chain2);
        System.out.println("TEST CHAIN 2 ");
        door2.processCode("1234");   // Open
        door2.processCode("5678");   // Fail, logged only

        // CHAIN 3: With fire alarm
        CodeHandler chain3 = new Log(new FireAlarm(codeFireAlarm, new Open(codeOpen, null)));
        Door door3 = new Door("D3", codeOpen, codeFireAlarm, codeUnlock, chain3);
        System.out.println(" TEST CHAIN 3 ");
        door3.processCode("1234");   // Open
        door3.processCode("9999");   // Fire alarm
        door3.processCode("5678");   // Fail, logged

    }
}