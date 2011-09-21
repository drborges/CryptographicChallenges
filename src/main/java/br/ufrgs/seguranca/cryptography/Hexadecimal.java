package br.ufrgs.seguranca.cryptography;

public class Hexadecimal {

	private String value;
	private Hexadecimal padding;

	public Hexadecimal() {}
	
	public Hexadecimal(String value) {
		this.value = CipherUtils.asHex(value.getBytes());
	}
	
	public Hexadecimal(byte[] value) {
		this.value = CipherUtils.asHex(value);
	}
	
	public boolean hasPadding() {
		return padding != null;
	}
	
	public Hexadecimal setValue(String value) {
		this.value = value.toUpperCase();
		return this;
	}
	
	public String getValue() {
		
		if (padding == null) {
			return value;
		} else {
			return value + padding.value;
		}
	}
	
	public byte[] asByteArray() {
		return CipherUtils.hexToBytes(getValue());
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

	public Hexadecimal setPadding(Hexadecimal padding) {
		this.padding = padding;
		return this;
	}
	
	public Hexadecimal setPadding(String hexaPadding) {
		this.padding = new Hexadecimal().setValue(hexaPadding);
		return this;
	}
	
	public Hexadecimal getPadding() {
		return padding;
	}

	public int getBytesCount() {
		return getValue().length();
	}
}
