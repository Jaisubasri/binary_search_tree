import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class BSTVisualization extends JFrame implements ActionListener, KeyListener
{
    // Tree Root Node.
    private Node root;
    // private Color color;
    private JPanel topPanel, treePanel, infoPanel;
    private JPanel topLeftPanel, topRightPanel;
    private JButton btnAdd, btnDelete,btnSearch;
    private JTextField tf;
    private int X = 300, Y = 75;
    private Graphics2D g2;
    private Rectangle size;
    private JLabel labelInorder, labelPreorder, labelPostorder, labelHeight;
    private JLabel ansInorder, ansPreorder, ansPostorder, ansHeight;
    private FontMetrics fontMatrix;

    //Node Structure
    private static class Node {
        static int TEXT_WIDTH = 40;
        static int TEXT_HEIGHT = 40;

        JLabel data;
        Node left;
        Node right;
        Points p;

        Node(int info)
        {
            data = new JLabel(info + "", SwingConstants.CENTER);
            data.setFont(new Font("Arial", Font.BOLD, 20));
            data.setBorder(BorderFactory.createLineBorder(Color.yellow));
            data.setOpaque(true);
            data.setBackground(Color.CYAN);
            p = null;
        }
    }

    //Points structure
    private static class Points
    {
        int x1 = 0, x2 = 0, y2 = 0, y1 = 0;
        Points(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.x2 = x2;
            this.y2 = y2;
            this.y1 = y1;
        }

        public String toString() {
            return "x1 = " + x1 + ", y1 = " + y1 + ", x2 = " + x2 + ", y2 = " + y2;
        }
    }

    // For storing the Height of the root,left and right child height.
    private static class Height
    {
        int root, left, right;

        Height() {
            this.root = 0;
            this.left = 0;
            this.right = 0;
        }

        Height(int left, int right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return Integer.toString(this.root);
        }
    }

    public void paint(Graphics g)
    {
        super.paintComponents(g);
        g.setColor(Color.LIGHT_GRAY);
        g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3.0f));

        Stack<Node> s = new Stack<>();
        Node curr = root;
        Points pts;
        int offset = topPanel.getBounds().height;
        while (!s.isEmpty() || curr != null)
        {
            while (curr != null) {
                s.push(curr);
                curr = curr.left;
            }
            if (!s.isEmpty())
                curr = s.pop();
            pts = curr.p;
            g2.drawLine(pts.x1 + 7, pts.y1 + 30 + offset, pts.x2 + 3, pts.y2 + 10 + offset);
            curr = curr.right;
        }

        // x1 = label.getX()+7
        // y1 = label.getY()+30
    }

    public BSTVisualization()
    {
        // Initialize the frame.
        initialize();
    }

    private void initialize() {

        // setLayout(null); // layout
        setSize(1200, 700); //frame size

        size = getBounds();
        X = size.width / 2;

        topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.black);
        Rectangle top = topPanel.getBounds();

        topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topLeftPanel.setBackground(Color.black);
        topPanel.add(topLeftPanel, BorderLayout.WEST);

        topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topRightPanel.setBackground(Color.black);
        topPanel.add(topRightPanel, BorderLayout.EAST);

        treePanel = new JPanel(null);
        treePanel.setBackground(Color.black);
        treePanel.setPreferredSize(new Dimension(size.width, size.height - 300));

        infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setPreferredSize(new Dimension(size.width, 200));
        infoPanel.setBackground(Color.black);

        // Height of BST label
        labelHeight = new JLabel("BST HEIGHT : ");
        labelHeight.setFont(new Font("Calibri", Font.BOLD, 20));
        labelHeight.setForeground(Color.WHITE);
        topLeftPanel.add(labelHeight);

        // Height of BST answer
        ansHeight = new JLabel("0");
        ansHeight.setFont(new Font("Calibri", Font.BOLD, 20));
        ansHeight.setPreferredSize(new Dimension(50, 30));
        ansHeight.setForeground(Color.WHITE);
        topLeftPanel.add(ansHeight);

        //For geting data.
        tf = new JTextField("");
        tf.setFont(new Font("Arial", Font.BOLD, 20));
        tf.setPreferredSize(new Dimension(150, 30));
        tf.addKeyListener(this);
        topRightPanel.add(tf);

        //Add Button
        btnAdd = new JButton("ADD");
        btnAdd.setFont(new Font("Arial", Font.BOLD, 20));
        btnAdd.setForeground(Color.BLACK);
        // btnAdd.setBounds(size.width - 130, 20, 100, 30);
        btnAdd.addActionListener(this);
        topRightPanel.add(btnAdd);

        //Delete Button
        btnDelete = new JButton("DELETE");
        btnDelete.setFont(new Font("Arial", Font.BOLD, 20));
        btnDelete.setForeground(Color.BLACK);
        // btnDelete.setBounds(size.width - 130, 60, 100, 30);
        btnDelete.addActionListener(this);
        topRightPanel.add(btnDelete);

        //Search Button
        btnSearch = new JButton("SEARCH");
        btnSearch.setFont(new Font("Arial", Font.BOLD, 20));
        btnSearch.setForeground(Color.BLACK);
        // btnSearch.setBounds(size.width - 130, 60, 100, 30);
        btnSearch.addActionListener(this);
        topRightPanel.add(btnSearch);

        // Inorder label
        labelInorder = new JLabel("INORDER :");
        labelInorder.setFont(new Font("Times New Roman", Font.BOLD, 20));
        labelInorder.setForeground(Color.WHITE);
        infoPanel.add(labelInorder);

        infoPanel.add(Box.createRigidArea(new Dimension(7, 5)));

        // Inorder traversal answer
        ansInorder = new JLabel("BST IS EMPTY!!!.");
        ansInorder.setFont(new Font("Arial", Font.PLAIN, 18));
        ansInorder.setForeground(Color.WHITE);
        infoPanel.add(ansInorder);

        infoPanel.add(Box.createRigidArea(new Dimension(7, 15)));

        // Preorder label
        labelPreorder = new JLabel("PREORDER :");
        labelPreorder.setFont(new Font("Times New Roman", Font.BOLD, 20));
        labelPreorder.setForeground(Color.WHITE);
        infoPanel.add(labelPreorder);

        infoPanel.add(Box.createRigidArea(new Dimension(7, 5)));

        // Preorder traversal answer
        ansPreorder = new JLabel("BST IS EMPTY!!!");
        ansPreorder.setFont(new Font("Arial", Font.PLAIN, 18));
        ansPreorder.setForeground(Color.WHITE);
        infoPanel.add(ansPreorder);

        infoPanel.add(Box.createRigidArea(new Dimension(7, 15)));

        // Postorder label
        labelPostorder = new JLabel("POSTORDER :");
        labelPostorder.setFont(new Font("Times New Roman", Font.BOLD, 20));
        labelPostorder.setForeground(Color.WHITE);
        infoPanel.add(labelPostorder);

        // Postorder traversal answer
        ansPostorder = new JLabel("BST IS EMPTY!!!");
        ansPostorder.setFont(new Font("Arial", Font.PLAIN, 18));
        ansPostorder.setForeground(Color.WHITE);
        infoPanel.add(ansPostorder);

        tf.requestFocusInWindow();

        add(topPanel, BorderLayout.NORTH);
        add(treePanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);

        setTitle("BINARY SEARCH VISUALIZATION"); //Title Frame
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent evt)
    {
        if (tf.isEnabled()) {
            try {
                int data = Integer.parseInt(tf.getText());
                if (evt.getSource() == btnAdd) {
                    add(data);
                } else if(evt.getSource() == btnDelete){
                    delete(data);
                }
                else
                {
                    Search(data);
                }
                tf.setText("");
                tf.requestFocusInWindow();
            } catch (Exception e)
            {
                JOptionPane.showMessageDialog(null, "PLEASE ENTER INTEGER!!!","ERROR MESSAGE",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent evt) {
        char c = evt.getKeyChar();
        if (!tf.isEnabled()) {
            return;
        } else if (c == 'a' || c == 'A' || c == '\n') {
            try {
                String data = tf.getText();
                evt.consume(); // Not type 'a' or 'A' character in textfield
                if (!data.isEmpty()) {
                    add(Integer.parseInt(data));
                } else {
                    throw new Exception();
                }
                tf.requestFocusInWindow();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "PLEASE ENTER INTEGER!!!","ERROR MESSAGE",JOptionPane.ERROR_MESSAGE);
            }
            tf.setText("");
        } else if (c == 'd' || c == 'D') {
            try {
                String data = tf.getText();
                evt.consume(); // Not type 'd' or 'D' character in textfield
                if (!data.isEmpty()) {
                    delete(Integer.parseInt(data));
                }
                tf.requestFocusInWindow();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "PLEASE ENTER INTEGER!!!","ERROR MESSAGE",JOptionPane.ERROR_MESSAGE);
            }
            tf.setText("");
        }else if(c=='s' || c=='S')
        {
            try {
                String data = tf.getText();
                evt.consume(); // Not type 'a' or 'A' character in textfield
                if (!data.isEmpty()) {
                    Search(Integer.parseInt(data));
                } else {
                    throw new Exception();
                }
                tf.requestFocusInWindow();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "PLEASE ENTER INTEGER!!!","ERROR MESSAGE",JOptionPane.ERROR_MESSAGE);
            }
            tf.setText("");
        }
        else if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z')
            evt.consume();
    }

    @Override
    public void keyPressed(KeyEvent evt) {
    }

    @Override
    public void keyReleased(KeyEvent evt) {
    }

    //Add element in BST.
    public void add(int info) {
        Node newNode = new Node(info);
        int width = getWidth(newNode);

        if (root == null) {
            root = newNode;
            newNode.data.setBounds(treePanel.getBounds().width / 2, 10, width, 40);
            newNode.p = new Points(0, 0, 0, 0);
        } else {
            Node curr = root, pre = root;
            int currData;
            X = treePanel.getBounds().width / 2;
            while (curr != null) {
                pre = curr;
                currData = Integer.parseInt(curr.data.getText());
                if (info == currData) {
                    JOptionPane.showMessageDialog(null, "IS ALREADY EXIST!!!","ERROR MESSAGE",JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (currData > info) {
                    curr = curr.left;
                } else {
                    curr = curr.right;
                }
                X /= 2;
            }

            currData = Integer.parseInt(pre.data.getText());
            int x = pre.data.getX();
            int y = pre.data.getY();
            Dimension preDimension = pre.data.getSize();
            Dimension currDimension = new Dimension(width, Node.TEXT_HEIGHT);

            if (currData > info) {
                pre.left = newNode;
                newNode.data.setBounds(x - X, y + Y, width, 40);
                // x1=x;y1=y+20;x2=x-X+20;y2=y+Y+20;
                newNode.p = new Points(x, y + preDimension.height / 2, x - X + currDimension.width / 2, y + Y + currDimension.height / 2);
            } else {
                pre.right = newNode;
                newNode.data.setBounds(x + X, y + Y, width, 40);
                // x1=x+40;y1=y+20;x2=x+X+20;y2=y+Y+20;
                newNode.p = new Points(x + preDimension.width, y + preDimension.height / 2, x + X + currDimension.width / 2, y + Y + currDimension.height / 2);
            }
        }

        // Set all traversal and height of BST
        setInfo();

        // paint(treePanel.getGraphics());
        treePanel.add(newNode.data);
        treePanel.validate();
        treePanel.repaint();

        validate();
        repaint();
    }

    // Delete Node from BST
    public void delete(int data) {
        if (root == null) {
            JOptionPane.showMessageDialog(null, "BST IS EMPTY.");
        } else {
            Node curr = root, pre = root;

            while (curr != null) {
                int info = Integer.parseInt(curr.data.getText());
                if (info == data) {
                    break;
                } else if (info > data) {
                    pre = curr;
                    curr = curr.left;
                } else {
                    pre = curr;
                    curr = curr.right;
                }
            }

            if (curr == null) { // data is not find.
                JOptionPane.showMessageDialog(null, "DATA IS NOT AVAILABLE!!!","ERROR MESSAGE",JOptionPane.ERROR_MESSAGE);
                return;
            } else if (curr.left == null || curr.right == null) { // data has 0 or 1 child

                treePanel.remove(curr.data);
                treePanel.validate();
                treePanel.repaint();

                validate();
                repaint();

                if (curr != root) {
                    Node address = curr.left != null ? curr.left : curr.right;
                    // curr.data>pre.data
                    int preData = Integer.parseInt(pre.data.getText());
                    int currData = Integer.parseInt(curr.data.getText());
                    if (currData > preData) {
                        pre.right = address;
                    } else {
                        pre.left = address;
                    }
                } else {
                    if (curr.left != null) {
                        root = curr.left;
                    } else {
                        root = curr.right;
                    }
                }

            } else { // data has 2 child.

                treePanel.remove(curr.data);
                treePanel.validate();
                treePanel.repaint();

                validate();
                repaint();

            /*
             It set another node depending upon the height of left and right sub tree.
             */
                Node nextRoot = null, preRoot = curr;
                Height height = calculateHeight(curr);

                /* For taking maximum element from the left Side. */
                if (height.left > height.right) {
                    nextRoot = curr.left;
                    while (nextRoot.right != null) {
                        preRoot = nextRoot;
                        nextRoot = nextRoot.right;
                    }

                    if (preRoot != curr) {
                        preRoot.right = nextRoot.left;
                    } else {
                        preRoot.left = nextRoot.left;
                    }
                } else { /* For taking minimum element from the right Side.*/
                    nextRoot = curr.right;
                    while (nextRoot.left != null) {
                        preRoot = nextRoot;
                        nextRoot = nextRoot.left;
                    }

                    if (preRoot != curr) {
                        preRoot.left = nextRoot.right;
                    } else {
                        preRoot.right = nextRoot.right;
                    }
                }

                curr.data = nextRoot.data;
            }
            reArrangeNode(root, root, treePanel.getBounds().width / 2);
        }

        // Set all traversal and height of BST
        setInfo();
    }
    public void Search(int info)
    {
            Node curr = root, pre = root;
            int currData,i=1;
            X = treePanel.getBounds().width / 2;
            while (curr != null)
            {
                pre = curr;
                currData = Integer.parseInt(curr.data.getText());
                if (info == currData) {
                    JOptionPane.showMessageDialog(null, "THE ELEMENT EXIST!!!", "ERROR MESSAGE", JOptionPane.INFORMATION_MESSAGE);
                    i=0;
                    return;
                }else if (currData > info) {
                    curr = curr.left;
                } else {
                    curr = curr.right;
                }
                X /= 2;
            }
            if(i==1)
            {
                JOptionPane.showMessageDialog(null, "THE ELEMENT DOES NOT EXIST!!!", "ERROR MESSAGE", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            treePanel.validate();
            treePanel.repaint();
            validate();
            repaint();
    }

    // Set all traversal and height of BST
    private void setInfo() {
        Height height = calculateHeight(root);

        if (height.root == 0) {
            ansInorder.setText("BST IS EMPTY");
            ansPostorder.setText("BST IS EMPTY");
            ansPreorder.setText("BST IS EMPTY");
        } else {
            ansInorder.setText(inorder(root));
            ansPostorder.setText(postorder(root));
            ansPreorder.setText(preorder(root));
        }

        ansHeight.setText(height.root + "");
    }

    private int getWidth(Node node) {
        fontMatrix = getFontMetrics(node.data.getFont());
        int width = fontMatrix.stringWidth(node.data.getText());
        return width < Node.TEXT_WIDTH ? Node.TEXT_WIDTH : (width + 5);
    }

    //Inorder logic
    private String inorder(Node root) {
        if (root == null)
            return "";

        return inorder(root.left) + root.data.getText() + " " + inorder(root.right);
    }

    //Preorder logic
    public String preorder(Node root) {
        if (root == null)
            return "";

        return root.data.getText() + " " + preorder(root.left) + preorder(root.right);
    }

    //Postorder logic
    public String postorder(Node root) {
        if (root == null)
            return "";

        return postorder(root.left) + postorder(root.right) + root.data.getText() + " ";
    }

    // Calculate Height of BST using recursive method.
    private Height calculateHeight(Node root) {
        if (root == null) {
            return new Height();
        }
        Height leftChild = calculateHeight(root.left);
        Height rightChild = calculateHeight(root.right);
        Height current = new Height(leftChild.root, rightChild.root);
        current.root = 1 + Math.max(leftChild.root, rightChild.root);
        return current;
    }

    // Rearrange nodes
    private void reArrangeNode(Node node, Node pre, int X) {
        if (node == null)
            return;

        int width = getWidth(node);

        if (root == node) {
            node.data.setBounds(X, 10, width, Node.TEXT_HEIGHT);
        } else {
            int x = pre.data.getX();
            int y = pre.data.getY();
            Dimension preDimension = pre.data.getSize();
            Dimension currDimension = new Dimension(width, Node.TEXT_HEIGHT);

            int preData = Integer.parseInt(pre.data.getText());
            int nodeData = Integer.parseInt(node.data.getText());
            if (nodeData < preData) {
                node.data.setBounds(x - X, y + Y, width, Node.TEXT_HEIGHT);
                node.p = new Points(x, y + preDimension.height / 2, x - X + currDimension.width / 2, y + Y + currDimension.height / 2);
            } else {
                node.data.setBounds(x + X, y + Y, width, Node.TEXT_HEIGHT);
                // node.p = new Points(x + 40, y + 20, x + X + 20, y + Y + 20);
                node.p = new Points(x + preDimension.width, y + preDimension.height / 2, x + X + currDimension.width / 2, y + Y + currDimension.height / 2);
            }
        }

        reArrangeNode(node.left, node, X / 2);
        reArrangeNode(node.right, node, X / 2);
    }

    public static void main(String arg[])
    {
        BSTVisualization bst = new BSTVisualization();
        /*bst.add(500);
        bst.add(250);
        bst.add(350);
        bst.add(200);
        bst.add(750);
        bst.add(1000);
        bst.add(700);*/
    }
}