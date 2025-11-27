import java.util.*;
public class a2_csp_mapcoloring {
    


    static List<String> colors = Arrays.asList("Red", "Green", "Blue");
    static List<String> order = Arrays.asList("SA", "WA", "NT", "Q", "NSW", "V", "T");

    static Map<String, List<String>> adj = new HashMap<>();
    static Map<String, List<String>> domain = new HashMap<>();
    static Map<String, String> assignment = new LinkedHashMap<>();

    static boolean solve(int idx) {
        if (idx == order.size())
            return true;

        String region = order.get(idx);

        for (String c : domain.get(region)) {
            boolean ok = true;

            // Check consistency with neighbors
            for (String n : adj.get(region)) {
                if (assignment.containsKey(n) && assignment.get(n).equals(c)) {
                    ok = false;
                    break;
                }
            }

            if (!ok) continue;

            assignment.put(region, c);

            // Deep copy of domain (like C++ map copy with vector<string>)
            Map<String, List<String>> oldDomain = new HashMap<>();
            for (Map.Entry<String, List<String>> e : domain.entrySet()) {
                oldDomain.put(e.getKey(), new ArrayList<>(e.getValue()));
            }

            // Forward checking: remove color c from neighbors' domains
            for (String n : adj.get(region)) {
                List<String> d = domain.get(n);
                if (d != null) {
                    d.removeIf(color -> color.equals(c));
                }
            }

            if (solve(idx + 1))
                return true;

            // Backtrack
            assignment.remove(region);
            domain = oldDomain;
        }

        return false;
    }

    public static void main(String[] args) {
        adj.put("WA",  Arrays.asList("NT", "SA"));
        adj.put("NT",  Arrays.asList("WA", "SA", "Q"));
        adj.put("SA",  Arrays.asList("WA", "NT", "Q", "NSW", "V"));
        adj.put("Q",   Arrays.asList("NT", "SA", "NSW"));
        adj.put("NSW", Arrays.asList("Q", "SA", "V"));
        adj.put("V",   Arrays.asList("SA", "NSW"));
        adj.put("T",   Collections.emptyList());

        // Initialize domains
        for (String r : order) {
            domain.put(r, new ArrayList<>(colors));
        }

        if (solve(0)) {
            System.out.println("Solution:");
            for (Map.Entry<String, String> p : assignment.entrySet()) {
                System.out.println(p.getKey() + " -> " + p.getValue());
            }
        } else {
            System.out.println("No solution found.");
        }
    }
}


