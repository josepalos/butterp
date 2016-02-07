package cat.udl.eps.butterp.data;

import java.util.Arrays;
import java.util.List;

public class ListOps {

    public static SExpression cons(SExpression car, SExpression cdr) {
        return new ConsCell(car, cdr);
    }

    public static SExpression car(SExpression sexpr) {
        return ((ConsCell) sexpr).car;
    }

    public static SExpression cdr(SExpression sexpr) {
        return ((ConsCell) sexpr).cdr;
    }

    public static SExpression list(SExpression... elems) {
	return list(Arrays.asList(elems));
    }

    public static SExpression list(List<SExpression> elems) {
        return listRec(elems, 0);
    }
    
    private static SExpression listRec(List<SExpression> elems, int offset){
	if(elems.size() == offset){
	    return Symbol.NIL;
	}else{
	    return new ConsCell(
		    elems.get(offset),
		    listRec(elems, offset+1)
		);
	}
    }

    public static int length(SExpression sexpr) {
        if (sexpr.equals(Symbol.NIL)) return 0;
        return 1 + length( cdr(sexpr) );
    }

    public static SExpression nth(SExpression sexpr, int n) {
	if( sexpr == Symbol.NIL)
	    throw new IndexOutOfBoundsException();
	else if(n == 0)
	    return car(sexpr);
	else
	    return nth( cdr(sexpr), n-1 );
    }

    public static boolean isListOf(SExpression params, Class<?> klass) {
        return params.equals(Symbol.NIL) ||
		( klass.isInstance(car(params)) &&
		isListOf(cdr(params), klass));
    }

}