package cham11ng.bmax;

/**
 * Created by sudesh chamling on 7/2/2016.
 */
public class DiseaseListAdapterClass {
    private String diseaseName;
    private String shortDefinition;
    private boolean markStatus;

    public DiseaseListAdapterClass(String diseaseName, String shortDefinition, boolean markStatus) {
        this.diseaseName = diseaseName;
        this.shortDefinition = shortDefinition;
        this.markStatus = markStatus;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getShortDefinition() {
        return shortDefinition;
    }

    public void setShortDefinition(String shortDefinition) {
        this.shortDefinition = shortDefinition;
    }

    public boolean getMarkStatus() {
        return markStatus;
    }

    public void setMarkStatus(boolean markStatus) {
        this.markStatus = markStatus;
    }
}
