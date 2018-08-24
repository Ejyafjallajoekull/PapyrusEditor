package papyrus;

import java.util.ArrayList;

public class PapyrusScript {
	
	// variables
	private String name = null; // name of the script
	private ArrayList<PapyrusVariable> variables = new ArrayList<PapyrusVariable>(); // list of all variables

	public ArrayList<PapyrusVariable> getVariables() {
		return variables;
	}
	public void setVariables(ArrayList<PapyrusVariable> variables) {
		this.variables = variables;
	}
	

}
