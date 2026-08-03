public class UsingMap{
	public static void main(String[] args){
		// create a new HashMap
		Map<Integer, String> map = new HashMap<>();

		//put (key, value) - insert key-value pairs into the map

		map.put(101, "Frank john");
		map.put(102, "Henry Clinton");
		map.put(103, " Kate Benson");
		map.put(104, "Lucy Gerry");
		map.put(105, "Jhonny Victor");
		map.put(106, "Kate Benson");
		map.put(107, "Nathan Zoe");
		

		// get(object key) - return the value associated with the specified key
		String value = map.get(102);
		System.out.println("The value for key 102: " + value);

		// Remove(object key) - remove the key-value pair associated with the specified key
		map.remove(106);

		// containsKey(object key) - check if the map contains the specified key
		boolean hasApple = map.containsKey(106);
		System.out.println("The map contains key 106: " + hasApple);

		//KeySet() - return a set view of the keys contained in the map
		Set<Integer> keys = map.keySet();
		System.out.println("Keys in the map: " + keys);

		
	}
}