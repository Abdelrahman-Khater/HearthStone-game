package exceptions;

public abstract class HearthstoneException extends Exception {

    public HearthstoneException(){

    }

    public HearthstoneException(String s){
        super(s);
    }

}
