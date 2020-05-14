Concerning AI
we implemented Game Commands in order to make the AI interact with the game easily
Game Commands

PlayMinion + minion index
AttackMinion + attacker index + target index
CastSpell + spell index
useHeroPower + Target(minion or hero)

** Game Commands can be used also in Making the game playable over internet as it captures the moves of each player as a String that could be sent online
	but we didn't Complete the Networking part as we know much about Networking yet.
1- we first used Monte Carlo Tree Search(Mcts) along with optimizing possible moves by Scoring them using an evaluation function
2- we then tried Minmax Algorithm and found that minmax is performing better than Mcts and it is also optimized using the evaluation function we talked about
** we made a button for the AI to Perform the next move as for the user to be able to realize what the AI played,otherwise the AI would perform all the moves very fast.

Concerning the Graphics
we tried to design our own style for the game making it different from the original game in design,
all the buttons are fully functional with graphics ,we implemented and drawn by us
we made most of the functions like the real game , as for example to make an attack just click on the attacker then the target
and we 've made a video tutorial about how to play the game
we added also some sounds from the original game

we hope you enjoyed the Game ,the Graphics and the AI part