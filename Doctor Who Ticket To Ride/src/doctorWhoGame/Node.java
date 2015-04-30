package doctorWhoGame;

public class Node {

	private final int id;
	private final String name;
	
	public Node(int i){
		this(i, "");
	}
	
	public Node(int i, String name) {
		this.id = i;
		this.name = name;
	}

	public int getID() {
		return this.id;
	}
	
	public String getName(){
		return this.name;
	}

	@Override
	public boolean equals(Object n1) {
		return (((Node) n1).id == this.id && ((Node) n1).name == this.name);
	}
	
	

}
