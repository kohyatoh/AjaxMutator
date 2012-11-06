package jp.gr.java_conf.daisy.ajax_mutator.mutator;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.gr.java_conf.daisy.ajax_mutator.mutatable.Mutatable;

import org.mozilla.javascript.ast.AstNode;

/**
 * Provide utility methods to implement Mutator
 *  
 * @author Kazuki Nishiura
 */
public abstract class AbstractMutator<T extends Mutatable> implements Mutator {
	protected PrintStream stream;
	protected List<T> mutationTargets;
	protected List<AstNode> mutatedElements;
	protected int targetIndex = 0;
	
	protected AbstractMutator(
			PrintStream printStream, Collection<T> mutationTargets) {
		this.stream = printStream;
		this.mutationTargets = new ArrayList<T>(mutationTargets);
		mutatedElements = new ArrayList<AstNode>(this.mutationTargets.size());
		for (T attachment: this.mutationTargets) {
			mutatedElements.add(getFocusedNode(attachment));
		}
	}
	
	/**
	 * @return focus of Mutator object. If the class try to mutate event kind
	 * from event attachment statement like 
	 * 'element.addEventListener('click', func);', this method must return
	 * something like 'click'.
	 */
	abstract protected AstNode getFocusedNode(T node);
	
	/**
	 * e.g., 
	 * In case of event kind mutator, 'parant' param would be
	 * 'element.addEventListener('click', func);' and 'newPart' param would be
	 * 'mouseover'.
	 */
	abstract protected void replaceFocusedNodeOf(T parent, AstNode newPart);
	
	@Override
	public boolean applyMutation() {
		T mutationTarget = mutationTargets.get(targetIndex);
		Set<AstNode> equivalents = new HashSet<AstNode>();
		equivalents.add(getFocusedNode(mutationTarget));
		AstNode newtype = null;
		while (equivalents.size() < mutatedElements.size()) {
			newtype = mutatedElements.get((int) Math.floor(Math.random() * mutatedElements.size()));
			if (ifEquals(getFocusedNode(mutationTarget), newtype))
				equivalents.add(newtype);
			else
				break;
		}
		if (newtype == null) {
			System.out.println(mutationTarget.toString());
			System.out.println(mutatedElements.get(0).toSource());
			System.out.println(mutatedElements.size());
			System.out.println("not applied");
			return false;
		}
		printMutationInformation(getFocusedNode(mutationTarget), newtype);
		replaceFocusedNodeOf(mutationTarget, newtype);
		return true;
	}

	@Override
	public void undoMutation() {
		Mutatable mutationTarget = mutationTargets.get(targetIndex);
		mutationTarget.undoLastReplace();
		targetIndex++;
	}

	@Override
	public boolean isFinished() {
		return mutationTargets.size() <= targetIndex;
	}
	
	protected void printMutationInformation(AstNode from, AstNode to) {
		if (stream != null) {
			StringBuilder builder = new StringBuilder();
			AstNode parent = from.getParent();
			builder.append("mutate '");
			builder.append(from.toSource());
			builder.append("' in \"");
			builder.append(parent.toSource());
			builder.append("\" (at line ");
			builder.append(parent.getLineno());
			builder.append(") -> '");
			builder.append(to.toSource());
			builder.append("'");
			stream.println(builder);
		}
	}
	
	protected boolean ifEquals(AstNode node1, AstNode node2) {
		return node1.toSource().equals(node2.toSource());
	}
}
