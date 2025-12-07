package by.it.group410972.stasulevich.lesson13;

import java.util.*;

public class GraphB {

    private static boolean hasCycle(Map<String, List<String>> graph, Set<String> allVertices) {
        Map<String, Integer> color = new HashMap<>();
        for (String vertex : allVertices) {
            color.put(vertex, 0);
        }

        for (String vertex : allVertices) {
            if (color.get(vertex) == 0) {
                if (dfs(vertex, graph, color)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean dfs(String vertex, Map<String, List<String>> graph, Map<String, Integer> color) {
        color.put(vertex, 1);

        if (graph.containsKey(vertex)) {
            for (String neighbor : graph.get(vertex)) {
                if (color.get(neighbor) == 1) {
                    return true;
                }
                if (color.get(neighbor) == 0 && dfs(neighbor, graph, color)) {
                    return true;
                }
            }
        }

        color.put(vertex, 2);
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        scanner.close();

        Map<String, List<String>> graph = new HashMap<>();
        Set<String> allVertices = new HashSet<>();

        String[] edges = input.split(", ");
        for (String edge : edges) {
            String[] parts = edge.split(" -> ");
            String from = parts[0].trim();
            String to = parts[1].trim();
            allVertices.add(from);
            allVertices.add(to);
            graph.putIfAbsent(from, new ArrayList<>());
            graph.get(from).add(to);
        }

        boolean cycle = hasCycle(graph, allVertices);
        System.out.println(cycle ? "yes" : "no");
    }
}

