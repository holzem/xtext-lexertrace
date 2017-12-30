/*
 * MIT License
 *
 * Copyright (c) 2017 Markus Holzem
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.example.xtext.lexertrace.ide.addon;

import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.example.xtext.lexertrace.addon.TraceLexer;
import org.example.xtext.lexertrace.ide.contentassist.antlr.internal.InternalLexertraceLexer;

/**
 * CustomIdeLexer behaves like {@link InternalLexertraceLexer} but adds the debug output.
 * 
 * @author Markus Holzem
 */
public class CustomIdeLexer extends InternalLexertraceLexer implements TraceLexer {
	/**
	 * Create CustomIdeLexer
	 */
	public CustomIdeLexer() {
		super();
	}

	/**
	 * Create CustomIdeLexer
	 * 
	 * @param pInput
	 *            the {@link CharStream}
	 * @param pState
	 *            the {@link RecognizerSharedState}
	 */
	public CustomIdeLexer(final CharStream pInput, final RecognizerSharedState pState) {
		super(pInput, pState);
	}

	/**
	 * Create CustomIdeLexer
	 * 
	 * @param pInput
	 *            the {@link CharStream}
	 */
	public CustomIdeLexer(final CharStream pInput) {
		super(pInput);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.xtext.parser.antlr.Lexer#nextToken()
	 */
	@Override
	public Token nextToken()
	{
		// all generated Antlr tokens are of type CommonToken
		final CommonToken ct = (CommonToken) super.nextToken();
		debugToken(ct);
		return ct;
	}

}
