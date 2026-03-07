public abstract class CodeHandler {
    protected CodeHandler nextHandler;

    public CodeHandler(CodeHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    //pass to next handler
    public void handleCode(String code, Door door) {
        if (nextHandler != null) {
            nextHandler.handleCode(code, door);
        }
    }
}