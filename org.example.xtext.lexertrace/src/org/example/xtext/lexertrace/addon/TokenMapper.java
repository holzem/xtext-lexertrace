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

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.example.xtext.lexertrace.parser.antlr.internal.InternalLexertraceLexer;

/**
 * TokenMapper maps the token type to a readable string. It reads the mapping between the integer value in
 * {@link InternalLexertraceLexer} from the file <code>InternalLexertrace.tokens</code> which is stored in the same
 * package as the {@link InternalLexertraceLexer}.
 * 
 * @author Markus Holzem
 */
public class TokenMapper {
	private static final Logger logger = Logger.getLogger("org.eclipse.xtext.lexertrace.addon");
	private static final String TOKEN_RESOURCE = "/org/example/xtext/lexertrace/parser/antlr/internal/InternalLexertrace.tokens";

	private TokenMapper() {
		// do nothing
	}

	/** The map containing the mapping between integer values und strings */
	static private Map<Integer, String> _tokens = new HashMap<>();
	/** read the generated file as a property file and store the values in the map */
	static {
		final Properties properties = new Properties();
		try (final InputStream tokens = TokenMapper.class.getResourceAsStream(TOKEN_RESOURCE)) {
			properties.load(tokens);
		} catch (final IOException e) {
			logger.error("unable to read the resource " + TOKEN_RESOURCE);
			// do nothing, this is just development trace support
		}
		for (final Entry<Object, Object> property : properties.entrySet()) {
			final String token = (String) property.getKey();
			final Integer tokenvalue = Integer.parseInt((String) property.getValue());
			_tokens.put(tokenvalue, token);
		}
	}

	/**
	 * Return the text representation for the token type
	 * 
	 * @param pType
	 *            the integer value of the token type
	 * @return the string representation.
	 */
	public static String getToken(final int pType)
	{
		final String s = _tokens.get(pType);
		if (s != null)
			return s;
		return Integer.toString(pType);
	}

}
