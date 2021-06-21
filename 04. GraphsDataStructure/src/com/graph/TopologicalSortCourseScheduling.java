package com.graph;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * Typically, topological sort is used to model tasks and dependencies of tasks. 
 * We need to ensure the tasks which are dependent on other have the dependencies complete first before the tasks themselves.
 * 
 * Here we look at an example of how topological sort can be used to schedule courses for students in a university.
 * 
 * Now, course scheduling involves ensuring that pre-requisites for courses are complete before the courses themselves.
 * You want to make sure that the student has the right foundation before he takes on a harder course. 
 * In this demo, we'll see how you can structure courses and prereqs in the form of a graph. 
 * And then sort courses using topological sort so that prereqs are complete before the courses.
 * 
 *
 */
public class TopologicalSortCourseScheduling extends TopologicalSort {
	
	
	public static List<String> order (List<String> courseList, Map<String, List<String>> prereqs) {
		
		Graph courseGraph = new AdjacencyListGraph(courseList.size(), Graph.GraphType.DIRECTED);
		
		Map<String, Integer> courseIdMap = new HashMap<>();
		Map<Integer, String> idCourseMap = new HashMap<>();
		
		for (int i = 0 ; i < courseList.size(); i ++) {
			courseIdMap.put(courseList.get(i), i);
			idCourseMap.put(i, courseList.get(i));
		}
		
		for (Map.Entry<String, List<String>> prereq : prereqs.entrySet()) {
			for (String course: prereq.getValue()) {
				courseGraph.addEdge(courseIdMap.get(prereq.getKey()), courseIdMap.get(course));
			}
		}
		
		//courseGraph.displayGraph();
		
		List<Integer> courseIdList = sort(courseGraph);
		List<String> courseScheduleList = new ArrayList<>();
		for (int courseId : courseIdList) {
			courseScheduleList.add(idCourseMap.get(courseId));
		}
		
		return courseScheduleList;
	}

	public static void main(String[] args) {
		
		List<String> courses = Arrays.asList(new String[] {"CS100", "CS101", "CS102", "CS103", "CS104", "CS105", "CS240"});
		
		Map<String, List<String>> prereqs = new HashMap<>();
		prereqs.put("CS100", Arrays.asList(new String[] {"CS101", "CS102", "CS103"}));
		prereqs.put("CS101", Arrays.asList(new String[] {"CS104"}));
		prereqs.put("CS103", Arrays.asList(new String[] {"CS105"}));
		prereqs.put("CS102", Arrays.asList(new String[] {"CS240"}));
		
		/**
		 * Course Schedule Graph
		 * 
		 * ("CS100") -> ("CS101") -> ("CS104")
		 * 		|_----> ("CS103") -> ("CS240")
		 * 		|_----> ("CS102") -> ("CS105")
		 * 
		 * 
		 */
		
		List<String> courseSchedule = order(courses, prereqs);
		System.out.println("Valid Schedule for CS Students : " + courseSchedule);
		// [CS100, CS101, CS102, CS103, CS104, CS240, CS105]

	}

}
