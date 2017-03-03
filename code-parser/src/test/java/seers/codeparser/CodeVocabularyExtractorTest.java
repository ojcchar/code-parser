package seers.codeparser;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.junit.Test;

public class CodeVocabularyExtractorTest {

	@Test
	public void testExtractMethodVocabulary() throws IOException {

		String baseFolder ="classes";
		String[] classPaths ={};
		String[] sourceFolders ={};
		CodeVocabularyExtractor extractor = new CodeVocabularyExtractor(baseFolder, classPaths, sourceFolders);
		File javaFile = new File(baseFolder + File.separator + "GeneralVisitor2.java");
		
		HashMap<String, List<String>> vocabu = extractor.extractMethodVocabulary(javaFile);
		for (Entry<String, List<String>> entry : vocabu.entrySet()) {
			System.out.print(entry.getKey());
			System.out.print(" --> ");
			System.out.println(entry.getValue());
		}
	}

}
