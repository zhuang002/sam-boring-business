import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	static Scanner sc=new Scanner(System.in);
	static ArrayList<Path> history = new ArrayList<>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Coord end=initialize();
		
		char direct = sc.next().charAt(0);
		int length = sc.nextInt();
		while (direct!='q') {
			Coord start=null;
			switch (direct) {
			case 'l':
				start = new Coord(end.x-1,end.y);
				end = new Coord(end.x-length,end.y);
				break;
			case 'r':
				start = new Coord(end.x+1,end.y);
				end = new Coord(end.x+length,end.y);
				break;
			case 'u':
				start = new Coord(end.x,end.y+1);
				end = new Coord(end.x,end.y+length);
				break;
			case 'd':
				start = new Coord(end.x,end.y-1);
				end = new Coord(end.x,end.y-length);
				break;
			default:
				break;
			}
			
			/*if (start.x>0) start.x =0;
			if (start.x<-200) start.x =-200;
			if (start.y<-200) start.y = -200;
			if (start.y>200) start.y = 200;*/
			
			Path path = new Path(start,end);
			if (intersectWithHistory(path)) {
				System.out.println(end.x+" "+end.y+" DANGER");
				return;
			} else {
				System.out.println(end.x+" "+end.y+" safe");
			}
			history.add(path);
			
			
			direct = sc.next().charAt(0);
			length = sc.nextInt();
		}
		
	}

	private static boolean intersectWithHistory(Path path) {
		// TODO Auto-generated method stub
		for (Path p:history) {
			if (path.intersect(p)) {
				return true;
			}
		}
		return false;
	}

	private static Coord initialize() {
		// TODO Auto-generated method stub
		history.add(new Path(new Coord(0,-1), new Coord(0,-3)));
		history.add(new Path(new Coord(1,-3), new Coord(3,-3)));
		history.add(new Path(new Coord(3,-4), new Coord(3,-5)));
		history.add(new Path(new Coord(4,-5), new Coord(5,-5)));
		history.add(new Path(new Coord(5,-4), new Coord(5,-3)));
		history.add(new Path(new Coord(6,-3), new Coord(7,-3)));
		history.add(new Path(new Coord(7,-4), new Coord(7,-7)));
		history.add(new Path(new Coord(6,-7), new Coord(-1,-7)));
		Coord end = new Coord(-1,-5);
		history.add(new Path(new Coord(-1,-6), end));
		
		return end;
	}

}

class Path {
	 Coord start,end;
	 
	public Path(Coord start, Coord end) {
		// TODO Auto-generated constructor stub
		this.start = start;
		this.end = end;
	}

	public boolean intersect(Path p) {
		// TODO Auto-generated method stub
		if (this.isVertical()) {
			if (p.isVertical()) {
				// both are vertical;
				if (this.start.x != p.start.x) {
					return false;
				} else {
					if(isOverlap(this.start.y, this.end.y, p.start.y, p.end.y)) {
						return true;
					} else return false;
				}
			}
			else {
				if (this.verticalCrossHorizontal(p)) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			if (p.isVertical()) {
				if (this.horizontalCrossVertical(p)) {
					return true;
				}
				else return false;
			} else {
				if (this.start.y != p.start.y) {
					return false;
				}
				if (isOverlap(this.start.x, this.end.x, p.start.x, p.end.x)) {
					return true;
				} else return false;
			}
		}
		//return false; // should not reach here.
	}

	private boolean verticalCrossHorizontal(Path p) {
		// TODO Auto-generated method stub
		return p.horizontalCrossVertical(this);
	}

	private boolean horizontalCrossVertical(Path p) {
		// TODO Auto-generated method stub
		return isBetween(this.start.y, p.start.y, p.end.y) && isBetween(p.start.x, this.start.x, this.end.x);
	}

	private boolean isBetween(int x, int y1, int y2) {
		// TODO Auto-generated method stub
		int d1=y1;
		int d2=y2;
		if (y2<y1) {
			d1 = y2;
			d2 = y1;
		}
		return x>=d1 && x<=d2;
	}

	private boolean isOverlap(int d1, int d2, int d3, int d4) {
		// TODO Auto-generated method stub
		int x1=d1,x2=d2,x3=d3,x4=d4;
		
		if (d2<d1) {
			x1=d2;
			x2=d1;
		}  
		
		if (d4<d3) {
			x3 = d4;
			x4 = d3;
		}
		
		return isBetween(x1,x3,x4) || isBetween(x4, x1,x2);
		
	}

	public boolean isVertical() {
		// TODO Auto-generated method stub
		return start.x == end.x;
	}
	
}

class Coord {
	int x;
	int y;
	public Coord(int x, int y) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		
	}
	
	
}
