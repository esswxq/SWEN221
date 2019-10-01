package swen221.cards.core;

import java.io.Serializable;

public class Card implements Comparable<Card>, Serializable {
	
	private static final long serialVersionUID = 123L;
	
	/**
	 * Represents a card suit.
	 * 
	 * @author David J. Pearce
	 *
	 */
	public enum Suit {
		HEARTS,
		CLUBS,
		DIAMONDS,
		SPADES;
	}
	
	/**
	 * Represents the different card "numbers".
	 * 
	 * @author David J. Pearce
	 *
	 */
	public enum Rank {
		TWO,
		THREE,
		FOUR,
		FIVE,
		SIX,
		SEVEN,
		EIGHT,
		NINE,
		TEN,
		JACK,
		QUEEN,
		KING,
		ACE;
	}
	
	// =======================================================
	// Card stuff
	// =======================================================
	
	private Suit suit; // HEARTS, CLUBS, DIAMONDS, SPADES
	private Rank rank; // 2 <= number <= 14 (ACE)
	
	/**
	 * Construct a card in the given suit, with a given number
	 * 
	 * @param suit
	 *            --- between 0 (HEARTS) and 3 (SPADES)
	 * @param number
	 *            --- between 2 and 14 (ACE)
	 */
	public Card(Suit suit, Rank number) {				
		this.suit = suit;
		this.rank = number;
	}

	/**
	 * Get the suit of this card, between 0 (HEARTS) and 3 (SPADES).
	 * 
	 * @return
	 */
	public Suit suit() {
		return suit;
	}

	/**
	 * Get the number of this card, between 2 and 14 (ACE).
	 * 
	 * @return
	 */
	public Rank rank() {
		return rank;
	}	
		
	private static String[] suits = { "Hearts","Clubs","Diamonds","Spades"};
	private static String[] ranks = { "2 of ", "3 of ", "4 of ",
			"5 of ", "6 of ", "7 of ", "8 of ", "9 of ", "10 of ", "Jack of ",
			"Queen of ", "King of ", "Ace of " };
	
	public String toString() {
		return ranks[rank.ordinal()] + suits[suit.ordinal()];		
	}

	
	public boolean equals(Object o) {
		if(o != null && o instanceof Card) {
			return this.rank == ((Card)o).rank && this.suit == ((Card)o).suit;
		}
		return false;		
	}
	
	public int getNumber() {
		if(this.rank.equals(Rank.TWO))    return 2;
		if(this.rank.equals(Rank.THREE))  return 3;
		if(this.rank.equals(Rank.FOUR))   return 4;
		if(this.rank.equals(Rank.FIVE))   return 5;
		if(this.rank.equals(Rank.SIX))    return 6;
		if(this.rank.equals(Rank.SEVEN))  return 7;
		if(this.rank.equals(Rank.EIGHT))  return 8;
		if(this.rank.equals(Rank.NINE))   return 9;
		if(this.rank.equals(Rank.TEN))    return 10;
		if(this.rank.equals(Rank.JACK))   return 11;
		if(this.rank.equals(Rank.QUEEN))  return 12;
		if(this.rank.equals(Rank.KING))   return 13;
		if(this.rank.equals(Rank.ACE))    return 14;
		return 0;
	}
	
	public int getSuitNumber() {
		if(this.suit.equals(Suit.HEARTS))   return 1;
		if(this.suit.equals(Suit.CLUBS))    return 2;
		if(this.suit.equals(Suit.DIAMONDS)) return 3;
		if(this.suit.equals(Suit.SPADES))   return 4;
		return 0;
	}
	
	public Card.Suit getSuit(){
		return this.suit;
	}
	
	public int hashCode() {
		return this.getSuitNumber() * 100 + this.getNumber();
	}
	
	public int uniqueCode() {
		return this.getNumber() * 10 + this.getSuitNumber();				
	}
	
	
	@Override
	public int compareTo(Card o) {
		// TODO: you need to implement this!
		if(this.hashCode() > o.hashCode()) return 1;
		if(this.hashCode() < o.hashCode()) return -1;
		return 0;
	}
 }
