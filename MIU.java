import java.util.*;

public class MIU {

	public MIU() {
	}

	public List<String> iterativeDeepening(String goalString) {
		List<String> currentPath = new ArrayList<String>();
		for (int i = 2; i < 20; i++) {
			currentPath = depthLimitedDFS(goalString, i);
			if (currentPath.contains(goalString)) {
				return currentPath;
			}
		}
		currentPath.clear();
		return currentPath;
	}

	public List<String> depthLimitedDFS(String goalString, int limit) {
		System.out.println("Current limit = " + limit);
		List<String> currentPath = new ArrayList<String>();
		Stack<List<String>> agenda = new Stack<List<String>>();
		List<String> visited = new ArrayList<String>();
		List<String> mi = new ArrayList<String>();
		int nextCall = 0;
		mi.add("MI");
		agenda.push(mi);
		while (!agenda.isEmpty()) {
		currentPath = (agenda.pop());
		
			for (int i = 0; i<currentPath.size(); i++){
					List<String> tempPath = new ArrayList<String>();
					tempPath.addAll(currentPath);
					tempPath.remove(tempPath.get(tempPath.size()-1));
					if (tempPath.contains(currentPath.get(currentPath.size() -1))){
						currentPath=(agenda.pop());
						System.out.println("PathD");	
					}
			}
		
		if (!visited.contains(currentPath.get(currentPath.size() - 1))){
			visited.add(currentPath.get(currentPath.size() - 1));
			if (currentPath.get(currentPath.size() - 1).equals(goalString)) {
				System.out.println("DEPTH LIMITED SEARCH");
				System.out.println("The size of the current path: "
						+ currentPath.size());
				System.out.println("The size of the agenda: " + agenda.size());
				System.out.println("Number of times next state is called: "
						+ nextCall);
				return currentPath;
			}
			if (currentPath.size() < limit) {
				agenda.addAll(extendPath(currentPath));
				nextCall++;
			}
		}
		}
		currentPath.clear();
		return currentPath;
	}

	public List<String> breadthFirstSearch(String goalString) {
		Queue<List<String>> agenda = new LinkedList<List<String>>();
		List<String> currentPath = new ArrayList<String>();
		List<String> mi = new ArrayList<String>();
		List<String> visited = new ArrayList<String>();
		mi.add("MI");
		agenda.add(mi);
		int nextCall = 0;
		while (!agenda.isEmpty()) {
			currentPath = (agenda.poll());
			
			for (int i = 0; i<currentPath.size(); i++){
					List<String> tempPath = new ArrayList<String>();
					tempPath.addAll(currentPath);
					tempPath.remove(tempPath.get(tempPath.size()-1));
					if (tempPath.contains(currentPath.get(currentPath.size() -1))){
						System.out.println("PathB");	
					currentPath = (agenda.poll());					
					}
			}
			
			
			if (!visited.contains(currentPath.get(currentPath.size() - 1))){
				visited.add(currentPath.get(currentPath.size() - 1));
			
				if (currentPath.get(currentPath.size() - 1).equals(goalString)) {
					System.out.println(" HERE" + visited);
				System.out.println("BREADTH FIRST SEARCH");
				System.out.println("The size of the current path: "
						+ currentPath.size());
				System.out.println("Number of times next state is called: "
						+ nextCall);
				System.out.println("The size of the agenda: " + agenda.size());
				return currentPath;
			}
			
			if (nextCall < 100000) {
				nextCall++;
				agenda.addAll(extendPath(currentPath));
			} else {
				currentPath.clear();
				System.out
						.println("The search couldnt find the answer in the limited moves.");
				return currentPath;
			}
				}
			}
		return currentPath;
	}

	public List<List<String>> extendPath(List<String> p) {
		List<List<String>> extendPath = new ArrayList<List<String>>();
		List<String> nextState = new ArrayList<String>();

		int size = p.size();
		String extend = "";
		nextState = nextStates(p.get(size - 1));
		for (int i = 0; i < nextState.size(); i++) {
			extend = nextState.get(i);
			List<String> interPath = new ArrayList<String>(p);
			interPath.add(extend);
			extendPath.add(interPath);
		}

		return extendPath;
	}

	public List<String> nextStates(String s) {
		String U = "U";
		String III = "III";
		String UU = "UU";
		String I = "I";
		ArrayList<String> possibles = new ArrayList<String>();

		if (s.endsWith("I")) {
			String s1 = s + ("U");
			if (!possibles.contains(s1)) {
				possibles.add(s1);
			}
		}

		if (s.startsWith("M")) {
			String sub = s.substring(1);
			String s2 = s + sub;
			if (!possibles.contains(s2)) {
				possibles.add(s2);
			}
		}

		if ((s.contains("III")) && (s.length() > 3)) {
			for (int i = 0; i <= s.length() - 3; i++) {
				if ((s.substring(i, i + 3)).equals(III)) {
					String start = s.substring(0, i);
					String rest = s.substring(i + 3);
					String s3 = start + "U" + rest;
					if (!possibles.contains(s3)) {
						possibles.add(s3);
					}
				}
			}
		}

		if ((s.contains("UU")) && (s.length() > 2)) {
			for (int i = 0; i <= s.length() - 2; i++) {
				if ((s.substring(i, i + 2)).equals("UU")) {
					String begin = s.substring(0, i);
					String end = s.substring(i + 2);
					String s4 = begin + end;
					if (!possibles.contains(s4)) {
						possibles.add(s4);
					}
				}
			}
		}

		return possibles;
	}

	public static void main(String args[]) {
		MIU state = new MIU();
		boolean found = false;
		do {
			System.out.println("Please enter a string");
			Scanner scan = new Scanner(System.in);
			String s = scan.nextLine().toUpperCase();
			List<String> currentPath = new ArrayList<String>();
			List<String> currentPathTwo = new ArrayList<String>();
			List<String> currentPathThree = new ArrayList<String>();
			currentPath = state.breadthFirstSearch(s);
			currentPathTwo = state.depthLimitedDFS(s, 10);
			currentPathThree = state.iterativeDeepening(s);

			System.out.println("Breadth first: " + currentPath);
			System.out.println("Depth first: " + currentPathTwo);
			System.out.println("Iteritive Deepening" + currentPathThree);

		} while (found != true);

	}

}
