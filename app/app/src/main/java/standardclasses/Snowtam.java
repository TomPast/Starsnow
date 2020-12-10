package standardclasses;

import java.io.Serializable;
import java.util.HashMap;

public class Snowtam implements Serializable {
    private String OACI;
    private String plainCodedSnowtam;
    private SnowtamDecodeObject DecodedSnowtam;

    public Snowtam(String codeOACI){
        this.setOACI(OACI);
    }

    public Snowtam(String codeOACI, String plainCodedSnowtam){
        this.setOACI(OACI);
        this.setPlainCodedSnowtam(plainCodedSnowtam);
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

    public SnowtamDecodeObject getDecodedSnowtam() {
        return DecodedSnowtam;
    }

    public void setDecodedSnowtam(SnowtamDecodeObject decodedSnowtam) {
        DecodedSnowtam = decodedSnowtam;
    }
}
