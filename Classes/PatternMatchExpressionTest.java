package lf1.plp.functional1.expression;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import lf1.plp.expressions2.expression.ValorString;
import lf1.plp.expressions2.expression.ValorBooleano;
import lf1.plp.expressions2.memory.ContextoExecucao;
import lf1.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf1.plp.expressions2.memory.VariavelNaoDeclaradaException;

public class PatternMatchExpressionTest {
    private ContextoExecucao ambiente;
    
    @Before
    public void setUp() {
        ambiente = new ContextoExecucao();
    }
    
    @Test
    public void testPadraoLetrasMaiusculasDigitos() throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        PatternMatchExpression matcher = new PatternMatchExpression(
            new ValorString("ABC-123"),
            new ValorString("LLL-DDD")
        );
        ValorBooleano resultado = (ValorBooleano) matcher.avaliar(ambiente);
        assertTrue(resultado.valor());
    }
    
    @Test
    public void testPadraoLetrasMinusculasDigitos() throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        PatternMatchExpression matcher = new PatternMatchExpression(
            new ValorString("abc-123"),
            new ValorString("lll-DDD")
        );
        ValorBooleano resultado = (ValorBooleano) matcher.avaliar(ambiente);
        assertTrue(resultado.valor());
    }
    
    @Test
    public void testPadraoMisto() throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        PatternMatchExpression matcher = new PatternMatchExpression(
            new ValorString("AbC-1@3"),
            new ValorString("AAA-XXX")
        );
        ValorBooleano resultado = (ValorBooleano) matcher.avaliar(ambiente);
        assertTrue(resultado.valor());
    }
    
    @Test
    public void testPadraoTamanhoDiferente() throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        PatternMatchExpression matcher = new PatternMatchExpression(
            new ValorString("ABC-1234"),
            new ValorString("LLL-DDD")
        );
        ValorBooleano resultado = (ValorBooleano) matcher.avaliar(ambiente);
        assertFalse(resultado.valor());
    }
    
    @Test
    public void testPadraoEmail() throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        PatternMatchExpression matcher = new PatternMatchExpression(
            new ValorString("abc@def"),
            new ValorString("lll@lll")
        );
        ValorBooleano resultado = (ValorBooleano) matcher.avaliar(ambiente);
        assertTrue(resultado.valor());
    }
    
    @Test
    public void testPadraoTelefone() throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        PatternMatchExpression matcher = new PatternMatchExpression(
            new ValorString("(11) 98765-4321"),
            new ValorString("(DD) DDDDD-DDDD")
        );
        ValorBooleano resultado = (ValorBooleano) matcher.avaliar(ambiente);
        assertTrue(resultado.valor());
    }
    
    @Test
    public void testPadraoData() throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        PatternMatchExpression matcher = new PatternMatchExpression(
            new ValorString("25/12/2024"),
            new ValorString("DD/DD/DDDD")
        );
        ValorBooleano resultado = (ValorBooleano) matcher.avaliar(ambiente);
        assertTrue(resultado.valor());
    }
    
    @Test
    public void testPadraoNomeProprio() throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        PatternMatchExpression matcher = new PatternMatchExpression(
            new ValorString("Patricia"),
            new ValorString("Lllllll")
        );
        ValorBooleano resultado = (ValorBooleano) matcher.avaliar(ambiente);
        assertTrue(resultado.valor());
    }
    
    @Test
    public void testPadraoProduto() throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        PatternMatchExpression matcher = new PatternMatchExpression(
            new ValorString("XY-1234-AB"),
            new ValorString("AA-DDDD-LL")
        );
        ValorBooleano resultado = (ValorBooleano) matcher.avaliar(ambiente);
        assertTrue(resultado.valor());
    }
} 