
public interface Visitor {
	public boolean visit(WeakGhost weakGhost);
	public boolean visit(StrongGhost strongGhost);
	public boolean visit(RegularPacman regularPacman);
	public boolean visit(MightyPacman mightyPacman);
	public boolean visit(SuperPacman superPacman);
}
