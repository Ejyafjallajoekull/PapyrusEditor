package papyrus;

public class PapyrusVariable {
	// representing a papyrus variable
	
	// fields
	private String name = null; // name of the variable
	private String type = null; // type of the variable
	
	// constructor
	public PapyrusVariable(String type, String name) {
		this.name = name;
		this.type = type;
	}

	
	// getters
	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}
	
	// setters
	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

}
