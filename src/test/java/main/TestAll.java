/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import cat.udl.eps.butterp.LexerTest;
import cat.udl.eps.butterp.ParserTest;
import cat.udl.eps.butterp.PrimitivesTest;
import cat.udl.eps.butterp.ScopeTest;
import cat.udl.eps.butterp.data.ListOpsTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Usuari
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({LexerTest.class, ParserTest.class, PrimitivesTest.class, ScopeTest.class, ListOpsTest.class})
public class TestAll {
    
}
