package lf1.plp.functional1.expression;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import java.util.Arrays;
import java.util.List;

import lf1.plp.expressions2.expression.Expressao;
import lf1.plp.expressions2.expression.ValorString;
import lf1.plp.expressions2.expression.ValorInteiro;
import lf1.plp.expressions2.memory.ContextoExecucao;
import lf1.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf1.plp.expressions2.memory.VariavelNaoDeclaradaException;

public class PatternFillExpressionTest {
    private ContextoExecucao ambiente;
    
    @Before
    public void setUp() {
        ambiente = new ContextoExecucao();
    }
    
    @Test
    public void testPreenchimentoSimples() throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        List<Expressao> valores = Arrays.asList(
            new ValorString("mundo"),
            new ValorString("PLP")
        );
        
        PatternFillExpression fillPattern = new PatternFillExpression(
            new ValorString("Olá {}, bem-vindo ao {}!"),
            valores
        );
        
        ValorString resultado = (ValorString) fillPattern.avaliar(ambiente);
        assertEquals("Olá mundo, bem-vindo ao PLP!", resultado.valor());
    }
    
    @Test
    public void testPreenchimentoComNumeros() throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        List<Expressao> valores = Arrays.asList(
            new ValorInteiro(10),
            new ValorInteiro(20)
        );
        
        PatternFillExpression fillPattern = new PatternFillExpression(
            new ValorString("A soma de {} com {} é {}"),
            valores
        );
        
        ValorString resultado = (ValorString) fillPattern.avaliar(ambiente);
        assertEquals("A soma de 10 com 20 é {}", resultado.valor());
    }
    
    @Test
    public void testPreenchimentoVazio() throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        List<Expressao> valores = Arrays.asList();
        
        PatternFillExpression fillPattern = new PatternFillExpression(
            new ValorString("Texto sem placeholders"),
            valores
        );
        
        ValorString resultado = (ValorString) fillPattern.avaliar(ambiente);
        assertEquals("Texto sem placeholders", resultado.valor());
    }
    
    @Test
    public void testPreenchimentoMaisValoresQuePlaceholders() throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        List<Expressao> valores = Arrays.asList(
            new ValorString("um"),
            new ValorString("dois"),
            new ValorString("três")
        );
        
        PatternFillExpression fillPattern = new PatternFillExpression(
            new ValorString("Valores: {} e {}"),
            valores
        );
        
        ValorString resultado = (ValorString) fillPattern.avaliar(ambiente);
        assertEquals("Valores: um e dois", resultado.valor());
    }
    
    @Test
    public void testPreenchimentoMenosValoresQuePlaceholders() throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        List<Expressao> valores = Arrays.asList(
            new ValorString("único")
        );
        
        PatternFillExpression fillPattern = new PatternFillExpression(
            new ValorString("Valor {} de {}"),
            valores
        );
        
        ValorString resultado = (ValorString) fillPattern.avaliar(ambiente);
        assertEquals("Valor único de {}", resultado.valor());
    }
} 