package data.remote;

import com.fasterxml.jackson.databind.JsonNode;
import fr.itii.apiweb.data.remote.APICaller;
import fr.itii.apiweb.data.remote.EtablissementApiCaller;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class EtablissementApiCallerTest {

    @Test
    public void testEtablissementApiCallByNomCommuneExpectsResponseNotEmpty() {
        EtablissementApiCaller apiCaller = new EtablissementApiCaller();
        JsonNode response = apiCaller.getEtablissementsByNomCommune("Beauvais", "80");
        assertEquals(80, response.size());
        System.out.println(response);
    }

    @Test
    public void testEtablissementApiCallByNomCommuneExpectsResponseEmpty() {
        EtablissementApiCaller apiCaller = new EtablissementApiCaller();
        JsonNode response = apiCaller.getEtablissementsByNomCommune("", "80");
        assertEquals(0, response.size());
    }

    @Test
    public void testEtablissementApiCallByCodeCommuneExpectsResponseNotEmpty() {
        EtablissementApiCaller apiCaller = new EtablissementApiCaller();
        JsonNode response = apiCaller.getEtablissementsByCodeCommune("60057", "80");
        assertEquals(80, response.size());
    }

    @Test
    public void testEtablissementApiCallByCodeDepartementExpectsResponseNotEmpty() {
        EtablissementApiCaller apiCaller = new EtablissementApiCaller();
        JsonNode response = apiCaller.getEtablissementsByCodeDepartement("060", "80");
        assertNotEquals(0, response.size());
    }
}
