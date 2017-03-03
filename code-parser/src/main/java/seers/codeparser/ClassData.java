package seers.codeparser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class ClassData {

	private String clName;
	private String qualifiedName;
	private List<FieldDeclaration> fields = new ArrayList<>();
	private List<MethodDeclaration> methods = new ArrayList<>();
	private List<Initializer> bodies = new ArrayList<>();
	
	private ASTNode classNode = null;
	private List<ClassData> anonymousClasses = new ArrayList<>();
	private String superClass;

	public void addField(FieldDeclaration node) {
		fields.add(node);
	}

	public void addMethod(MethodDeclaration node) {
		methods.add(node);
	}

	public List<MethodDeclaration> getMethods() {
		return methods;
	}

	public String getClName() {
		return clName;
	}

	public String getQualifiedName() {
		return qualifiedName;
	}

	public void setClName(String clName) {
		this.clName = clName;
	}

	public void setQualifiedName(String qualifiedName) {
		this.qualifiedName = qualifiedName;
	}

	public List<FieldDeclaration> getFields() {
		return fields;
	}

	public List<Initializer> getBodies() {
		return bodies;
	}

	public void addBody(Initializer node) {
		this.bodies.add(node);
	}

	public ASTNode getClassNode() {
		return classNode;
	}

	public void setClassNode(ASTNode classNode) {
		this.classNode = classNode;
	}

	public List<ClassData> getAnonymousClasses() {
		return anonymousClasses;
	}

	public void setAnonymousClasses(List<ClassData> anonymousClasses) {
		this.anonymousClasses = anonymousClasses;
	}

	public void addAnonymousClass(ClassData clData2) {
		anonymousClasses.add(clData2);
	}

	public String getSuperClass() {
		return superClass;
	}

	public void setSuperClass(String superClass) {
		this.superClass = superClass;
	}

}
