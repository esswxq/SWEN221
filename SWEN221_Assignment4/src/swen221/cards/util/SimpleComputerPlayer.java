 package swen221.cards.util;

import java.util.List;
import java.util.Set;

import swen221.cards.core.Card;
import swen221.cards.core.Hand;
import swen221.cards.core.Player;
import swen221.cards.core.Player.Direction;
import swen221.cards.core.Trick;

/**
 * Implements a simple computer player who plays the highest card available when
 * the trick can still be won, otherwise discards the lowest card available. In
 * the special case that the player must win the trick (i.e. this is the last
 * card in the trick), then the player conservatively plays the least card
 * needed to win.
 * 
 * @author David J. Pearce
 * 
 */
public class SimpleComputerPlayer extends AbstractComputerPlayer {

	public SimpleComputerPlayer(Player player) {
		super(player);
	}

	@Override
	public Card getNextCard(Trick trick) {		
		// TODO: you need to implement this!		
		//Card.Suit leaderSuit = trick.getCardsPlayed().get(0).getSuit();
		
		//Player.Direction leadDir = trick.getLeadPlayer();
		Card.Suit trump = trick.getTrumps();
		Set<Card> cardsInHand = player.getHand().getCards();
		List<Card> cardPlayed = trick.getCardsPlayed();
		
		
		//lead player: pick the best card
		if(cardPlayed.size() == 0) {
			//has trump card
			if(!player.getHand().matches(trump).isEmpty()) {
				Set<Card> trumps = player.getHand().matches(trump);
				Card out = null;
				for(Card card : trumps) {
					if(out == null) out = card;
					else if(out.getSuitNumber() < card.getSuitNumber()) {
						out = card;
					}						
				}
				return out;
			}
			//no trump card
			else {
				Card out = null;
				for(Card card : cardsInHand) {
					if(out == null)  out = card;
					else if(out.uniqueCode() < card.uniqueCode()) {
						out = card;
					}
				}
				return out;
			}
		}
		
		//find the card need to beat
		Card cardToBeat = null;
		for(Card card : cardPlayed) {
			//if card is the first card
			if(cardToBeat == null) cardToBeat = card;
			//card is same suit with card to beat
			else if(card.getSuit() == cardToBeat.getSuit() && card.uniqueCode() > cardToBeat.uniqueCode()){
				cardToBeat = card;
			}
			//card has different suit with card to beat but card must be trump
			else if(card.getSuit() != cardToBeat.getSuit() && card.getSuit() == trump) {
				cardToBeat = card;
			}
		}
		
		
		Card.Suit leaderSuit = cardPlayed.get(0).getSuit();   //get leader suit
		
		//last player
		if(cardPlayed.size() == 3) {
			//must follow suit
			if(!player.getHand().matches(leaderSuit).isEmpty()) {
				Card best = null;
				Card lowest = null;
				for(Card card : player.getHand().matches(leaderSuit)) {
					//find the card which is just over card to beat
					if(best == null && card.uniqueCode() > cardToBeat.uniqueCode()) best = card;
					else if (best != null) {
						if(card.uniqueCode() > cardToBeat.uniqueCode() && best.uniqueCode() > card.uniqueCode()) {
							best = card;
						}
					}
					if(lowest == null) lowest = card;
					else {
						if(card.uniqueCode() < lowest.uniqueCode()) {
							lowest = card;
						}
					}					
				}
				if(best == null) return lowest;
				else return best;
			}
			//cannot follow suit
			else {
				if(cardToBeat.getSuit() == trump) {
					if(!player.getHand().matches(trump).isEmpty()) {
						Card best = null;
						for(Card card : player.getHand().matches(trump)) {
							if(best == null && card.uniqueCode() > cardToBeat.uniqueCode()) best = card;
							else if(best != null){
								if(card.uniqueCode() > cardToBeat.uniqueCode() && card.uniqueCode() < best.uniqueCode()) {
									best = card;
								}
							}
						}
						if(best != null) return best;
					}
					Card worst = null;
					for(Card card : cardsInHand) {
						if(worst == null && card.getSuit() != trump) worst = card;
						else if (worst != null) {
							if(worst.uniqueCode() > card.uniqueCode() && card.getSuit() != trump) {
								worst = card;
							}
						}
					}
					return worst;
				}
				else {
					//has trump cards
					if(!player.getHand().matches(trump).isEmpty()) {
						Card best = null;
						for(Card card : player.getHand().matches(trump)) {
							if(best == null) best = card;
							else if(card.uniqueCode() < best.uniqueCode()){
									best = card;
								}
							}
						return best;
					}
					//has no trump card
					else {
						Card worst = null;
						for(Card card : cardsInHand) {
							if(worst == null && card.getSuit() != trump) worst = card;
							else if (worst != null) {
								if(worst.uniqueCode() > card.uniqueCode() && card.getSuit() != trump) {
									worst = card;
								}
							}
						}
						return worst;
					}
				}
			}	
		}
		
		//middle player
		
		
		
		
		
		
		
		
		
		

			
		return null;
	}	
}
