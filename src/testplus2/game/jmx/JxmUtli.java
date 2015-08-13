package testplus2.game.jmx;

public abstract class JxmUtli {

	public static String getObjectName(Class<?> clz) {
		String name = clz.getName() + ":type=" + clz.getSimpleName();
 		return name;
	}
}
