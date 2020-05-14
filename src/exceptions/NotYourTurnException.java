package exceptions;

public class NotYourTurnException extends HearthstoneException {
    public NotYourTurnException() {
    }

    public NotYourTurnException(String s) {
        super(s);
    }
}
