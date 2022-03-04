package kruszywo.sa.computers.manage.model;

public enum OperationType {

	DISPLAY("DISPLAY", false),
	INSERT("INSERT", true),
	UPDATE("UPDATE", true),
	DELETE("DELETE", false);

	private String operationType;
	private boolean windowEditable;
	
	OperationType(String operationType, boolean windowEditable) {
		this.operationType = operationType;
		this.windowEditable = windowEditable;
	}

	public String getOperationType() {
		return operationType;
	}

	public boolean isWindowEditable() {
		return windowEditable;
	}

	public void setWindowEditable(boolean windowEditable) {
		this.windowEditable = windowEditable;
	}

}
