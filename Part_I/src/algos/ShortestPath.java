package algos;

import java.awt.Point;
import java.util.PriorityQueue;

public class ShortestPath {
    public void a_star(Point[][] parent, int[][] cost_so_far, double[][] Priority, Point start, Point goal, Point kink) {
    	PriorityQueue<Point> frontier = new PriorityQueue<Point>(new Comparator<Point>() {
    		public int compare (Point a, Point b) {
    			if (Priority[a.y][a.x] > Priority[b.y][b.x]) return 1;
    			if (Priority[a.y][a.x] < Priority[b.y][b.x]) return -1;
    			
    			return 0;
    		}
    	});
    	
    	frontier.add(start);
    	parent[start.y][start.x] = start;
    	cost_so_far[start.y][start.x] = 0;
    	
    	while (!frontier.isEmpty()) {
    		Point current = frontier.poll();
    		
    		if (current.equals(goal)) {
    			break;
    		}
    		
    		ArrayList<Point> neighbours = neighbours(parent, current);
    		
    		neighbours.forEach(neighbour -> {
    			int new_cost = cost_so_far[current.y][current.x] + 1;
    			if ((cost_so_far[neighbour.y][neighbour.x] == -1) || (new_cost < cost_so_far[neighbour.y][neighbour.x])) {
    				cost_so_far[neighbour.y][neighbour.x] = new_cost;
    				double priority = new_cost + heuristic(neighbour, goal);
    				Priority[neighbour.y][neighbour.x] = priority;
    				frontier.add(neighbour);
    				parent[neighbour.y][neighbour.x] = current;
    			}
    		});
    	}
    	
		if ((frontier.isEmpty()) && (parent[goal.y][goal.x].equals(new Point(-1, -1)) || parent[goal.y][goal.x].equals(new Point(-2, -2)))) {
			
			int height = parent.length, width = parent[0].length;
			Random r = new Random();
			int x = r.nextInt((int) (width * 0.5) + 1) + (int) (width * 0.25);
			int y = r.nextInt((int) (height * 0.5) + 1) + (int) (height * 0.25);
			
			Point move = new Point (x, y);
			
			move(move, start, kink);
			
		} else {
			Point current = goal;
			while (!parent[current.y][current.x].equals(start)) {
				current = parent[current.y][current.x];
			}

			move(current, start, kink);
		}
    	
    }
}