// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a SWEN221 assignment.
// You may not distribute it in any other way without permission.
package swen221.connect.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import swen221.connect.core.Board;
import swen221.connect.core.Game;
import swen221.connect.core.Game.Status;
import swen221.connect.core.Board.Token;
import swen221.connect.util.Position;

public class ConnectTests {

	@Test 
	public void test_01() {
		String output = "|_|_|_|_|\n" +
						"|_|_|_|_|\n" +
						"|_|_|_|_|\n" +
						"|_|_|_|_|\n";

		Board board = new Board();

		assertEquals(output,board.toString());
	}
	
	@Test 
	public void test_02() {
		//test creating new game and clone method
		String output = "|_|_|_|_|\n" +
						"|_|_|_|_|\n" +
						"|_|_|_|_|\n" +
						"|_|_|_|_|\n";

		Board board = new Board();
		Game game = new Game(board);
		Board clone = board.clone();
		
		assertEquals(game.getMovesPlayed(), 0);
		assertEquals(output,clone.toString());
	}
	
	@Test 
	public void test_03() {
		String output = "|_|_|_|_|\n" +
						"|_|_|_|_|\n" +
						"|_|_|_|_|\n" +
						"|_|_|_|_|\n";

		Board board = new Board();
		Position position = new Position(0,0);
		board.getSquare(position);
		
		assertEquals(output,board.toString());
	}
		
	@Test 
	public void test_04() {
		//test placeTopken
		String output = "|B|_|_|_|\n" +
						"|W|_|_|_|\n" +
						"|_|_|_|_|\n" +
						"|_|_|_|_|\n";
		
		Game game=new Game(new Board());
		Token tokenB = Token.BLACK;
		Token tokenW = Token.WHITE;
		Position positionB = new Position(0,0);
		Position positionW = new Position(0,1);
		game.placeToken(positionW, tokenW);
		game.placeToken(positionB, tokenB);
		Board board = game.getBoard();
		
		assertEquals(game.getMovesPlayed(),2);
		assertEquals(output,board.toString());
	}
	

	@Test
	public void test_05() {
		assertEquals(Game.Status.STALEMATE.toString(), "STALEMATE");
		assertEquals(Game.Status.BLACKWON.toString(), "BLACKWON");
		assertEquals(Game.Status.WHITEWON.toString(), "WHITEWON");
		assertEquals(Game.Status.ONGOING.toString(), "ONGOING");
	}

	@Test
	public void Invalidput_0() {
		// test invalidput
		Game game = new Game(new Board());
		try {
			game.placeToken(new Position(0, 0), Token.BLACK);
			fail("Black should go second");
		} catch (IllegalArgumentException e) {
			// good here
		}
	}
	
	@Test
	public void Invalidput_1() {

		Game game = new Game(new Board());
		try {
			game.placeToken(new Position(-1, 0), Token.WHITE);
			fail("Out of boundary");
			game.placeToken(new Position(4, 0), Token.WHITE);
		} catch (IllegalArgumentException e) {
			// good here
		}
	}
	
	@Test
	public void Invalidput_2() {
		Game game = new Game(new Board());
		try {
			game.placeToken(new Position(4, 0), Token.WHITE);
			fail("Out of boundary");
		} catch (IllegalArgumentException e) {
			// good here
		}

	}

	@Test
	public void Invalidput_3() {
		Game game = new Game(new Board());
		try {
			game.placeToken(new Position(2, 0), Token.WHITE);
			game.placeToken(new Position(3, 0), Token.WHITE);
			fail("White cannot take the second step");
		} catch (IllegalArgumentException e) {
			// good here
		}

	}

	@Test
	public void Invalidput_4() {
		Game game = new Game(new Board());
		try {
			game.placeToken(new Position(2, 1), Token.WHITE);
			game.placeToken(new Position(2, 0), Token.BLACK);
			game.placeToken(new Position(3, 0), Token.WHITE);
			game.placeToken(new Position(1, 0), Token.BLACK);
			game.placeToken(new Position(1, 0), Token.BLACK);
			fail("Can't let black take two steps");
		} catch (IllegalArgumentException e) {
			// good here
		}

	}

	@Test
	public void Invalidput_5() {
		Game game = new Game(new Board());
		try {
			game.placeToken(new Position(2, 1), Token.WHITE);
			game.placeToken(new Position(2, 0), Token.BLACK);
			game.placeToken(new Position(3, 0), Token.WHITE);
			game.placeToken(new Position(2, 0), Token.BLACK);
			fail("Can't put the black on the place already had token");
			game.placeToken(new Position(3, 3), Token.WHITE);
		} catch (IllegalArgumentException e) {
		}
		// good here
	}
		
	@Test
	public void Invalidput_6() {
		Game game = new Game(new Board());
		try {
			game.placeToken(new Position(2, 1), Token.WHITE);
			game.placeToken(new Position(2, 1), Token.BLACK);
			fail("Can't put the black on the place already had token");
		} catch (IllegalArgumentException e) {
		}
		// good here
	}

	@Test
	public void Invalidput_7() {
		Game game = new Game(new Board());
		try {
			game.placeToken(new Position(2, 1), Token.WHITE);
			game.placeToken(new Position(2, 0), Token.BLACK);
			game.placeToken(new Position(2, 1), Token.WHITE);
			fail("Can't put the white on the place already had token");

		} catch (IllegalArgumentException e) {
			// good here
		}
	}
	
	@Test 
	public void test_09() {
		//test moves after place
		String output = "|_|W|B|_|\n" +
						"|_|_|_|_|\n" +
						"|_|_|_|_|\n" +
						"|_|_|_|_|\n";
		
		Board board = new Board();
		Game game = new Game(board);
		
		Position position1 = new Position(1,0);
		Position position2 = new Position(2,0);
		Board.Token tokenW = Token.WHITE;
		Board.Token tokenB = Token.BLACK;
		
		game.placeToken(position1, tokenW);
		game.placeToken(position2, tokenB);
		
		int moves = game.getMovesPlayed();
		
		assertEquals(output,game.getBoard().toString());
		assertEquals(2,moves);
	}
	
	//assertEquals(Game.Status.ONGOING.toString(),"ONGOING");		
	
	@Test 
	public void test_10() {
		//test IllegalArgumentException
		String output = "|_|W|B|_|\n" +
						"|_|_|_|_|\n" +
						"|_|_|_|_|\n" +
						"|_|_|_|_|\n";
		
		Board board = new Board();
		Game game = new Game(board);
		
		Position position1 = new Position(1,0);
		Position position2 = new Position(2,0);
		Board.Token tokenW = Token.WHITE;
		Board.Token tokenB = Token.BLACK;
		
		game.placeToken(position1, tokenW);
		game.placeToken(position2, tokenB);
		
		int moves = game.getMovesPlayed();
		
		assertEquals(output,game.getBoard().toString());
		assertEquals(2,moves);
	}
	
	@Test 
	public void test_11() {
		//test left moves of players
		
		Board board = new Board();
		Game game = new Game(board);
		
		Position position1 = new Position(1,0);
		Position position2 = new Position(2,0);

		
		Board.Token token1 = Board.Token.WHITE;
		Board.Token token2 = Board.Token.BLACK;

		
		game.placeToken(position1, token1);
		game.placeToken(position2, token2);

		
		int movesW = game.tokensLeft(token1);
		int movesB = game.tokensLeft(token2);
		
		assertEquals(7,movesB);
		assertEquals(7,movesW);
	}
	
	@Test public void testBlack_Won_Row() {
		   String output = "|B|B|B|B|\n" + 
				   		   "|W|W|_|_|\n" + 
				   		   "|W|_|_|_|\n" + 
				   		   "|W|_|_|_|\n";
		    Game game = new Game(new Board());
			game.placeToken(new Position(0,1), Token.WHITE);
			game.placeToken(new Position(0,0), Token.BLACK );
			game.placeToken(new Position(0,2), Token.WHITE );
			game.placeToken(new Position(1,0), Token.BLACK );
			game.placeToken(new Position(0,3), Token.WHITE );
			game.placeToken(new Position(2,0), Token.BLACK );
			game.placeToken(new Position(1,1), Token.WHITE );
			game.placeToken(new Position(3,0), Token.BLACK );
			assertEquals(Status.BLACKWON,game.getStatus());
	}

	@Test public void testBlack_Won_Col() {
		   String output =   "|B|W|W|W|\n" + 
				   			 "|B|W|_|_|\n" + 
				   			 "|B|_|_|_|\n" + 
				   			 "|B|_|_|_|\n";
		   Board board=new Board();
		    Game game = new Game(board);
			game.placeToken(new Position(1,0), Token.WHITE);
			game.placeToken(new Position(0,0), Token.BLACK );
			game.placeToken(new Position(2,0), Token.WHITE );
			game.placeToken(new Position(0,1), Token.BLACK );
			game.placeToken(new Position(1,1), Token.WHITE );
			game.placeToken(new Position(0,2), Token.BLACK );
			game.placeToken(new Position(3,0), Token.WHITE );
			game.placeToken(new Position(0,3), Token.BLACK );
			assertEquals(game.getMovesPlayed(),8);
			assertEquals(output,board.toString());
			assertTrue(game.getStatus() == Status.BLACKWON);
	}
	@Test public void testWhite_Won_Col() {
		   String output = "|W|B|B|B|\n" + 
				    	   "|W|B|_|_|\n" + 
				    	   "|W|_|_|_|\n" + 
				    	   "|W|_|_|_|\n";
		    Game game = new Game(new Board());
			game.placeToken(new Position(0,0), Token.WHITE);
			game.placeToken(new Position(1,0), Token.BLACK );
			game.placeToken(new Position(0,1), Token.WHITE );
			game.placeToken(new Position(2,0), Token.BLACK );
			game.placeToken(new Position(0,2), Token.WHITE );
			game.placeToken(new Position(3,0), Token.BLACK );
			game.placeToken(new Position(0,3), Token.WHITE );
			//game.placeToken(new Position(1,1), Token.BLACK );
			
			//assertEquals(game.getMovesPlayed(),8);
			assertTrue(game.getStatus() == Status.WHITEWON);
	}
	@Test public void testWhite_Won_Row() {
		   String output = "|W|W|W|W|\n" + 
				           "|B|B|_|_|\n" + 
				   		   "|B|_|_|_|\n" + 
				   		   "|_|_|_|_|\n";
		   Game game = new Game(new Board());
	        
			game.placeToken(new Position(0,0), Token.WHITE);
			game.placeToken(new Position(1,1), Token.BLACK );
			game.placeToken(new Position(1,0), Token.WHITE );
			game.placeToken(new Position(0,1), Token.BLACK );
			game.placeToken(new Position(2,0), Token.WHITE );
			game.placeToken(new Position(0,2), Token.BLACK );
			game.placeToken(new Position(3,0), Token.WHITE );
			
			//assertEquals(output,board.toString());
			assertTrue(game.getStatus() == Status.WHITEWON);
	}
	
	@Test
	public void test_StaleMate() {
		Board board = new Board();
		Game game = new Game(board);
		String output = "|W|W|B|W|\n" + 
						"|W|B|W|W|\n" + 
						"|B|W|B|B|\n" + 
						"|B|B|W|B|\n";
		
		game.placeToken(new Position(0, 0), Token.WHITE);
		game.placeToken(new Position(3, 3), Token.BLACK);
		game.placeToken(new Position(3, 0), Token.WHITE);
		game.placeToken(new Position(0, 3), Token.BLACK);
		game.placeToken(new Position(2, 3), Token.WHITE);
		game.placeToken(new Position(1, 1), Token.BLACK);
		game.placeToken(new Position(3, 1), Token.WHITE);
		game.placeToken(new Position(0, 2), Token.BLACK);
		game.placeToken(new Position(0, 1), Token.WHITE);
		game.placeToken(new Position(3, 2), Token.BLACK);
		game.placeToken(new Position(1, 0), Token.WHITE);
		game.placeToken(new Position(1, 3), Token.BLACK);
		game.placeToken(new Position(1, 2), Token.WHITE);
		game.placeToken(new Position(1, 1), Token.BLACK);
		game.placeToken(new Position(2, 1), Token.WHITE);
		game.placeToken(new Position(2, 2), Token.BLACK);
		// assertEquals(output,board.toString());
		assertTrue(game.getStatus() == Status.STALEMATE);
	}

}