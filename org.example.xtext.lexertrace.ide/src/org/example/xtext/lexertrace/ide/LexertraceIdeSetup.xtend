/*
 * generated by Xtext 2.13.0
 */
package org.example.xtext.lexertrace.ide

import com.google.inject.Guice
import org.eclipse.xtext.util.Modules2
import org.example.xtext.lexertrace.LexertraceRuntimeModule
import org.example.xtext.lexertrace.LexertraceStandaloneSetup

/**
 * Initialization support for running Xtext languages as language servers.
 */
class LexertraceIdeSetup extends LexertraceStandaloneSetup {

	override createInjector() {
		Guice.createInjector(Modules2.mixin(new LexertraceRuntimeModule, new LexertraceIdeModule))
	}
	
}
