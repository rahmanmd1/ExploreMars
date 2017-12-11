package org.example.em.ExploreMars.model;

public enum Region {
	
    HELLAS_BASIN("Hellas Basin"),UTOPIA_BASIN("Utopian Basin"),TEMPE_TERRA("Tempe Terra"),ALBA_MONS("Alba Mons"),AMAZONIS_PLANITIA("Amazonis planitia");
	private String label;
	private Region(String label) {
		this.label = label;
	}
	

	public static Region findByLabel(String label) {
		
		for(Region r: Region.values()) {
			
			if(r.label.equalsIgnoreCase(label)) {
				
				return r;
			}
		}
		
		return null;
		
	}
	
}
