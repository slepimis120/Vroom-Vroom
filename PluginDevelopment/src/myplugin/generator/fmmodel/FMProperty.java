package myplugin.generator.fmmodel;

public class FMProperty extends FMElement {

    private String type;
    private String visibility;

    private Integer lower;
    private Integer upper;

    private boolean association;

    private Integer oppositeUpper;

    private boolean id;
    private String columnName;
    private String label;
    private boolean required;
    private boolean unique;
    private Integer length;
    private String fetch;

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
        this.id = false;
        this.required = false;
        this.unique = false;
        this.length = 255;
        this.fetch = "LAZY";
    }

    public FMProperty() {
        super();
        this.length = 255;
        this.fetch = "LAZY";
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

    public boolean isId() {
        return id;
    }

    public void setId(boolean id) {
        this.id = id;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getFetch() {
        return fetch;
    }

    public void setFetch(String fetch) {
        this.fetch = fetch;
    }
}