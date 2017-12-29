package object;

public class SuggestionObj {	
	private String suggname;
	private String suggtype;
	
	public SuggestionObj(String suggestion){
		super();
		this.suggname = suggestion;	
	}
	public SuggestionObj() {
		super();
		this.suggname = "";	
	}
	public String getsuggname() {
		return suggname;
	}
	public void setsuggname(String suggestion) {
		this.suggname = suggestion;
	}
	public String getsuggtype() {
		return suggtype;
	}
	public void setsuggtype(String suggtype) {
		this.suggtype = suggtype;
	}
	
	
}
