package paths;

import java.util.ArrayList;
import java.util.List;

public class GraphNode {

  private final List<GraphNode> nodes = new ArrayList<GraphNode>();
  
  private String value;
  
  public GraphNode(String value) {
    this.value = value;
  }

  public void pointTo(GraphNode b) {
    nodes.add(b);
  }

  public String getValue() {
    return value;
  }

  public List<GraphNode> getNodes() {
    return nodes;
  }

}
