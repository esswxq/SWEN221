// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a SWEN221 assignment.
// You may not distribute it in any other way without permission.
package game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import graph.Node;
import model.Direction;

/**
 *
 * A graph where each node has up to 6 edges.
 *
 * @author Julian Mackay
 *
 * @param <V>
 *            the value stored in each node
 */
public class HexNode<V> implements Node<Direction, V> {

	private final Map<Direction, Node<Direction, V>> neighbors=new LinkedHashMap<>();
	private V v;

	public HexNode(V v) {
		this.v = v;
	}

	public HexNode() {}

	@Override
	public V getValue() {
		return v;
	}

	@Override
	public Boolean setValue(V v) {
		if (this.v != null) {return false;}
		this.v = v;
		return true;
	}

	@Override
	public Node<Direction, V> go(Direction direction) {
		if (!this.neighbors.containsKey(direction)) {return null;}
		return this.neighbors.get(direction);
	}
	
	@Override
	public Collection<Node<Direction, V>> getNeighbors(){
		return neighbors.values();
	}
	
	@Override
	public void connectNewNeighbor(Direction direction,Node<Direction, V> n) {
		//recursive method
		if(this.hasNeighbor(direction.clockwise())) {
			//has neighbor on clockwise direction
			if(!n.hasNeighbor(direction.clockwise().clockwise())) {
				// if the 3rd neighbor has not been connected, connect this neighbor
				n.add(direction.clockwise().clockwise(), this.go(direction.clockwise()));
				this.go(direction.clockwise()).add(direction.clockwise().clockwise().inverse(), n);
				// go recursively to find the 4th and 5th neighbor
				this.go(direction.clockwise()).connectNewNeighbor(direction.clockwise().clockwise().inverse(), n);
			}	
		}
		if(this.hasNeighbor(direction.antiClockwise())) {
			//has neighbor on anti-clockwise direction
			if(!n.hasNeighbor(direction.antiClockwise().antiClockwise())) {
				// if the 3rd neighbor has not been connected, connect this neighbor
				n.add(direction.antiClockwise().antiClockwise(), this.go(direction.antiClockwise()));
				this.go(direction.antiClockwise()).add(direction.antiClockwise().antiClockwise().inverse(), n);
				// go recursively to find the 4th and 5th neighbor
				this.go(direction.antiClockwise()).connectNewNeighbor(direction.antiClockwise().antiClockwise().inverse(), n);
			}		
		}
	}
	@Override
	public Boolean connect(Direction direction, Node<Direction, V> n) {
		if(neighbors.containsKey(direction)) {
			//if the node has a neighbor on that direction
			return false;
		}
		else {
			// connect this two node 
			add(direction, n);
			n.add(direction.inverse(), this);
			//after connecting this two node, check whether there is other nodes that is able to connect
			connectNewNeighbor(direction,n);

			for(Direction dir:Direction.values()) {
				// fix the neighborhood of node n in clockwise direction
				if(n.hasNeighbor(dir)&&n.hasNeighbor(dir.clockwise())&&!n.go(dir).isConnected(dir.clockwise().clockwise(), n.go(dir.clockwise()))) {
					n.go(dir).add(dir.clockwise().clockwise(), n.go(dir.clockwise()));
					 n.go(dir.clockwise()).add(dir.clockwise().clockwise().inverse(),n.go(dir));
				}
			}
			return true;
		}
	}

	@Override
	public Boolean isConnected(Direction direction, Node<Direction, V> n) {
		if(neighbors.containsKey(direction)) {
			//check the value from the map
			return neighbors.get(direction).equals(n);
		}
		else {
			//no such key, then there is no neighbor on this direction
			return false;
		}
	}

	@Override
	public Boolean add(Direction direction, Node<Direction, V> n) {
		if(neighbors.containsKey(direction)){
			//has neighbor on this direction, fail to add
			return false;
		}
		else {
			// add the neighbor
			neighbors.put(direction, n);
			return true;
		}
	}

	@Override
	public Boolean hasNeighbor(Direction dir) {
		return neighbors.containsKey(dir);
	}

	
	@Override
	public Node<Direction, V> fillNeighborhood() {
	   Stream.of(Direction.values()).forEach(d->{		// for each direction
	      if (go(d) == null){							// if there is no neighbor on that direction
	         connect(d, new HexNode<V>());				// add a new neighbor
	      }
	   });
	   return this;
	}
	
	
	@Override
	public HexNode<V> generate(Integer depth) {
	   fillNeighborhood();									// fill the neighborhood of the current node
	   depth--;
	   if (depth>1){
	      int i = depth;
	      Stream.of(Direction.values()).forEach(d -> go(d).generate(i));		// go to each neighbor of the node generate next depth
	   }
	   return this;
	}
	
	@Override
	public List<Node<Direction, V>> toList() {
		List<Node<Direction, V>> list = new ArrayList<Node<Direction, V>>();
		Node<Direction, V> topleft = null;
		for(Node<Direction, V> node:collectAll()) {
			if(node.go(Direction.NORTHWEST)==null&&node.go(Direction.NORTHEAST)==null&&node.go(Direction.WEST)==null) {
				//find the top left node
				topleft=node;
			}
		}
		if(topleft==null) {
			return list;
		}
		list.add(topleft);	//add the first element
		list = scan(list,topleft);		// scan begin
		return list;
	}
	
	public List<Node<Direction, V>> scan(List<Node<Direction, V>> list,Node<Direction, V> node) {
		Node<Direction, V> startNode = node;	// an index node to scan
		while(startNode.go(Direction.EAST)!=null) {
			// the index node has neighbor on its EAST
			list.add(startNode.go(Direction.EAST));
			//add the neighbor
			startNode=startNode.go(Direction.EAST);
			//move the index node to its east neighbor
		}
		// a row is scanned, move to the next row
		if(node.go(Direction.SOUTHWEST)!=null) {
			//move to the south-west if there is a neighbor
			node=node.go(Direction.SOUTHWEST);
			list.add(node);
			//scan the next line
			list=scan(list,node);
		}
		else if(node.go(Direction.SOUTHEAST)!=null) {
			//move to the south-east if there is a neighbor
			node=node.go(Direction.SOUTHEAST);
			list.add(node);
			//scan the next line
			list=scan(list,node);
		}
		else {
			//reach the last row of the graph
			return list;
		}
		return list;
		
	}

	@Override
	public Stream<Node<Direction, V>> stream() {
		return toList().stream();
	}
	

	
	@Override
	public Stream<Node<Direction, V>> clockwiseStream() {
		
		List<Node<Direction, V>> sortedList=toList();
		Node<Direction, V> topLeft = sortedList.get(0);
		//find the top-left node by using toList method
		
		List<Node<Direction, V>> list = new ArrayList<Node<Direction, V>>();
		list.add(topLeft);
		//begin to generate the list
		clockwise(list,topLeft,Direction.EAST);
		return list.stream();
	}
	
	public List<Node<Direction, V>> clockwise(List<Node<Direction, V>> list,Node<Direction, V> n,Direction dir){
		if(!list.contains(n)) {
			list.add(n);
		}
		if(n==null) {
			return list;
		}
		if(dir==Direction.EAST&&list.contains(n.go(Direction.EAST))) {
			//if the east neighbor of this node has already in the list, then it is the center of the graph
			if(!list.contains(n)) {
				list.add(n);
			}
			return list;
		}
		boolean hasNewNode = true;
		if(n.go(dir)==null) {
			//if the node has no neighbor on its scanning direction
			hasNewNode=false;
		}
		if(list.contains(n.go(dir))) {
			// if the neighbor on its scanning direction is in the list
			hasNewNode=false;
		}
		while(hasNewNode) {
			// add the neighbor to the list
			list.add(n.go(dir));
			//move to the next node
			n=n.go(dir);
			
			if(n.go(dir)==null) {
				hasNewNode=false;
			}
			if(list.contains(n.go(dir))) {
				hasNewNode=false;
			}
			
			
		}
		// after reach the end of this direction, change the direction
		dir=dir.clockwise();
		if(dir!=Direction.EAST) {
			//if the direction is not east, continue to scan the next part in same depth
			list=clockwise(list,n,dir);
		}
		else {
			n=n.go(dir);		//go east next depth
			// a new scan on next depth
			list=clockwise(list,n,dir);
		}
		return list;
	}

	@Override
	public String toString() {
		return v==null?"*"+this.hashCode():v.toString();
	}
	@Override
	public Boolean isValid() {
		assert this.collectAll().stream().allMatch(n->isValidOne(n));
		return true;
	}
	/**
	 * Very general algorithm to collect all the nodes of a graph
	 * @return
	 */
	private Set<Node<Direction,V>> collectAll(){
		Set<Node<Direction,V>> res=new LinkedHashSet<>();
		collectAll(this,res);
		return res;
	}
	private static<V> void collectAll(Node<Direction,V> n,Set<Node<Direction,V>> acc){
		if(n==null) {return;}
		if(acc.contains(n)) {return;}
		acc.add(n);
		Stream.of(Direction.values()).forEach(d->collectAll(n.go(d),acc));
	}

	public static<V> Boolean isValidOne(Node<Direction,V> n) {
		Predicate<Predicate<Direction>> ns=
			p->Stream.of(Direction.values())
				.filter(d->n.hasNeighbor(d))
				.allMatch(p);
		assert ns.test(d->n.go(d)!=n);
		assert ns.test(d->n.go(d).go(d.inverse())==n);
		assert ns.test(d->{
			Node<Direction, V> n1 = n.go(d);
			Node<Direction, V> n2 = n.go(d.clockwise());
			assert n1!=null;
			if(n2==null) {return true;}
			assert n1.go(d.inverse().antiClockwise())==n2:
				"surrunding pieces not well connected";
			return true;
		});
		assert ns.test(d->{
			Node<Direction, V> n1 = n.go(d);
			Node<Direction, V> n2 = n.go(d.antiClockwise());
			assert n1!=null;
			if(n2==null) {return true;}
			assert n1.go(d.inverse().clockwise())==n2;
			return true;
		});
		return true;
	}

}