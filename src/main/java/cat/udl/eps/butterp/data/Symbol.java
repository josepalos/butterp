package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

public class Symbol implements SExpression {

    public static final Symbol TRUE = new Symbol("t");
    public static final Symbol NIL = new Symbol("nil");

    public final String name; // Si el definiu privat caldrà un getter

    public Symbol(String name) {
        this.name = name;
    }

    @Override
    public SExpression eval(Environment env) {
	return env.find(this).eval(env);
    }

    @Override
    public boolean equals(Object o) {
        return (
		o instanceof Symbol &&
		((Symbol)o).name.equals(this.name)
	    );
    }

    @Override
    public int hashCode() {
        int hash = 1;
	hash = hash*17 + this.name.hashCode();
	return hash;
    }

    @Override
    public String toString() {
        return name;
    }
}
