package exceptions;

import model.cards.Card;

public class FullHandException extends HearthstoneException {

    private Card burned;

    public Card getBurned() {
		return burned;
	}

	public FullHandException(Card b){
        burned = b;
    }

    public FullHandException(String s,Card b){
        super(s);
        burned = b;
    }

}
