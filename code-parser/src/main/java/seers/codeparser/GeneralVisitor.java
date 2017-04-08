package seers.codeparser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FIXME: INNER METHODS ARE TAKEN AS PART OF THE OUTTER CLASS
 * 
 * @author ojcch
 *
 */
public class GeneralVisitor extends ASTVisitor {

	private static final Logger LOGGER = LoggerFactory.getLogger(GeneralVisitor.class);

	List<ClassData> classesInfo = new ArrayList<>();

	public GeneralVisitor() {
	}

	public List<ClassData> getClassesInformation() {
		return classesInfo;
	}

	@Override
	public boolean visit(TypeDeclaration node) {
		addClassess(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(EnumDeclaration node) {
		addClassess(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(AnnotationTypeDeclaration node) {
		addClassess(node);
		return super.visit(node);
	}

	public void addClassess(AbstractTypeDeclaration node) {

		final ClassData clData = new ClassData();

		ITypeBinding resolveBinding = node.resolveBinding();

		if (resolveBinding == null) {
			LOGGER.warn("No binding for : " + node.getName());
			return;
		}

		String qualifiedName = resolveBinding.getQualifiedName();

		node.accept(new ASTVisitor() {

			@Override
			public boolean visit(FieldDeclaration node) {
				clData.addField(node);
				return true;
			}

			@Override
			public boolean visit(MethodDeclaration node) {
				clData.addMethod(node);
				return true;
			}

			@Override
			public boolean visit(Initializer node) {
				clData.addBody(node);
				return true;
			}

		});
		String className = node.getName().toString();

		clData.setClName(className);
		clData.setQualifiedName(qualifiedName);
		clData.setClassNode(node);

		classesInfo.add(clData);

	}
}
