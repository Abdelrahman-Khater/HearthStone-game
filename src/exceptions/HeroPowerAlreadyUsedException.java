package exceptions;

public class HeroPowerAlreadyUsedException extends HearthstoneException {

    public HeroPowerAlreadyUsedException() {

    }

    public HeroPowerAlreadyUsedException(String s){
        super(s);
    }
}
