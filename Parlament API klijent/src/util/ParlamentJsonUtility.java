package util;

import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import parlament.Poslanik;

public class ParlamentJsonUtility {
	
	public static LinkedList<Poslanik> prebaciIzJsonUListu(JsonArray jsonArray) throws Exception{
		Gson gson = new GsonBuilder().create();

		LinkedList<Poslanik> poslanici = new LinkedList<>();
		
		for (int i = 0; i < jsonArray.size(); i++) {
			
			JsonObject jsonObject = (JsonObject) jsonArray.get(i);
			Poslanik p = gson.fromJson(jsonObject, Poslanik.class);
			
			poslanici.add(p);
		}
		return poslanici;
	}
	
}
