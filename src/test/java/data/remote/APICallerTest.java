package data.remote;

import com.fasterxml.jackson.databind.JsonNode;
import fr.itii.apiweb.data.remote.APICaller;
import fr.itii.apiweb.domain.models.api_models.CommunesFieldsEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class APICallerTest {

    // Test sur byName avec des resultats
    @Test
    public void testApiCallByNameExpectsResponseNotEmpty() {
        APICaller apiCaller = new APICaller();
        JsonNode response = apiCaller.getCommunesByName("paris", "1000");
        assertNotEquals(0, response.size());
    }

    // Test sur byName sans resultats
    @Test
    public void testApiCallByNameExpectsEmpty() {
        APICaller apiCaller = new APICaller();
        JsonNode response = apiCaller.getCommunesByName("zzzzz", "10");
        assertEquals(0, response.size());
    }

    // Test sur byCodePostal avec des resultats
    @Test
    public void testApiCallByCodePostalExpectsResponseNotEmpty() {
        APICaller apiCaller = new APICaller();
        JsonNode response = apiCaller.getCommunesByCodePostal("60130", "10");
        assertNotEquals(0, response.size());
    }

    // Test sur byCodePostal sans resultats
    @Test
    public void testApiCallByCodePostalExpectsEmpty() {
        APICaller apiCaller = new APICaller();
        JsonNode response = apiCaller.getCommunesByCodePostal("zzzzz", "10");
        assertEquals(0, response.size());
    }

    // Test sur byCodePostal avec des resultats
    @Test
    public void testApiCallByCodeDepartementExpectsResponseNotEmpty() {
        APICaller apiCaller = new APICaller();
        JsonNode response = apiCaller.getCommunesByDepartement("60", "10");
        assertNotEquals(0, response.size());
    }

    // Test sur byCodePostal sans resultats
    @Test
    public void testApiCallByCodeDepartementExpectsEmpty() {
        APICaller apiCaller = new APICaller();
        JsonNode response = apiCaller.getCommunesByDepartement("zzzzz", "10");
        assertEquals(0, response.size());
    }

    @Test
    public void testGetCommuneNom(){
        APICaller apiCaller = new APICaller();
        JsonNode communes = apiCaller.getCommunes(CommunesFieldsEnum.NOM, "Beauvais", "10");
        assertNotEquals(0, communes.size());
    }

    @Test
    public void testGetCommunesCodePostal(){
        APICaller apiCaller = new APICaller();
        JsonNode communes = apiCaller.getCommunes(CommunesFieldsEnum.CODE_POSTAL, "60130", "100");
        assertNotEquals(0, communes.size());
    }


    @Test
    public void testGetEtablissementsCodeDepartement2Numbers(){
        APICaller apiCaller = new APICaller();
        JsonNode communes = apiCaller.getCommunes(CommunesFieldsEnum.DEPARTEMENT, "60", "100");
        assertNotEquals(0, communes.size());
    }
}
