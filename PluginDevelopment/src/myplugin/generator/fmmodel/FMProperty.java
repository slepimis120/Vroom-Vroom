package myplugin.generator.fmmodel;

public class FMProperty extends FMElement {

    private String type;
    private String visibility;

    // Kardinalitet ove strane veze
    private Integer lower;
    private Integer upper;

    // Da li je property deo asocijacije
    private boolean association;

    // Gornja granica suprotne strane asocijacije
    private Integer oppositeUpper;

    public FMProperty(
            String name,
            String type,
            String visibility,
            int lower,
            int upper) {

        super(name);

        this.type = type;
        this.visibility = visibility;
        this.lower = lower;
        this.upper = upper;
        this.association = false;
        this.oppositeUpper = 1;
    }

    public FMProperty() {
        super();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Integer getLower() {
        return lower;
    }

    public void setLower(Integer lower) {
        this.lower = lower;
    }

    public Integer getUpper() {
        return upper;
    }

    public void setUpper(Integer upper) {
        this.upper = upper;
    }

    public boolean isAssociation() {
        return association;
    }

    public void setAssociation(boolean association) {
        this.association = association;
    }

    public Integer getOppositeUpper() {
        return oppositeUpper;
    }

    public void setOppositeUpper(Integer oppositeUpper) {
        this.oppositeUpper = oppositeUpper;
    }
}