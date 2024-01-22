package xx.anonymauthor.semanticsod.rolemodel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SoDMatrix {

    private final Map<SoDClass, Set<SoDClass>> mutualExclusiveSoDClasses;


    public SoDMatrix() {
        mutualExclusiveSoDClasses = new HashMap<>();
    }

    public void addConflictingSoDClasses(SoDClass soDClassA, SoDClass soDClassB) {
        mutualExclusiveSoDClasses.computeIfAbsent(soDClassA, x->new HashSet<>()).add(soDClassB);
        mutualExclusiveSoDClasses.computeIfAbsent(soDClassB, x->new HashSet<>()).add(soDClassA);
    }

    public boolean hasConflict(SoDClass soDClassA, SoDClass SoDClassB) {
        // No need for checking classes of sodClassB as we add both ways to the matrix when creating a conflict

        return mutualExclusiveSoDClasses.getOrDefault(soDClassA, new HashSet<>()).contains(SoDClassB);


    }

    public Map<SoDClass, Set<SoDClass>> getMutualExclusiveSoDClasses() {
        return mutualExclusiveSoDClasses;
    }
}
