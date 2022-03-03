package kruszywo.sa.computers.manage.model;

public enum OperationType {

	DISPLAY("DISPLAY"),
	INSERT("INSERT"),
	UPDATE("UPDATE"),
	DELETE("DELETE");

	private String operationType;
	
	OperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getOperationType() {
		return operationType;
	}

}
