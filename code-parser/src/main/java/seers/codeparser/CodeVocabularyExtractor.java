package seers.codeparser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.BlockComment;
import org.eclipse.jdt.core.dom.Comment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class CodeVocabularyExtractor {

	private JavaCodeParser codeParser;

	public CodeVocabularyExtractor(String baseFolder, String[] classPaths, final String[] sourceFolders) {
		codeParser = new JavaCodeParser(baseFolder, classPaths, sourceFolders);
	}

	public HashMap<String, List<String>> extractMethodVocabulary(File javaFile) throws IOException {

		CompilationUnit cu = codeParser.parseFile(javaFile);

		GeneralVisitor vis = new GeneralVisitor();
		cu.accept(vis);
		List<ClassData> clInfo = vis.getClassesInformation();

		HashMap<String, List<String>> methodsTokens = new HashMap<>();

		// --------------------

		@SuppressWarnings("unchecked")
		List<Comment> inlineComments = ((List<Comment>) cu.getCommentList()).stream()
				.filter(c -> (!(c instanceof Javadoc))).collect(Collectors.toList());
		String fileContent = null;
		if (!inlineComments.isEmpty()) {
			fileContent = FileUtils.readFileToString(javaFile);
		}

		for (ClassData classData : clInfo) {

			List<MethodDeclaration> methods = classData.getMethods();
			for (MethodDeclaration method : methods) {
				List<String> tokens = getJavadocComments(method);
				tokens.addAll(getInlineComments(method, inlineComments, fileContent));
				tokens.addAll(getIdentifiers(method));

				String qName = AstUtils.getQualifiedName(method, classData, 0);
				methodsTokens.put(qName, tokens);
			}

			List<Initializer> bodies = classData.getBodies();
			for (int i = 0; i < bodies.size(); i++) {
				Initializer body = bodies.get(i);
				List<String> tokens = getJavadocComments(body);
				tokens.addAll(getInlineComments(body, inlineComments, fileContent));
				tokens.addAll(getIdentifiers(body));

				String qName = AstUtils.getQualifiedName(body, classData, i);
				methodsTokens.put(qName, tokens);
			}

		}

		return methodsTokens;
	}

	private List<String> getInlineComments(ASTNode method, List<Comment> inlineComments, String fileContent) {

		ArrayList<String> textItems = new ArrayList<>();
		if (fileContent == null) {
			return textItems;
		}

		List<Comment> comments = inlineComments.stream().filter((comment) -> {

			int start = comment.getStartPosition();
			int end = start + comment.getLength();

			int stPosition = method.getStartPosition();
			int endPosition = stPosition + method.getLength();

			if (stPosition <= start && end <= endPosition) {
				return true;
			}

			return false;
		}).collect(Collectors.toList());

		for (Comment comment : comments) {
			String commentContent = extractComment(comment, fileContent);
			textItems.add(commentContent);
		}

		return textItems;
	}

	private String extractComment(Comment comment, String fileContent) {

		int start = comment.getStartPosition();
		int end = start + comment.getLength();

		int offset = 0;
		if (comment instanceof BlockComment) {
			offset = 2;
		}
		String commentTxt = fileContent.substring(start + 2, end - offset);
		return commentTxt;
	}

	private List<String> getJavadocComments(ASTNode node) {

		if (node == null) {
			return new ArrayList<>();
		}

		JavadocVisitor identifiersVisitor = new JavadocVisitor();
		node.accept(identifiersVisitor);
		List<String> textItems = identifiersVisitor.getTextItems();
		return textItems;
	}

	private List<String> getIdentifiers(ASTNode node) {

		if (node == null) {
			return new ArrayList<>();
		}

		IdentifiersVisitor identifiersVisitor = new IdentifiersVisitor();
		node.accept(identifiersVisitor);
		List<String> textItems = identifiersVisitor.getTextItems();
		return textItems;
	}
}
