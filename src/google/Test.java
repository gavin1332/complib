package google;

import graph.AbstractGraph.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import number.PrimeNumber;
import aov.TopologicalSort;

public class Test {

	public static void main(String[] args) {
		Set<Integer> primeSet = PrimeNumber.generate(100, 1000);
		List<List<Integer>> map = new ArrayList<List<Integer>>(100);
		for (int i = 0; i < 100; ++i) {
			map.add(new ArrayList<Integer>());
		}
		for (int value : primeSet) {
			map.get(value / 10).add(value);
		}
		Graph g = new Graph();
		for (int value : primeSet) {
			for (int prime : map.get(value % 100)) {
				g.addConnection(value, prime);
			}
		}
		int[] order = TopologicalSort.sort(g);
		System.out.println(order);
	}

}
