package de.danielnaber.languagetool.rules.uk;

import java.io.IOException;

import junit.framework.TestCase;
import de.danielnaber.languagetool.JLanguageTool;
import de.danielnaber.languagetool.Language;
import de.danielnaber.languagetool.TestTools;
import de.danielnaber.languagetool.rules.RuleMatch;

public class PunctuationCheckRuleTest extends TestCase {

	public void testRule() throws IOException {
		PunctuationCheckRule rule = new PunctuationCheckRule(TestTools.getEnglishMessages());

		RuleMatch[] matches;
		JLanguageTool langTool = new JLanguageTool(Language.UKRAINIAN);
		
		// correct sentences:
		matches = rule.match(langTool.getAnalyzedSentence("Дві, коми. Ось: дві!!!"));
		assertEquals(0, matches.length);

		// correct sentences:
		matches = rule.match(langTool.getAnalyzedSentence("- Це ваша пряма мова?!!"));
		assertEquals(0, matches.length);

		// correct sentences:
		matches = rule.match(langTool.getAnalyzedSentence("Дві,- коми!.."));
		assertEquals(0, matches.length);

		// correct sentences:
		matches = rule.match(langTool.getAnalyzedSentence("Два  пробіли."));	// поки що ігноруємо - не царська це справа :)
		assertEquals(0, matches.length);

		// incorrect sentences:
		matches = rule.match(langTool.getAnalyzedSentence("Дві крапки.."));
		assertEquals(1, matches.length);
		assertEquals(1, matches[0].getSuggestedReplacements().size());
		assertEquals(".", matches[0].getSuggestedReplacements().get(0));

		// incorrect sentences:
		matches = rule.match(langTool.getAnalyzedSentence("Дві,, коми."));
		assertEquals(1, matches.length);

		// incorrect sentences:
		matches = rule.match(langTool.getAnalyzedSentence("Не там ,кома."));
		assertEquals(1, matches.length);

		// incorrect sentences:
		matches = rule.match(langTool.getAnalyzedSentence("Двокрапка:- з тире."));
		assertEquals(1, matches.length);
	}
}
