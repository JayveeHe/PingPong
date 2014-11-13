package UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Items.ABasicItems;

public class ItemManager {
	protected static Map<String, ABasicItems> itemMap = new HashMap<String, ABasicItems>();

	public static void addDomain(ABasicItems domain) {
		itemMap.put(domain.getKey(), domain);
	}
}
