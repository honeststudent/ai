import java.util.*;

public class  a2_cspNmapcoloring{

    // Variables
    static List<String> variables = Arrays.asList("A", "B", "C", "D");

    // Domains
    static Map<String, List<String>> domains = new HashMap<>();

    // Neighbors (constraints graph)
    static Map<String, List<String>> neighbors = new HashMap<>();

    public static void main(String[] args) {

        // Initialize domains
        List<String> colors = Arrays.asList("Red", "Green", "Blue");
        for (String var : variables) {
            domains.put(var, colors);
        }

        // Initialize neighbors
        neighbors.put("A", Arrays.asList("B", "C"));
        neighbors.put("B", Arrays.asList("A", "C", "D"));
        neighbors.put("C", Arrays.asList("A", "B", "D"));
        neighbors.put("D", Arrays.asList("B", "C"));

        // Empty assignment
        Map<String, String> assignment = new HashMap<>();

        Map<String, String> solution = backtrack(assignment);

        System.out.println("CSP Solution:");
        if (solution != null) {
            for (String var : variables) {
                System.out.println(var + " -> " + solution.get(var));
            }
        } else {
            System.out.println("No solution found.");
        }
    }

    // Backtracking function
    static Map<String, String> backtrack(Map<String, String> assignment) {
        // If all variables are assigned, return solution
        if (assignment.size() == variables.size()) {
            return new HashMap<>(assignment); // return a copy
        }

        // Choose an unassigned variable
        String var = null;
        for (String v : variables) {
            if (!assignment.containsKey(v)) {
                var = v;
                break;
            }
        }

        // Try all values in the domain of var
        for (String value : domains.get(var)) {
            if (isValid(assignment, var, value)) {
                assignment.put(var, value); // assign

                Map<String, String> result = backtrack(assignment);
                if (result != null) {
                    return result; // solution found
                }

                assignment.remove(var); // backtrack
            }
        }

        // No value worked → failure
        return null;
    }

    // Check if assigning (var = value) is consistent with current assignment
    static boolean isValid(Map<String, String> assignment, String var, String value) {
        for (String neighbor : neighbors.get(var)) {
            if (assignment.containsKey(neighbor) &&
                assignment.get(neighbor).equals(value)) {
                return false; // same color as neighbor → not valid
            }
        }
        return true;
    }
}