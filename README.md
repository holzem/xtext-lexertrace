# xtext-lexertrace

This demo shows how to trace the lexer tokens that are fed to the parser. Whenever you are
trying to understand why the parser does not work as expected it is helpful to understand
what is happening in the background.

## Adding the trace feature

##### Adding tracing to unit test

These are the basic changes to the base implementation of the `Hello World` grammar.

1.  Create classes inheriting from `InternalLexertraceLexer`. Unfortunately Xtext creates two
    similar classes, one in the language plugin and another one in the ide plugin.
2.  After providing the same constructors as the superclass you need to override the method
    `nextToken`. This method provides the next token from the character stream to the parser.
    An implementation detail is, that Xtext/Antlr just provide CommonTokens although the 
    implementation is based on the token interface. A simple cast can fix that the debug output
    can provide information about the line and the character position in the line.
3.  TokenMapper uses a generated file to translate the integer token types to a readable format.
4.  Since there are two `InternalLexertraceLexer` classes to override I put the
    utility functions in an interface with default methods. Not the designated use of an
    interface but I prefer that to Util classes.
5.  Register the new `CustomLexer` and `CustomIdeLexer` classes in the Guice runtime
    modules `LexertraceRuntimeModule`, `LexertraceIdeModule`, and 
    `LexertraceUiModule` to be created instead of the `InternalLexertraceLexer` 
    classes
 
 The unit test now yields (don't forget the log4j.properties in the tests plugin):

	0    [main] DEBUG org.example.xtext.lexertrace.addon.TraceLexer.debugToken  - [@-1,0:4='Hello',<'Hello'>,1:0]
	0    [main] DEBUG org.example.xtext.lexertrace.addon.TraceLexer.debugToken  - [@-1,6:10='Xtext',<RULE_ID>,1:6]
	0    [main] DEBUG org.example.xtext.lexertrace.addon.TraceLexer.debugToken  - [@-1,11:11='!',<T__12>,1:11]
	0    [main] DEBUG org.example.xtext.lexertrace.addon.TraceLexer.debugToken  - [@-1,0:0='<no text>',<-1>,0:-1]
	16   [main] DEBUG org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser.parse  - Parsing took: 15 ms
	16   [main] DEBUG org.eclipse.xtext.linking.impl.AbstractCleaningLinker.linkModel  - beforeModelLinked took: 0ms
	31   [main] DEBUG org.eclipse.xtext.linking.impl.AbstractCleaningLinker.linkModel  - doLinkModel took: 0ms
	31   [main] DEBUG org.eclipse.xtext.linking.impl.AbstractCleaningLinker.linkModel  - afterModelLinked took: 0ms

##### Adding tracing to eclipse runtime workbench

The Xtext SDK provides a logging plugin: `org.eclipse.xtext.logging`. This plugin sets the logging
level of classes staring with `org.eclipse.xtext` to debug. Creating the logger in `TraceLexer` and
`TokenMapper` with a string starting with the same characters addes the trace output from `CustomLexer`
and `CustomIdeLexer` to the console output.

Additionally to your own lexer output you can find trace output from Xtext as well. I found this information
very helpful to understand how the editor works and where to put other extensions in place to modify the
lexer with customized synthetic terminals. 

## Building the example project

I use Eclipse as my IDE. I added some launch configurations to the project to simplify using
this example project. After you have cloned the repository using **EGit** you need to import all
projects (including the root) into your workspace.

After that you can use the preconfigured launch configurations:

* `lexertrace - Generate Language Infrastructure` 
  to generate the Xtext artifacts making up the language
* `lexertrace - unit test`
  to launch the JUnit test on the tests plugin
* `lexertrace - Launch Runtime Eclipse`
  to launch the Eclipse Runtime to test the language
* `lexertrace - mvn clean`
  to clean up the workspace
* `lexertrace - mvn clean package`
  to build the p2 repository
* `lexertrace - mvn tycho set version`
  to set a new version for the Maven Tycho build

## Setting up your own project

I used Eclipse Oxygen 1.a with Xtext 2.13 to create the project:

![New Xtext Project Wizard page 1](resources/new-xtext-project-1.png "New Xtext Project Wizard page 1") 
![New Xtext Project Wizard page 2](resources/new-xtext-project-2.png "New Xtext Project Wizard page 2")

This will create a basic Xtext project with a simple [Hello World grammar]
(https://www.eclipse.org/Xtext/documentation/102_domainmodelwalkthrough.html#write-the-grammar "Hello World grammar").   

I prefer to create the following GIT repository layout:

![GIT repository layout](resources/git-repository-layout.png "GIT repository layout")

This is unfortunately not possible to create with **EGit**. Therefore I use the following steps:

* Create or clone a new GIT repository
* Create the Xtext project using the wizards
* Delete the projects from the workspace (do **not** delete project contents on disk)
* Move the content of the maven parent project into the Working Tree directory
* Delete the (empty) parent project folder from the workspace
* Use EGit Import Projects to put the projects back into the workspace

Hopefully there will be someone on the **EGit** team to make this possible in the future. If you
currently share the parent project into a GIT repository you get a subfolder containing the
maven modules. **Never share the module projects! They are shared automatically with the parent.** 
 