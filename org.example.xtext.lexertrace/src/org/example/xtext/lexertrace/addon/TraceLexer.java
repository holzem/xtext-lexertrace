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
package org.example.xtext.lexertrace.addon;

import org.antlr.runtime.CommonToken;
import org.apache.log4j.Logger;
import org.example.xtext.lexertrace.parser.antlr.internal.InternalLexertraceLexer;

/**
 * TraceLexer is implemented as an interface so that it can be used in CustomLexer and in CustomIdeLexer in the ide
 * plugin.
 * 
 * @author Markus Holzem
 */
public interface TraceLexer {

	// /* private */ static final Logger logger = Logger.getLogger(TraceLexer.class);
	/* private */ static final Logger logger = Logger.getLogger("org.eclipse.xtext.lexertrace.addon");

	/**
	 * Private helper method to create a readable debug output of the given token.
	 * 
	 * @param pToken
	 *            the token to log
	 */
	default void debugToken(final CommonToken pToken)
	{
		String channelStr = "";
		if (pToken.getChannel() > 0) {
			channelStr = ",channel=" + pToken.getChannel();
		}
		String txt = pToken.getText();
		if (txt != null) {
			txt = txt.replaceAll("\n", "\\\\n");
			txt = txt.replaceAll("\r", "\\\\r");
			txt = txt.replaceAll("\t", "\\\\t");
		} else {
			txt = "<no text>";
		}
		final int type = pToken.getType();
		final String debugtext = "[@" + pToken.getTokenIndex() + "," + pToken.getStartIndex() + ":"
				+ pToken.getStopIndex() + "='" + txt + "',<" + TokenMapper.getToken(type) + ">" + channelStr + ","
				+ pToken.getLine() + ":" + pToken.getCharPositionInLine() + "]";
		if (type == InternalLexertraceLexer.RULE_WS || type == InternalLexertraceLexer.RULE_SL_COMMENT
				|| type == InternalLexertraceLexer.RULE_ML_COMMENT)
			logger.trace(debugtext);
		else
			logger.debug(debugtext);
	}

}
