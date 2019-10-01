// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a SWEN221 assignment.
// You may not distribute it in any other way without permission.
package game;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import graph.Node;
import model.Direction;
import model.Location;
import model.Resource;
import model.ResourceCounter;
import model.Road;
import model.Settlement;
import model.Tile;

/**
 * The main Tile class
 *
 * @author Julian Mackay
 *
 */
public class CatanTile implements Tile{

	private final Integer id;
	private final Map<Location, Settlement> settlements;
	private final Map<Direction, Road> roads;
	private final Node<Direction, Tile> currentNode;
	private Resource resource;
	private ResourceCounter resourceCounter;

	public CatanTile(Integer id, Node<Direction, Tile> currentNode) {
		this.id = id;
		this.settlements = new LinkedHashMap<>();
		this.roads = new LinkedHashMap<>();
		this.currentNode = currentNode;
	}

	@Override
	public Integer getID() {
		return this.id;
	}

	@Override
	public Boolean setResource(Resource r) {
		resource=r;
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	@Override
	public Resource getResource() {
		return this.resource;
	}

	@Override
	public Integer getResourceNumber() {
		return resourceCounter.getNumber();
	}

	public  Map<Location, Settlement> getSettlementsMap() {
		return settlements;
	}
	
	@Override
	public Boolean setResourceCounter(ResourceCounter resourceCounter) {
		this.resourceCounter=resourceCounter;
		return true;
	}

	@Override
	public Boolean hasSettlement(Location loc) {
		return settlements.containsKey(loc);
	}

	@Override
	public Boolean addSettlement(Settlement s, Location loc) {
		if(hasAdjacentSettlement(s, loc)) return false;		// check if adjacent tile has
		if(settlements.containsKey(loc)){
			//if the location already has a settlement
			return false;
		}
		else {
			//add a settlement to the location
			settlements.put(loc, s);
			addToAdjacentTiles(s,loc);
			return true;
		}
	}
	
	private void addToAdjacentTiles(Settlement s, Location loc){
		if(currentNode.go(loc.clockwiseDirection()) != null) {	
			Tile tile1 = currentNode.go(loc.clockwiseDirection()).getValue();// get the clockwise adjacent node
			((CatanTile) tile1).getSettlementsMap().put(loc.clockwise(), s);// add settlement to this tile using correct directioning.

		}
		if(currentNode.go(loc.antiClockwiseDirection()) != null) {
			Tile tile2 = currentNode.go(loc.antiClockwiseDirection()).getValue();// get the anti-clockwise adjacent node
			((CatanTile) tile2).getSettlementsMap().put(loc.antiClockwise(), s);// add settlement to this tile using correct directioning.
		}
	}
	
	private boolean hasAdjacentSettlement(Settlement s, Location loc) {
		if(currentNode.go(loc.clockwiseDirection()) != null ) {
			if(currentNode.go(loc.clockwiseDirection()).getValue().hasSettlement(loc.antiClockwise())) {
				return ((CatanTile)currentNode.go(loc.clockwiseDirection()).getValue()).getSettlementsMap().get(loc.antiClockwise()).getPlayer() != s.getPlayer();
			} // check if this adjacent settlement exist
		}
		else {
			if(hasSettlement(loc.clockwise())) {
				return settlements.get(loc.clockwise()).getPlayer() != s.getPlayer();
			}
			if(hasSettlement(loc.antiClockwise())) {
				return settlements.get(loc.antiClockwise()).getPlayer() != s.getPlayer();
			}	
		}
		return false;	
	}

	
	@Override
	public Boolean hasRoad(Direction dir) {
		return roads.containsKey(dir);
	}

	@Override
	public Boolean addRoad(Road r, Direction dir) {
		if(roads.containsKey(dir)){
			// if there already is a road 
			return false;
		}
		else {
			// add the road
			roads.put(dir, r);
			return true;
		}
	}

	@Override
	public List<Settlement> getSettlements(){
		return new ArrayList<Settlement>(settlements.values());
	}

	@Override
	public String toString() {
		return this.id.toString();
	}

}