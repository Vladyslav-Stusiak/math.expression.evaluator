# Simple mathematical expression parser

## Overview

Simple parser written to do some algorithmic practice.

Consists of three modules:

- Lexical analyzer
  - Responsibilities
    - Produce arrays of tokens from expression based on defined grammar
- Syntax analyzer
  - Responsibilities
    - Build a syntax tree from tokens
- Translator
  - Responsibilities
    - Traverse the syntax tree in the defined manner (depth-first, e.g.) and evaluate nodes

![Modules diagram](https://i.ibb.co/64wKwyX/Math-expression-evaluation-drawio.png)