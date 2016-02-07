package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ConsCell implements SExpression, Iterable<SExpression>{

    public final SExpression car; // Si el definiu privat caldrà un getter
    public final SExpression cdr; // Si el definiu privat caldrà un getter

    public ConsCell(SExpression car, SExpression cdr) {
        this.car = car;
	this.cdr = cdr;
    }

    @Override
    public SExpression eval(Environment env) {
	SExpression first = car.eval(env);
	if( first instanceof Function ){
	    Function f = (Function) first;
	    return f.apply(cdr, env);
	}else if( first instanceof Special){
	    Special s = (Special) first;
	    return s.applySpecial(cdr, env);
	}else{
	    throw new EvaluationError("Cannot apply "+first);
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

    @Override
    public Iterator<SExpression> iterator() {
	return new ConsCellIterator(this);
    }
    
    private class ConsCellIterator implements Iterator<SExpression>{
	private SExpression current;

	public ConsCellIterator(ConsCell c) {
	    current = c;
	}
	
	@Override
	public boolean hasNext() {
	    return !current.equals(Symbol.NIL);
	}

	@Override
	public SExpression next() {
	    if(!hasNext()) throw new NoSuchElementException();
	    
	    SExpression next = ListOps.car(current);
	    current = ListOps.cdr(current);
	    return next;
	}
    }
}
