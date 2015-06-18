import java.util.ArrayList;
import java.util.List;

/**
 * An interface to force a class to actually contain a clone() method
 * @author Dylan
 */
interface Item extends Cloneable {
	public Item clone();
	public Item clone(List<Item> parent);
}
/**
 * Sample String wrapper class
 * @author Dylan
 */
class ItemString implements Item {
	String data;
	public ItemString(String s) {data = s;}
	public String toString() {return data;}
	public Item clone() {return new ItemString(data);}
	public Item clone(List<Item> parent) {return this.clone();}
}

/**
 * A list of Items, able to be deep-cloned with its clone method
 * @author Dylan
 * @param <T>
 */
class CloneList<T extends Item> extends ArrayList<T> implements Item, List<T>{
	private static final long serialVersionUID = 1L; //To make java shut up

	@SuppressWarnings("all") //Be quiet java
	public CloneList<T> clone () {return this.clone((List)this);}
	
	@SuppressWarnings("all") //Again, quiet java
	public CloneList<T> clone (List<Item> parent) {
		CloneList<T> result = new CloneList<T>();
		for (T element: this) {
			//If it's the parent, it's very difficult to do anything
			//about it in general, so just throw a fit
			if (element==parent && parent!=this) throw new UnsupportedOperationException(
					"Parent Clone not allowed");
			//If the list contains itself on the top level,
			//add a reference to the clone instead of recursing forever
			if (element==this) {
				result.add((T)result);
				continue;
			}
			//Recurse
			T clone = (T) element.clone((List)this);
			result.add(clone);
		}
		return result;
	}
}

public class cloning {
	public static void main (String [] args) {
		//Create list, add items to it
		CloneList<Item> words = new CloneList<Item>();
		words.add(new ItemString("Yes"));
		words.add(new ItemString("Hoiwihknsf lhvLKVhLKVHKgvsgkhldvgkl"));
		words.add(words);
			//Nested items to crash the clone method
			//CloneList<Item> nest = new CloneList<Item>();nest.add(words);
			//words.add(nest);
		//Clone list, and compare
		CloneList<Item> cloneWords = (CloneList<Item>) words.clone(words);
		System.out.println(words+"\n");
		System.out.println(cloneWords+"\n");
	}
}
