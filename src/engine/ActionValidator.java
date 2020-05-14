package engine;

import exceptions.*;
import model.heroes.*;
import model.cards.minions.*;
import model.cards.*;
public interface ActionValidator {
	public void validateTurn(Hero user) throws NotYourTurnException;
	public void validateAttack(Minion attacker,Minion target) throws
	CannotAttackException, NotSummonedException, TauntBypassException,
	InvalidTargetException;
	public void validateAttack(Minion attacker,Hero target) throws
	CannotAttackException, NotSummonedException, TauntBypassException,
	InvalidTargetException;
	public void validateManaCost(Card card) throws NotEnoughManaException;
	public void validatePlayingMinion(Minion minion) throws FullFieldException;
	public void validateUsingHeroPower(Hero hero) throws NotEnoughManaException,
	HeroPowerAlreadyUsedException;
}
