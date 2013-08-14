package paths;

public class BTreeNode {

  private String value;
  private BTreeNode left;
  private BTreeNode right;

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public BTreeNode getLeft() {
    return left;
  }

  public void setLeft(BTreeNode left) {
    this.left = left;
  }

  public BTreeNode getRight() {
    return right;
  }

  public void setRight(BTreeNode right) {
    this.right = right;
  }

}
