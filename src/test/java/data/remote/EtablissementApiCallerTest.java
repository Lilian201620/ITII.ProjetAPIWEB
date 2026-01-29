package data.remote;

import com.fasterxml.jackson.databind.JsonNode;
import fr.itii.apiweb.data.remote.EtablissementApiCaller;
import fr.itii.apiweb.domain.models.objet.Etablissement;
import fr.itii.apiweb.domain.models.enumlist.api.APIEtablissement;
import fr.itii.apiweb.domain.tools.JSONSerializer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void testEtablissementApiCallByCodeDepartementExpectsResponseNotEmptyWith2Numbers() {
        EtablissementApiCaller apiCaller = new EtablissementApiCaller();
        JsonNode response = apiCaller.getEtablissementsByCodeDepartement("60", "80");
        assertNotEquals(0, response.size());
    }

    @Test
    public void testFlowApiAndSErializer(){
        EtablissementApiCaller apiCaller = new EtablissementApiCaller();
        JSONSerializer serializer = new JSONSerializer();
        JsonNode commune = apiCaller.getEtablissementsByCodeCommune("60130", "10");
        assertInstanceOf(Etablissement.class, serializer.toEtablissements(commune).getFirst());
        System.out.println(serializer.toEtablissements(commune).getFirst());
    }

    @Test
    public void testGetEtablissementsNom(){
        EtablissementApiCaller apiCaller = new EtablissementApiCaller();
        JsonNode response = apiCaller.getEtablissements(APIEtablissement.NOM_COMMUNE, "Beauvais", "100");
        assertEquals(80, response.size());
        System.out.println(response);
    }

    @Test
    public void testGetEtablissementsCodeCommune(){
        EtablissementApiCaller apiCaller = new EtablissementApiCaller();
        JsonNode response = apiCaller.getEtablissements(APIEtablissement.CODE_COMMUNE, "60130", "100");
        assertEquals(1, response.size());
    }

    @Test
    public void testGetEtablissementsCodeDepartement(){
        EtablissementApiCaller apiCaller = new EtablissementApiCaller();
        JsonNode response = apiCaller.getEtablissements(APIEtablissement.CODE_DEPARTEMENT, "060", "100");
        assertNotEquals(0, response.size());
    }

    @Test
    public void testGetEtablissementsCodeDepartement2Numbers(){
        EtablissementApiCaller apiCaller = new EtablissementApiCaller();
        JsonNode response = apiCaller.getEtablissements(APIEtablissement.CODE_DEPARTEMENT, "60", "100");
        assertNotEquals(0, response.size());
    }

}
