# Paradigmas de Linguagem de Programação 2025.1 - IN1007
# Alunas:

Maria Luisa Barreto de Gois - mlbg

Maria Thereza Carlos Rodrigues - mtcr

# Atividade

Este projeto tem como objetivo atender à disciplina de Paradigmas de Linguagens de Programação, ofertado pelo CIN na UFPE no semestre 2025.1.

O objetivo será estender uma linguagem de programação funcional com operadores básicos para manipulação de strings, adicionando funcionalidades como verificação de conformidade com o formato JSON, preenchimento de padrões pré-definidos, verificação de padrões e substituição de ocorrências de substrings.

# Contextualização
Este projeto estende as linguagens Funcional 1 com operadores para manipulação de Strings e validações com JSON. As funções são as seguintes:


- Verifica a conformidade com o formato JSON: avalia uma expressão e verifica se o seu valor tem o formato básico de JSON válido.

```
Input: {"nome": "João"}

Output: true

Input: {nome: "Maria", idade: 20}

Output: false
```

- Preenche um padrão pré-definido: realiza a substituição de placeholders {} dentro de uma string com valores fornecidos dinamicamente.

```
Input: "Olá, {}, você tem {} mensagens." ["Lucas", "5"]

Output: "Olá, Lucas, você tem 5 mensagens."
  
Input: "Resultado: {} + {} = {}" ["2", "3", "5"]

Output: "Resultado: 2 + 3 = 5"
```

- Verifica padrão customizado: permite comparar uma string contra um template de caracteres especiais (como "DllD"), e retorna um valor booleano indicando se o texto obedece ao padrão.

```
Input: "Abe9" "DllD"

Output: true

Input: "abc123" "lllDDD"

Output: true
```

- Substitui ocorrências de uma substring: substitui substrings dentro de uma string.

```
Input: "banana" "na" "ma"

Output: "bamama"

Input: "abcabc" "a" "z"

Output: "zbczbc"
```
