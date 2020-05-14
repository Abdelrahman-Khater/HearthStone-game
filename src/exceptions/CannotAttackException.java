package exceptions;

public class CannotAttackException extends HearthstoneException {

    public CannotAttackException(){

    }

    public CannotAttackException(String s){
        super(s);
    }

}
