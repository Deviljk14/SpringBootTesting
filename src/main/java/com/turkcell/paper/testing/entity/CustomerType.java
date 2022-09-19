package com.turkcell.paper.testing.entity;


public enum CustomerType {
	
	INDIVIDUAL(1, "Individual"), CORPORATE(2, "Corporate");

	private final int code;
	private final String label;

	CustomerType(int code, String label) {
		this.code = code;
		this.label = label;
	}

	public int getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}

	public boolean isCorporate() {
		return code == 2;
	}

	public boolean isIndividual() {
		return code == 1;
	}
}
