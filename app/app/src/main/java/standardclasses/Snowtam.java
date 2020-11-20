package standardclasses;

import java.util.HashMap;

public class Snowtam {
    private String OACI;
    private String plainCodedSnowtam;
    private HashMap<String, String> DecodedSnowtam = new HashMap<String, String>();

    public Snowtam(String codeOACI){
        this.setOACI(OACI);
    }

    public Snowtam(String codeOACI, String plainCodedSnowtam){
        this.setOACI(OACI);
    }

    public String getOACI() {
        return OACI;
    }

    public void setOACI(String OACI) {
        this.OACI = OACI;
    }

    public String getPlainCodedSnowtam() {
        return plainCodedSnowtam;
    }

    public void setPlainCodedSnowtam(String plainCodedSnowtam) {
        this.plainCodedSnowtam = plainCodedSnowtam;
    }

    public HashMap<String, String> getDecodedSnowtam() {
        return DecodedSnowtam;
    }

    public void setDecodedSnowtam(HashMap<String, String> decodedSnowtam) {
        DecodedSnowtam = decodedSnowtam;
    }
}
