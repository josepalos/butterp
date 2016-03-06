package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;
import java.util.LinkedList;
import java.util.List;

public class ConsCell implements SExpression {

    public final SExpression car; // Si el definiu privat caldrà un getter
    public final SExpression cdr; // Si el definiu privat caldrà un getter

    public ConsCell(SExpression car, SExpression cdr) {
	this.car = car;
	this.cdr = cdr;
    }

    @Override
    public SExpression eval(Environment env) {
	SExpression first = car.eval(env);

	if (first instanceof Function) {
	    Function f = (Function) first;
            SExpression args = evalAll(cdr, env);
	    return f.apply(args, env);
	} else if (first instanceof Special) {
	    Special s = (Special) first;
	    return s.applySpecial(cdr, env);
	} else {
	    throw new EvaluationError("Cannot apply " + first);
	}
    }

    @Override
    public boolean equals(Object o) {
	return (o instanceof ConsCell
		&& ((ConsCell) o).car.equals(this.car)
		&& ((ConsCell) o).cdr.equals(this.cdr));
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

    private void elementsToString(StringBuilder strb) {
	strb.append(car.toString());
	if (cdr instanceof ConsCell) {
	    strb.append(", ");
	    ((ConsCell) cdr).elementsToString(strb);
	}
    }

    private SExpression evalAll(SExpression args, Environment env) {
        SExpression current = args;
        List<SExpression> evaluatedArgs = new LinkedList<>();
        while( !current.equals(Symbol.NIL) ){
            evaluatedArgs.add( ListOps.car(current).eval(env) );
            current = ListOps.cdr(current);
        }
        
        return ListOps.list(evaluatedArgs);
    }
}
