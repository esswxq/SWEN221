// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a SWEN221 assignment.
// You may not distribute it in any other way without permission.
package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import graph.Graph;
import graph.Node;
import model.Tile;
import model.Direction;
import model.Game;
import model.Location;
import model.Player;
import model.Resource;
import model.ResourceCounter;
import model.Road;
import model.Settlement;
import model.SettlementType;

/**
 * CatanGame class. Controller for the game Settlers of Catan.
 * @author Julian Mackay
 *
 */
public class CatanGame implements Game<Tile> {

	private Node<Direction, Tile> middle;
	private final List<Player> players;
	private final Map<Integer, Tile> tiles;

	public CatanGame(Integer depth) {
		this.tiles = new HashMap<>();
		this.middle = new HexNode<>();
		middle.generate(depth);
		AtomicInteger id = new AtomicInteger(0);
		// Sanity check case where stream is not yet implemented.
		if(middle.stream() != null) {
			middle.stream().forEach(n -> {
				this.tiles.put(id.get(), new CatanTile(id.get(), n));
				n.setValue(tiles.get(id.getAndIncrement()));
			});
		}
		this.players = new ArrayList<>();

		// initialise all players
		IntStream.range(0, 3).forEachOrdered(n -> {
			this.players.add(new Player(n));
		});
	}

	@Override
	public Stream<Node<Direction, Tile>> stream() {
		return middle.stream();
	}

	@Override
	public Stream<Node<Direction, Tile>> clockwiseStream() {
		return middle.clockwiseStream();
	}

	public Stream<Integer> streamIDs(Stream<Node<Direction, Tile>> stream) {
		return stream.map(n -> n.getValue().getID());
	}

	@Override
	public void setResources(List<Resource> pool) {
		this.middle.stream().forEach(n -> n.getValue().setResource(pool.remove(0)));
	}

	@Override
	public Resource getResource(Integer id) {
		return this.tiles.get(id).getResource();
	}
	
	@Override
	public void setResourceCounters(List<ResourceCounter> pool) {
		List<Node<Direction, Tile>> clockwiseList =new ArrayList<Node<Direction, Tile>>();
		this.middle.clockwiseStream().forEach(n -> clockwiseList.add(n));		//get the clockwise list
		int j=0;		//index of pool
		for(int i=0;i<clockwiseList.size();i++) {
			if(clockwiseList.get(i).getValue().getResource()!=Resource.DESERT) {
				//set the resource from pool
				clockwiseList.get(i).getValue().setResourceCounter(pool.get(j));
			}
			else {
				//skip the desert tile
				clockwiseList.get(i).getValue().setResourceCounter(ResourceCounter.NONE);
				j--;
			}
		j++;
		}
	}

	@Override
	public List<Player> getPlayers() {
		return this.players;
	}

	@Override
	public void distributeResources(Integer diceRoll) {
		if(diceRoll<2||diceRoll>12) {
			//check the dice number is correct
			return;
		}

		List<Tile> list = new ArrayList<Tile>();
		for(Tile t:tiles.values()) {
			if(t.getResourceNumber()==diceRoll) {
				//find the tile that match the diceRoll
				list.add(t);
			}
		}
		System.out.println(list);
		
		for(Tile t:list) {
			System.out.println(t.getSettlements());
			for(Settlement s:t.getSettlements()) {
				System.out.println(s.getType());
				//all settlements in this tile
				if(s==null) {
					continue;
				}
				if(s.getType()==SettlementType.TOWN) {
					// if the settlement is a town, add 1 resource to the owner
					s.getPlayer().addResource(t.getResource(), 1);
				}
				else if(s.getType()==SettlementType.CITY) {
					// if the settlement is a city, add 2 resource to the owner
					s.getPlayer().addResource(t.getResource(), 2);
				}
			}
		}
	}

	@Override
	public String toResourceString() {
		return toString(stream(), v -> v.getResource().toString());
	}

	@Override
	public void addRoad(Node<Direction, Tile> n, Road r, Direction dir) {
		n.getValue().addRoad(r, dir);
	}

	@Override
	public Boolean upgradeSettlement(Settlement s) {
		return s.upgrade();
	}

	@Override
	public String toResourceCounterString() {
		return toString(stream(), v -> v.getResourceNumber().toString());
	}

	@Override
	public void addSettlement(Node<Direction, Tile> n, Location loc, Settlement s) {
		n.getValue().addSettlement(s, loc);
	}

	@Override
	public String toString() {
		StringBuffer res = new StringBuffer("");
		stream().map(n -> n.getValue().toString()).forEach(s -> res.append("  " + pad(s)));

		return res.toString().trim();
	}

	private String pad(String s) {
		if (s.length() < 2) {
			return pad(s + " ");
		} else {
			return s;
		}
	}

	/**
	 * Richer toString that allows an order (streamer) and a mapping to be specified in the case of complex nodes
	 * @param streamer Function used to construct a stream from this graph
	 * @param mapper Function used to construct a string from the value contained within each node.
	 * @return
	 */
	public static String toString(Stream<Node<Direction, Tile>> stream, Function<Tile, String> mapper) {
		StringBuffer res = new StringBuffer("");
		stream.map(n -> mapper.apply(n.getValue())).forEach(s -> res.append("  " + newpad(s)));
		return res.toString().trim();
	}
	
	private static String newpad(String s) {
		if (s.length() < 2) {
			return newpad(s + " ");
		} else {
			return s;
		}
	}

}