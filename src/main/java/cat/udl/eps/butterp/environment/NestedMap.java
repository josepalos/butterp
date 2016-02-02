package cat.udl.eps.butterp.environment;

import cat.udl.eps.butterp.data.EvaluationError;
import cat.udl.eps.butterp.data.SExpression;
import cat.udl.eps.butterp.data.Symbol;
import java.util.HashMap;
import java.util.Map;

public class NestedMap implements Environment {
    private final Map<Symbol, SExpression> map;
    private final Environment parent;
    
    public NestedMap() {
        map = new HashMap<>();
	parent = null;
    }
    
    private NestedMap(Environment parent){
	map = new HashMap<>();
	this.parent = parent;
    }

    @Override
    public void bindGlobal(Symbol symbol, SExpression value) {
	/*
	This Environment implementation is designed to represent all the
	NestedMaps like a tree, with a root (identified with the parent set to
	null).
	So if a NestedMap is not the root, it should tell the parent to
	bind globally this symbol.
	If it's the root, trying to bind globally to the parent will not be
	logic (it hasn't parent).
	*/
        if(parent != null){
	    parent.bindGlobal(symbol, value);
	}else{
	    this.bind(symbol, value);
	}
    }

    @Override
    public SExpression find(Symbol symbol) {
	/*
	If this NestedMap doesn't have this symbol binded, call
	recursively to parent's find method.
	If this recursively call reaches root, but the symbol is not found
	there either, the system couldn't identify the symbol, so an
	EvaluationError is thrown.
	*/
        if(map.containsKey(symbol)){
	    return map.get(symbol);
	}else if(parent != null){
	    return parent.find(symbol);
	}else{
	    throw new EvaluationError("Symbol "+symbol.name+" not found");
	}
    }

    @Override
    public Environment extend() {
	Environment env = new NestedMap(this);
	return env;
    }

    @Override
    public void bind(Symbol symbol, SExpression value) {
        throw new UnsupportedOperationException("not implemented yet");
    }

}
