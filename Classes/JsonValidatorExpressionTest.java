package lf1.plp.functional1.expression;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import lf1.plp.expressions2.expression.ValorString;
import lf1.plp.expressions2.expression.ValorInteiro;
import lf1.plp.expressions2.expression.ValorBooleano;
import lf1.plp.expressions2.memory.ContextoExecucao;
import lf1.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf1.plp.expressions2.memory.VariavelNaoDeclaradaException;

public class JsonValidatorExpressionTest {
    private ContextoExecucao ambiente;
    
    @Before
    public void setUp() {
        ambiente = new ContextoExecucao();
    }
    
    @Test
    public void testObjetoVazio() throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        JsonValidatorExpression validator = new JsonValidatorExpression(new ValorString("{}"));
        ValorBooleano resultado = (ValorBooleano) validator.avaliar(ambiente);
        assertTrue(resultado.valor());
    }
    
    @Test
    public void testArrayVazio() throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        JsonValidatorExpression validator = new JsonValidatorExpression(new ValorString("[]"));
        ValorBooleano resultado = (ValorBooleano) validator.avaliar(ambiente);
        assertTrue(resultado.valor());
    }
    
    @Test
    public void testObjetoSimples() throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        JsonValidatorExpression validator = new JsonValidatorExpression(
            new ValorString("{ \"nome\": \"valor\" }")
        );
        ValorBooleano resultado = (ValorBooleano) validator.avaliar(ambiente);
        assertTrue(resultado.valor());
    }
    
    @Test
    public void testArraySimples() throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        JsonValidatorExpression validator = new JsonValidatorExpression(
            new ValorString("[1, 2, 3]")
        );
        ValorBooleano resultado = (ValorBooleano) validator.avaliar(ambiente);
        assertTrue(resultado.valor());
    }
    
    @Test
    public void testObjetoAninhado() throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        JsonValidatorExpression validator = new JsonValidatorExpression(
            new ValorString("{ \"pessoa\": { \"nome\": \"João\", \"idade\": 30 } }")
        );
        ValorBooleano resultado = (ValorBooleano) validator.avaliar(ambiente);
        assertTrue(resultado.valor());
    }
    
    @Test
    public void testChavesNaoBalanceadas() throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        JsonValidatorExpression validator = new JsonValidatorExpression(new ValorString("{"));
        ValorBooleano resultado = (ValorBooleano) validator.avaliar(ambiente);
        assertFalse(resultado.valor());
    }
    
    @Test
    public void testColchetesNaoBalanceados() throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        JsonValidatorExpression validator = new JsonValidatorExpression(new ValorString("[1, 2"));
        ValorBooleano resultado = (ValorBooleano) validator.avaliar(ambiente);
        assertFalse(resultado.valor());
    }
    
    @Test
    public void testStringVazia() throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        JsonValidatorExpression validator = new JsonValidatorExpression(new ValorString(""));
        ValorBooleano resultado = (ValorBooleano) validator.avaliar(ambiente);
        assertFalse(resultado.valor());
    }
    
    @Test
    public void testEntradaNaoString() throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        JsonValidatorExpression validator = new JsonValidatorExpression(new ValorInteiro(123));
        ValorBooleano resultado = (ValorBooleano) validator.avaliar(ambiente);
        assertFalse(resultado.valor());
    }
    
    @Test
    public void testObjetoComplexo() throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        JsonValidatorExpression validator = new JsonValidatorExpression(
            new ValorString("{ \"array\": [1, 2, { \"chave\": \"valor\" }], \"objeto\": { \"sub\": true } }")
        );
        ValorBooleano resultado = (ValorBooleano) validator.avaliar(ambiente);
        assertTrue(resultado.valor());
    }
} 