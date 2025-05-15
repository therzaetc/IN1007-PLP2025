package lf1.plp.functional1.expression;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import lf1.plp.expressions2.expression.ValorString;
import lf1.plp.expressions2.memory.ContextoExecucao;
import lf1.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf1.plp.expressions2.memory.VariavelNaoDeclaradaException;

public class StringReplaceExpressionTest {
    private ContextoExecucao ambiente;
    
    @Before
    public void setUp() {
        ambiente = new ContextoExecucao();
    }
    
    @Test
    public void testSubstituicaoSimples() throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        StringReplaceExpression replace = new StringReplaceExpression(
            new ValorString("Hello, World!"),
            new ValorString("World"),
            new ValorString("PLP")
        );
        ValorString resultado = (ValorString) replace.avaliar(ambiente);
        assertEquals("Hello, PLP!", resultado.valor());
    }
    
    @Test
    public void testSubstituicaoMultipla() throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        StringReplaceExpression replace = new StringReplaceExpression(
            new ValorString("banana banana banana"),
            new ValorString("banana"),
            new ValorString("laranja")
        );
        ValorString resultado = (ValorString) replace.avaliar(ambiente);
        assertEquals("laranja laranja laranja", resultado.valor());
    }
    
    @Test
    public void testSubstituicaoStringVazia() throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        StringReplaceExpression replace = new StringReplaceExpression(
            new ValorString("Remover espaços"),
            new ValorString(" "),
            new ValorString("")
        );
        ValorString resultado = (ValorString) replace.avaliar(ambiente);
        assertEquals("Removerespaços", resultado.valor());
    }
    
    @Test
    public void testSubstituicaoPadraoInexistente() throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        StringReplaceExpression replace = new StringReplaceExpression(
            new ValorString("Texto original"),
            new ValorString("inexistente"),
            new ValorString("novo")
        );
        ValorString resultado = (ValorString) replace.avaliar(ambiente);
        assertEquals("Texto original", resultado.valor());
    }
    
    @Test
    public void testSubstituicaoCaracteresEspeciais() throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        StringReplaceExpression replace = new StringReplaceExpression(
            new ValorString("linha1\nlinha2"),
            new ValorString("\n"),
            new ValorString(" - ")
        );
        ValorString resultado = (ValorString) replace.avaliar(ambiente);
        assertEquals("linha1 - linha2", resultado.valor());
    }
    
    @Test
    public void testSubstituicaoTextoVazio() throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        StringReplaceExpression replace = new StringReplaceExpression(
            new ValorString(""),
            new ValorString("algo"),
            new ValorString("novo")
        );
        ValorString resultado = (ValorString) replace.avaliar(ambiente);
        assertEquals("", resultado.valor());
    }
    
    @Test
    public void testSubstituicaoEspacosDuplos() throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        StringReplaceExpression replace = new StringReplaceExpression(
            new ValorString("  espaços  extras  "),
            new ValorString("  "),
            new ValorString(" ")
        );
        ValorString resultado = (ValorString) replace.avaliar(ambiente);
        assertEquals(" espaços extras ", resultado.valor());
    }
    
    @Test
    public void testSubstituicaoCaseSensitive() throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        StringReplaceExpression replace = new StringReplaceExpression(
            new ValorString("TESTE teste TESTE"),
            new ValorString("TESTE"),
            new ValorString("Novo")
        );
        ValorString resultado = (ValorString) replace.avaliar(ambiente);
        assertEquals("Novo teste Novo", resultado.valor());
    }
} 