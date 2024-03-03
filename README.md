# AcDcCompiler
A simple compiler from the ac (adding calculator) language to the [dc](https://en.wikipedia.org/wiki/Dc_(computer_program)) (desktop calculator) language. Documentation and interface are entirely in italian.
## How to use this program
The program can be easily run from the executable [CompilatoreACDC.jar](https://github.com/SapphireDragoness/AcDcCompiler/blob/main/CompilatoreACDC.jar). Just feed it a text file containing a (valid) ac source file and let the program do its magic.
## How this compiler works
The compiler is made up of a scanner, whose job is to break down the source code into tokens; a parser, that checks if the tokens received from the scanner follow the ac grammar (spedified below) and builds an abstract syntax tree representing the syntax of the source code; a typechecker, that decorates the AST with information regarding data types; and a code generator, that walks the AST and generates dc code.
### ac's grammar
0. Prg → DSs $
1. DSs → Dcl DSs
2. DSs → Stm DSs
3. DSs → ϵ
4. Dcl → Ty id DclP
5. DclP → ;
6. DclP → opAss Exp;
7. Stm → id opAss Exp;
8. Stm → print id;
9. Exp → Tr ExpP
10. ExpP → -Tr ExpP
11. ExpP → +Tr ExpP
12. ExpP → ϵ
13. Tr → Val TrP
14. TrP → /Val TrP
15. TrP → *Val TrP
16. TrP → ϵ
17. Ty → float
18. Ty → int
19. Val → intVal
20. Val → floatVal
21. Val → id
## Special features
- GUI to make compilation more accessible
- "panic mode" in parser: if the source code is malformed, the parser keeps on parsin' by finding the delimiter ';' and resuming operation

