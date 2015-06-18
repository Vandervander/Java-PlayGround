import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Class Foo documentation
 * This is a class to reflect on
 * @author Vandervander
 */
class Foo {
	public int i = 4;
	private int j = 9;
	private String s = "Hello";
	/** Empty constructor **/
	public Foo() {}
	/** Returns true if String s is the String stored privately **/
	public boolean bar(String s) {return this.s.equals(s);}
	/** Returns (Y/N) if String s (is/is not) the String stored privately**/
	private String method(String s) {
		if (s.equals(this.s)) {
			return "Y";
		}
		return "N";
	}
}

public class reflection{
	public static void main(String [] arg){
		//Make new Foo object, get its class object
		Foo foo = new Foo();
		Class<?> c = foo.getClass();
		System.out.print("Class Name: " + c.getName() +"\n\n");
		//Iterate over Fields
		int i = 1;
		for (Field f: c.getDeclaredFields()) {
			System.out.println(String.format("Field%1$s: ",i) + f);
			i++;
			if (!f.isAccessible()) f.setAccessible(true); //Use this filthy method to make everything accessible
			try{
				f.set(foo, 99);
				System.out.println("Value of "+f.getName()+" set to 99, success: "+f.get(foo).equals(99)+"\n");
			}
			catch (IllegalAccessException e) {System.out.println("Access Exception: "+e.getMessage()+"\n");/* This will never happen */}
			catch (IllegalArgumentException e) {System.out.println("Type Exception: "+e.getMessage()+"\n");}
		}
		//Iterate over methods
		i = 1;
		for (Method m: c.getDeclaredMethods()) {
			System.out.println(String.format("Method%1$s: ", i) + m);
			i++;
			if(!m.isAccessible()) m.setAccessible(true); //Use this filthy method to gain access
			try {
				System.out.println("Method run, result: " + m.invoke(foo, "Hello")+"\n");
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				System.out.println("Exception: " + e +"\n");
			}
		}
		//
		//!!!!!			Output Spoilers 		!!!!!
		//
		//Class Name: Foo

		//Field1: public int Foo.i
		//Value of i set to 99, success: true

		//Field2: private int Foo.j
		//Value of j set to 99, success: true

		//Field3: private java.lang.String Foo.s
		//Type Exception: Can not set java.lang.String field Foo.s to java.lang.Integer

		//Method1: private java.lang.String Foo.method(java.lang.String)
		//Method run, result: Y

		//Method2: public boolean Foo.bar(java.lang.String)
		//Method run, result: true


	}
}
