package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

public class ConsCell implements SExpression {

    public final SExpression car; // Si el definiu privat caldrà un getter
    public final SExpression cdr; // Si el definiu privat caldrà un getter

    public ConsCell(SExpression car, SExpression cdr) {
        this.car = car;
	this.cdr = cdr;
    }

    @Override
    public SExpression eval(Environment env) {
	if( car instanceof Function ){
	    Function f = (Function) car;
	    return f.apply(cdr, env);
	}else if( car instanceof Special){
	    Special s = (Special) car;
	    return s.applySpecial(cdr, env);
	}else{
	    return car.eval(env);
	}
    }

    @Override
    public boolean equals(Object o) {
        return (
		o instanceof ConsCell &&
		((ConsCell)o).car.equals(this.car) &&
		((ConsCell)o).cdr.equals(this.cdr)
	    );
    }

    @Override
    public int hashCode() {
        int hash = 1;
	hash = hash * 13 + car.hashCode();
	hash = hash * 31 + cdr.hashCode();
	return hash;
    }

    @Override
    public String toString() {
	StringBuilder strb = new StringBuilder().append('(');
	elementsToString(strb);
	strb.append(')');
        return strb.toString();
    }
    
    private void elementsToString(StringBuilder strb){
	strb.append(car.toString());
	if( cdr instanceof ConsCell ){
	    strb.append(", ");
	    ((ConsCell)cdr).elementsToString(strb);
	}
    }
}
