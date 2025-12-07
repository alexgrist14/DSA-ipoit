package by.it.group410972.stasulevich.lesson13;

import java.util.*;

public class GraphA {

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

        Map<String, Integer> inDegree = new HashMap<>();
        for (String vertex : allVertices) {
            inDegree.put(vertex, 0);
        }

        for (List<String> neighbors : graph.values()) {
            for (String neighbor : neighbors) {
                inDegree.put(neighbor, inDegree.get(neighbor) + 1);
            }
        }

        PriorityQueue<String> queue = new PriorityQueue<>();
        for (String vertex : allVertices) {
            if (inDegree.get(vertex) == 0) {
                queue.offer(vertex);
            }
        }

        List<String> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            String vertex = queue.poll();
            result.add(vertex);

            if (graph.containsKey(vertex)) {
                for (String neighbor : graph.get(vertex)) {
                    inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                    if (inDegree.get(neighbor) == 0) {
                        queue.offer(neighbor);
                    }
                }
            }
        }

        for (int i = 0; i < result.size(); i++) {
            if (i > 0) {
                System.out.print(" ");
            }
            System.out.print(result.get(i));
        }
        System.out.println();
    }
}

