package xx.anonymauthor.semanticsod.rolemodel;

public class SoDClass extends AbstractEntity {

    public static SoDClass neutral = new SoDClass("Neutral", "Neutral SoD Class");
    public static SoDClass invalid = new SoDClass("Violation", "Violation of homogeneity");

    public SoDClass(String identifier, String displayName) {
        super(identifier, displayName);
    }

    @Override
    public String toString() {
        return getIdentifier();
    }

}
