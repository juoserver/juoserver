package net.sf.juoserver.api;

public interface ItemVisitor extends ContextRequired {
	void visit(Item item);
	void visit(Container item);
}
