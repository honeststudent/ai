import java.util.*;

class Person {
    String name, gender;
    List<Person> parents = new ArrayList<>();

    Person(String name, String gender) {
        this.name = name;
        this.gender = gender;
    }
}

class FamilyTree {
    Map<String, Person> people = new HashMap<>();

    // Add or get a person
    Person addPerson(String name, String gender) {
        return people.computeIfAbsent(name, n -> new Person(name, gender));
    }

    // Add parent-child relation
    void addParentChild(String parentName, String parentGender,
                        String childName, String childGender) {
        Person parent = addPerson(parentName, parentGender);
        Person child = addPerson(childName, childGender);
        child.parents.add(parent);
    }

    // Get father
    List<String> getFather(String name) {
        List<String> result = new ArrayList<>();
        Person child = people.get(name);
        if (child != null) {
            for (Person p : child.parents) {
                if (p.gender.equals("male")) result.add(p.name);
            }
        }
        return result;
    }

    // Get grandmother
    List<String> getGrandmother(String name) {
        List<String> result = new ArrayList<>();
        Person child = people.get(name);
        if (child != null) {
            for (Person parent : child.parents) {
                for (Person gp : parent.parents) {
                    if (gp.gender.equals("female")) result.add(gp.name);
                }
            }
        }
        return result;
    }
}

public class a3_familyTree {
    public static void main(String[] args) {
        FamilyTree tree = new FamilyTree();

        // Add family data directly
        tree.addParentChild("John", "male", "Bob", "male");
        tree.addParentChild("Mary", "female", "Bob", "male");
        tree.addParentChild("Bob", "male", "Kate", "female");
        tree.addParentChild("Alice", "female", "Kate", "female");
        tree.addParentChild("Bob", "male", "Frank", "male");
        tree.addParentChild("Alice", "female", "Frank", "male");

        // Direct queries (no parsing strings)
        System.out.println("Father of Kate = " + tree.getFather("Kate"));
        System.out.println("Grandmother of Kate = " + tree.getGrandmother("Kate"));
        System.out.println("Father of Frank = " + tree.getFather("Frank"));
        System.out.println("Grandmother of Frank = " + tree.getGrandmother("Frank"));
    }
}
