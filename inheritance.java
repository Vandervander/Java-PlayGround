/**
 * Class Foo documentation
 * @author Dylan
 *
 */
class SuperBar {
	/** Constructor with no arguments */
	public SuperBar() {this(6);}
	/** Constructor taking an int for i*/
	public SuperBar(int i) {this.i = i;}
	
	private int i;
	static int j = 42;
	
	public void setI(int i) {this.i = i;}
	public int getI() {return i;}
	public int getJ() {return j;}
}

/**
 * Class Bar documentation
 * @author Dylan
 *
 */
class Bar extends SuperBar{
	/** Constructor using superClass */
	public Bar() {super(); i = 9;}
	
	private int i;
	
	public void setJ(int j) {this.j = j;}
	public int getBarI() {return i;}
}

public class inheritance {
	public static void main(String [] arg){
		//Make new SuperBar and Bar objects
		SuperBar superBar = new SuperBar(10);
		Bar bar = new Bar();
		//i tests
		System.out.println("SuperBar.i = "+superBar.getI());
		System.out.println("Bar.getBarI = "+bar.getBarI());
		System.out.println("bar.getI = "+bar.getI()+"\n");
		//j tests
		System.out.println("Bar.getJ = "+bar.getJ()+" SuperBar.getJ = "+superBar.getJ());
		System.out.println("Use Bar to reset j to 13");
		bar.setJ(13);
		System.out.println("Bar.getJ = "+bar.getJ()+" SuperBar.getJ = "+superBar.getJ());
		
		//
		//!!!!!			Output spoilers			!!!!!
		//
		//SuperBar.i = 10
		//Bar.getBarI = 9
		//bar.getI = 6
		//Bar.getJ = 42 SuperBar.getJ = 42
		//Use Bar to reset j to 13
		//Bar.getJ = 13 SuperBar.getJ = 13
	}
}
