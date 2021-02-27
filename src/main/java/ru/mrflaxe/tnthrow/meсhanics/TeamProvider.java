package ru.mrflaxe.tnthrow.meсhanics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TeamProvider {

	private final HashMap<String, List<String>> teams;
	
	public TeamProvider() {
		teams = new HashMap<>();
		
		teams.put("red", new ArrayList<>());
		teams.put("blue", new ArrayList<>());
		teams.put("green", new ArrayList<>());
		teams.put("yellow", new ArrayList<>());
	}
	
	public void setTeam(String name, String team) {
		teams.get(team).add(name);
	}
	
	public String getTeam(String name) {
		if(teams.get("red").contains(name)) return "red";
		if(teams.get("blue").contains(name)) return "blue";
		if(teams.get("yellow").contains(name)) return "yellow";
		return "green";
	}
}
