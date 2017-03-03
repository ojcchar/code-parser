package seers.codeparser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.TagElement;

public class JavadocVisitor extends ASTVisitor {

	private List<String> textItems = new ArrayList<>();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean visit(Javadoc node) {
		List<TagElement> tags = node.tags();

		for (TagElement tagEl : tags) {

			List fragments = tagEl.fragments();

			StringBuffer comment = new StringBuffer();
			for (Object fragment : fragments) {
				comment.append(fragment);
				comment.append(" ");
			}
			textItems.add(comment.toString().trim());

		}
		return false;
	}

	public List<String> getTextItems() {
		return textItems;
	}

}
