package rezoner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import jess.Filter;
import jess.JessException;
import jess.Rete;
import jess.WorkingMemoryMarker;

public class Rezoner {

	public static Rete engine = new Rete();
	private HashMap<String, WorkingMemoryMarker> markeri = new HashMap<String, WorkingMemoryMarker>();

	public void postaviMarker(String key) {
		// Mark end of catalog data for later
		if (markeri.containsKey(key)) {
			System.out.println("Vec postoji marker za" + key);
		} else {
			markeri.put(key, engine.mark());
		}
	}

	public void resetujNaMarker(String key) {
		// Remove any previous order data, leaving only catalog data
		if (!markeri.containsKey(key)) {
			System.out.println("Ne postoji marker " + key);
			return;
		} else {
			try {
				engine.resetToMark(markeri.get(key));
			} catch (JessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void pokreniRezonovanje() {
		try {
			engine.run();
		} catch (JessException e) {
			e.printStackTrace();
		}
	}

	public static void postaviTemplejte(String putanja) {
		try {
			engine.batch(putanja);
		} catch (JessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void postaviPravila(String putanja) {
		try {
			engine.batch(putanja);
		} catch (JessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void izvrsiNaredbuKaoTekst(String naredba) {
		try {
			engine.batch(naredba);
		} catch (JessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static List<Object> vratiRezultate(String className){
		List<Object> lista = new ArrayList<Object>();
		Iterator<?> it;
		try {
			it = engine.getObjects(new Filter.ByClass(Class.forName(className)));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		
		while (it.hasNext()) { 
			lista.add(it.next());
		}
		return lista;
	}
}
