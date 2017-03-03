package seers.codeparser;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;

public class AstUtils {

	public static String getQualifiedName(ASTNode astNode, ClassData clData, int idx) {
		if (astNode instanceof MethodDeclaration) {
			return clData.getQualifiedName() + ":" + getMethodSignature((MethodDeclaration) astNode);
		} else {
			return clData.getQualifiedName() + ":<clinit" + idx + ">";
		}
	}

	@SuppressWarnings("unchecked")
	public static String getMethodSignature(MethodDeclaration methDecl) {
		StringBuffer buffer = new StringBuffer(methDecl.getName().toString());

		buffer.append("(");
		List<SingleVariableDeclaration> parameters = methDecl.parameters();
		for (SingleVariableDeclaration varDecl : parameters) {
			String type = varDecl.getType().toString();
			buffer.append(type);
			buffer.append(", ");
		}
		if (!parameters.isEmpty()) {
			buffer.delete(buffer.length() - 2, buffer.length());
		}
		buffer.append(")");

		return buffer.toString();
	}

	public static String getMethodSignatureNoParamtype(MethodDeclaration methDecl) {
		StringBuffer buffer = new StringBuffer(methDecl.getName().toString());

		buffer.append("(");
		@SuppressWarnings("unchecked")
		List<SingleVariableDeclaration> parameters = methDecl.parameters();
		for (SingleVariableDeclaration varDecl : parameters) {
			Type type2 = varDecl.getType();

			boolean varargs = varDecl.isVarargs();

			String type = type2.toString();
			if (type2 instanceof ParameterizedType) {
				type = ((ParameterizedType) type2).getType().toString();
			}

			if (varargs) {
				type += "[]";
			}

			buffer.append(type);
			buffer.append(", ");
		}
		if (!parameters.isEmpty()) {
			buffer.delete(buffer.length() - 2, buffer.length());
		}
		buffer.append(")");

		return buffer.toString();
	}

}
