package paths;

public class TestPaths {

  public static void main(String[] args) {
    new TestPaths().run();
    
  }

  private void run() {
    GraphNode a = new GraphNode("a");
    GraphNode b = new GraphNode("b");
    GraphNode c = new GraphNode("c");
    GraphNode d = new GraphNode("d");
    GraphNode e = new GraphNode("e");
    GraphNode f = new GraphNode("f");
    GraphNode g = new GraphNode("g");
    
    a.pointTo(b);
    a.pointTo(d);
    b.pointTo(d);
    d.pointTo(c);
    d.pointTo(e);
    c.pointTo(f);
    f.pointTo(e);
    e.pointTo(g);
    
    generateTree(a);
  }

  private void generateTree(GraphNode a) {
    BTreeNode root = new BTreeNode();
    root.setValue(a.getValue());
    
    for(GraphNode n: a.getNodes()){
      
    }
    
  }
  
}
