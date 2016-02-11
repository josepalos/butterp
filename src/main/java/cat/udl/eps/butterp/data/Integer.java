package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

public class Integer implements SExpression {

    public final int value; // Si el definiu privat caldrà un getter

    public Integer(int value) {
	this.value = value;
    }

    @Override
    public SExpression eval(Environment env) {
	return this;
    }

    @Override
    public boolean equals(Object o) {
	return (o instanceof Integer
		&& ((Integer) o).value == this.value);
    }

    @Override
    public int hashCode() {
	return value;
    }

    @Override
    public String toString() {
	return java.lang.Integer.toString(value);
    }
}
