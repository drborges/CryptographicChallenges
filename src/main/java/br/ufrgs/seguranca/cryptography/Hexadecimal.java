package br.ufrgs.seguranca.cryptography;

public class Hexadecimal {

	private String value;
	private String padding = "";

	public Hexadecimal() {}
	
	public Hexadecimal(String value) {
		this.value = CipherUtils.asHex(value.getBytes());
	}
	
	public Hexadecimal(byte[] value) {
		this.value = CipherUtils.asHex(value);
	}
	
	public Hexadecimal setValue(String value) {
		this.value = value.toUpperCase();
		return this;
	}
	
	public String getValue() {
		return value;
	}
	
	public byte[] asByteArray() {
		return CipherUtils.hexToBytes(getPaddedValue());
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj == null) return false;
		if (!(obj instanceof Hexadecimal)) return false;
		
		Hexadecimal that = (Hexadecimal)obj;
		
		return this.value.equals(that.value);
	}
	
	@Override
	public String toString() {
		return "[Hexadecimal: " + value + "]";
	}

	public Hexadecimal setPadding(String padding) {
		this.padding = padding;
		return this;
	}

	public String getPadding() {
		return padding;
	}
	
	public String getPaddedValue() {
		return value + padding;
	}
}
