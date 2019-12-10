package main;

import java.util.HashMap;
import java.util.Map;

public class KeyState {
	public static Map<Character, Boolean> map;
	
	public KeyState() {
		map = new HashMap<>();
		
		map.put('W', false);
		map.put('A', false);
		map.put('S', false);
		map.put('D', false);
		map.put('F', false);
		map.put('I', false);
		map.put('J', false);
		map.put('K', false);
		map.put('L', false);
		map.put('H', false);
	}
	
	public static boolean getState(Character c) {
		return map.get(c);
	}
	
	public static void setState(Character c, Boolean state) {
		map.replace(c, state);
	}
}
