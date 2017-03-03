package seers.codeparser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.StringLiteral;

public class IdentifiersVisitor extends ASTVisitor {

	private List<String> textItems = new ArrayList<>();

	@Override
	public boolean visit(SimpleName node) {
		textItems.add(node.getIdentifier());
		return super.visit(node);
	}

	@Override
	public boolean visit(StringLiteral node) {
		textItems.add(node.getLiteralValue());
		return super.visit(node);
	}

	public List<String> getTextItems() {
		return textItems;
	}

}
