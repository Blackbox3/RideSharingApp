package com.example.ridesharing.model;

import org.osmdroid.util.GeoPoint;

import java.util.*;

public class Graph {
    private Map<Node, List<Edge>> adjacencyList;

    public Graph() {
        adjacencyList = new HashMap<>();
    }

    // Add node to graph
    public void addNode(Node node) {
        adjacencyList.putIfAbsent(node, new ArrayList<>());
    }

    // Add edge to graph (a road between two locations)
    public void addEdge(Node source, Node destination, double weight) {
        adjacencyList.get(source).add(new Edge(destination, weight));
        adjacencyList.get(destination).add(new Edge(source, weight)); // For undirected graph
    }

    // Dijkstra's algorithm to find the shortest path
    public List<Node> dijkstra(Node start, Node target) {
        Map<Node, Double> distances = new HashMap<>();
        Map<Node, Node> previousNodes = new HashMap<>();
        PriorityQueue<NodeDistance> pq = new PriorityQueue<>(Comparator.comparingDouble(nd -> nd.distance));

        // Initialize distances
        for (Node node : adjacencyList.keySet()) {
            distances.put(node, Double.MAX_VALUE);
            previousNodes.put(node, null);
        }
        distances.put(start, 0.0);
        pq.add(new NodeDistance(start, 0.0));

        while (!pq.isEmpty()) {
            NodeDistance currentNodeDist = pq.poll();
            Node currentNode = currentNodeDist.node;

            // If we reach the target node, stop the algorithm
            if (currentNode.equals(target)) {
                break;
            }

            // Relax edges
            for (Edge edge : adjacencyList.get(currentNode)) {
                double newDist = distances.get(currentNode) + edge.weight;
                if (newDist < distances.get(edge.destination)) {
                    distances.put(edge.destination, newDist);
                    previousNodes.put(edge.destination, currentNode);
                    pq.add(new NodeDistance(edge.destination, newDist));
                }
            }
        }

        // Reconstruct the shortest path
        List<Node> path = new ArrayList<>();
        for (Node at = target; at != null; at = previousNodes.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

    // Method to get the shortest path between two nodes (just a wrapper for dijkstra)
    public List<Node> getShortestPath(Node start, Node target) {
        return dijkstra(start, target);
    }

    // Class for the edges (paths between nodes)
    private static class Edge {
        Node destination;
        double weight;

        public Edge(Node destination, double weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }

    // Class for nodes (locations)
    public static class Node {
        private final GeoPoint geoPoint;
        private final String name;

        public Node(String name, GeoPoint geoPoint) {
            this.name = name;
            this.geoPoint = geoPoint;
        }

        public GeoPoint getGeoPoint() {
            return geoPoint;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Node node = (Node) obj;
            return geoPoint.equals(node.geoPoint);
        }

        @Override
        public int hashCode() {
            return geoPoint.hashCode();
        }
    }

    // Class to store node and its distance for the priority queue
    private static class NodeDistance {
        Node node;
        double distance;

        public NodeDistance(Node node, double distance) {
            this.node = node;
            this.distance = distance;
        }
    }

    // Method to calculate distance between two GeoPoints
    public static double calculateDistance(GeoPoint point1, GeoPoint point2) {
        double lat1 = Math.toRadians(point1.getLatitude());
        double lon1 = Math.toRadians(point1.getLongitude());
        double lat2 = Math.toRadians(point2.getLatitude());
        double lon2 = Math.toRadians(point2.getLongitude());

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        // Using Haversine formula for distance calculation
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(lat1) * Math.cos(lat2) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double radius = 6371; // Earth radius in kilometers

        return radius * c; // Distance in kilometers
    }

    // Method to add a road between two locations
    public void addRoad(Node start, Node end) {
        // Calculate distance (weight) between the two nodes using the geodesic distance (Haversine)
        double distance = calculateDistance(start.getGeoPoint(), end.getGeoPoint());
        addEdge(start, end, distance); // Add an edge with calculated weight
    }
}
