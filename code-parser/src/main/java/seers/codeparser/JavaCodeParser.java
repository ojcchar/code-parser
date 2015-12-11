package seers.codeparser;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class JavaCodeParser {

	private ASTParser parser;
	private String[] classPaths;
	private String[] sourceFolders;
	private String[] encodings;

	public JavaCodeParser(String baseFolder, String[] classPaths, final String[] sourceFolders) {
		super();
		parser = ASTParser.newParser(AST.JLS8);

		this.sourceFolders = new String[sourceFolders.length];
		this.classPaths = classPaths;

		encodings = new String[sourceFolders.length];
		for (int i = 0; i < sourceFolders.length; i++) {
			encodings[i] = "UTF-8";
			this.sourceFolders[i] = FilenameUtils.separatorsToSystem(baseFolder + File.separator + sourceFolders[i]);
		}

		for (int i = 0; i < classPaths.length; i++) {
			this.classPaths[i] = FilenameUtils.separatorsToSystem(classPaths[i]);
		}

	}

	public CompilationUnit parseFile(File file) throws IOException {

		char[] fileContent = readFile(file);
		parser.setUnitName(file.getName());
		parser.setSource(fileContent);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);

		setParserConf();

		CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		return cu;

	}

	private char[] readFile(File file) throws IOException {
		String content = FileUtils.readFileToString(file);
		return content.toCharArray();
	}

	public void setParserConf() {
		@SuppressWarnings("unchecked")
		Map<String, String> options = JavaCore.getOptions();
		options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_8);
		options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_8);
		options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8);
		JavaCore.setComplianceOptions(JavaCore.VERSION_1_8, options);

		parser.setCompilerOptions(options);
		parser.setResolveBindings(true);

		parser.setEnvironment(classPaths, sourceFolders, encodings, true);
	}
}
