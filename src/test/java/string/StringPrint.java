package string;

import com.fasterxml.jackson.databind.JsonNode;
import fr.itii.apiweb.data.remote.APICaller;
import fr.itii.apiweb.domain.models.objet.Commune;
import fr.itii.apiweb.domain.models.enumlist.api.APICommune;
import fr.itii.apiweb.domain.tools.JSONSerializer;

import java.util.ArrayList;
import java.util.List;

public class StringPrint {

    public List<Commune> testCommune(List<Commune> liste, String value) {
        if(value.equals("*")){
            return liste;
        }

        String[] select = value.replaceAll("[^0-9\\- ]", "").split("\\s+");
        List<Commune> newList = new ArrayList<>();

        for (String s : select) {
            if (s.isEmpty()) continue;

            String[] intervalle = s.split("-");
            try {
                int start = Integer.parseInt(intervalle[0]);
                int end = (intervalle.length > 1) ? Integer.parseInt(intervalle[1]) : start;

                for (int j = Math.max(0, start); j <= Math.min(end, liste.size() - 1); j++) {
                    newList.add(liste.get(j));
                }
            } catch (NumberFormatException e) {

            }
        }
        return newList;
    }

    public static void main(String[] args) {
        String a = "";

        APICaller apiCaller = new APICaller();
        JsonNode jsonNode = apiCaller.getCommunes(APICommune.NOM, "beauvais", 100);
        System.out.println(jsonNode);

        JSONSerializer jsonSerializer = new JSONSerializer();

        List<Commune> communes = jsonSerializer.toCommunes(jsonNode);
        System.out.println(communes);

        StringPrint sb = new StringPrint();

        List<Commune> liste = sb.testCommune(communes, a);


        for (Commune commune : liste) {
            System.out.println(commune);
        }
    }


}
