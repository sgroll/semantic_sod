package xx.anonymauthor.semanticsod.rolemodel;

import java.util.Objects;

public abstract class AbstractEntity {

    private final String identifier;

    private final String displayName;

    public AbstractEntity(String identifier, String displayName)  {

        this.displayName = displayName;
        this.identifier = Objects.requireNonNull(identifier);
    }

    @Override
    public int hashCode() {
        return identifier.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof AbstractEntity other) {
            return other.getClass().equals(getClass()) && Objects.equals(other.identifier, identifier);
        }
        return false;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getDisplayName() {
        return displayName;
    }
}
