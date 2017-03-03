package seers.commons.visitor;

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

import seers.commons.bean.ClassData;


public class GeneralVisitor2 extends ASTVisitor {

	List<ClassData> classesInfo = new ArrayList<>();
	
	static{
		//this is body 1
	}
	
	static{
		//this is body 2
	}

	public GeneralVisitor2() {
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
	
	/** (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.AnnotationTypeDeclaration)
	 */
	@Override
	public boolean visit(AnnotationTypeDeclaration node) {
		addClassess(node);
		return super.visit(node);
	}

	public void addClassess(AbstractTypeDeclaration node) {

		final ClassData clData = new ClassData();

		ITypeBinding resolveBinding = node.resolveBinding();
		String qualifiedName = resolveBinding.getQualifiedName();

		node.accept(new ASTVisitor() {

			@Override
			//this is a comment
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
	
	public class InnerClass{

		public void innerMethod(AbstractTypeDeclaration node) {
			//this is inner method
			System.out.println("this is a literal");
			int a = 20;
		}
	}
}
