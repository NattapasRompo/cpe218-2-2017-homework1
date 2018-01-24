import javax.swing.*;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import java.net.URL;
import java.io.IOException;
import java.awt.Dimension;
import java.awt.GridLayout;
public class tree_demo extends JPanel implements TreeSelectionListener {
    private JEditorPane htmlPane;
    private JTree trees;
    private URL helpURL;
    private static boolean DEBUG = false;

    //Optionally play with line styles.  Possible values are
    //"Angled" (the default), "Horizontal", and "None".
    private static boolean playWithLineStyle = false;
    private static String lineStyle = "Horizontal";

    //Optionally set the look and feel.
    private static boolean useSystemLookAndFeel = false;
    public static Node tree;

    public tree_demo() {
        super(new GridLayout(1, 0));

        //Create the nodes.
        DefaultMutableTreeNode top =
                new DefaultMutableTreeNode(Homework1.tree);
        createNodes(top, Homework1.tree);

        //Create a tree that allows one selection at a time.
        trees = new JTree(top);
        trees.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
        ImageIcon leaf = new ImageIcon(tree_demo.class.getResource("Icon.gif"));
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
        renderer.setClosedIcon(leaf);
        renderer.setOpenIcon(leaf);
        trees.setCellRenderer(renderer);

        //Listen for when the selection changes.
        trees.addTreeSelectionListener(this);

        if (playWithLineStyle) {
            System.out.println("line style = " + lineStyle);
            trees.putClientProperty("JTree.lineStyle", lineStyle);
        }

        //Create the scroll pane and add the tree to it.
        JScrollPane treeView = new JScrollPane(trees);

        //Create the HTML viewing pane.
        htmlPane = new JEditorPane();
        htmlPane.setEditable(false);
        initHelp();
        JScrollPane htmlView = new JScrollPane(htmlPane);

        //Add the scroll panes to a split pane.
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(treeView);
        splitPane.setBottomComponent(htmlView);

        Dimension minimumSize = new Dimension(100, 50);
        htmlView.setMinimumSize(minimumSize);
        treeView.setMinimumSize(minimumSize);
        splitPane.setDividerLocation(100);
        splitPane.setPreferredSize(new Dimension(500, 300));

        //Add the split pane to this panel.
        add(splitPane);
    }

    /**
     * Required by TreeSelectionListener interface.
     */
    public static DefaultMutableTreeNode CHroot;

    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                trees.getLastSelectedPathComponent();
        CHroot = node;

        Node q = (Node)node.getUserObject();

        String ans = GetString(node);
        htmlPane.setText(ans+"="+Homework1.calculate(q));
        Object nodeInfo = node.getUserObject();
        System.out.println(nodeInfo.toString());



        /*Object nodeInfo = node.getUserObject();
        if (node.isLeaf()) {
            BookInfo book = (BookInfo)nodeInfo;
            displayURL(book.bookURL);
            if (DEBUG) {
                System.out.print(book.bookURL + ":  \n    ");
            }
        } else {
            displayURL(helpURL);
        }
        if (DEBUG) {
            System.out.println(nodeInfo.toString());
        }*/
    }
    static int c=0;

    public String GetString( DefaultMutableTreeNode l) {
        String name = "";
        char checko= l.toString().charAt(0);

        if (l == null) return null;
        else {

            if (checko == '+') {
                if (!l.isRoot()) name += "(";

                name += GetString((DefaultMutableTreeNode) l.getChildAt(0));
                name += "+";
                name +=GetString((DefaultMutableTreeNode) l.getChildAt(1));
                if (!l.isRoot()) name += ")";


            } else if (checko == '-') {
                if (!l.isRoot()) name += "(";
                ;
                name+=GetString((DefaultMutableTreeNode) l.getChildAt(0));
                name += "-";
                name+=GetString((DefaultMutableTreeNode) l.getChildAt(1));
                if (!l.isRoot()) name += ")";

                ;

            } else if (checko == '*') {
                if (!l.isRoot()) name += "(";
                ;
                name+=GetString((DefaultMutableTreeNode) l.getChildAt(0));
                name += "*";
                name+=GetString((DefaultMutableTreeNode) l.getChildAt(1));
                if (!l.isRoot()) name += ")";


            } else if (checko == '/') {
                if (!l.isRoot()) name += "(";
                ;
                name+=GetString((DefaultMutableTreeNode) l.getChildAt(0));
                name += "/";
                name+=GetString((DefaultMutableTreeNode) l.getChildAt(1));
                if (!l.isRoot()) name += ")";

                ;

            } else name += l.toString();

        }

        return name;
    }




    /*private class BookInfo {
        public String bookName;
        public URL bookURL;

        public BookInfo(String book, String filename) {
            bookName = book;
            bookURL = getClass().getResource(filename);
            if (bookURL == null) {
                System.err.println("Couldn't find file: "
                        + filename);
            }
        }

        public String toString() {
            return bookName;
        }
    }*/

    private void initHelp() {
        String s = "TreeDemoHelp.html";
        helpURL = getClass().getResource(s);
        if (helpURL == null) {
            System.err.println("Couldn't open help file: " + s);
        } else if (DEBUG) {
            System.out.println("Help URL is " + helpURL);
        }

        displayURL(helpURL);
    }

    private void displayURL(URL url) {
        try {
            if (url != null) {
                htmlPane.setPage(url);
            } else { //null url
                htmlPane.setText("File Not Found");
                if (DEBUG) {
                    System.out.println("Attempted to display a null URL.");
                }
            }
        } catch (IOException e) {
            System.err.println("Attempted to read a bad URL: " + url);
        }
    }

    private void createNodes(DefaultMutableTreeNode top,Node d) {
        if (d.k == '+' || d.k == '-' || d.k == '*' || d.k == '/') {

            DefaultMutableTreeNode ss = new DefaultMutableTreeNode(d.left);
            top.add(ss);
            createNodes(ss,d.left);
            DefaultMutableTreeNode ss2 = new DefaultMutableTreeNode(d.right);
            top.add(ss2);
            createNodes(ss2,d.right);


        }
    }
        /**
         * Create the GUI and show it.  For thread safety,
         * this method should be invoked from the
         * event dispatch thread.
         */
        private static void CreateAndShowGUI() {
            if (useSystemLookAndFeel) {
                try {
                    UIManager.setLookAndFeel(
                            UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    System.err.println("Couldn't use system look and feel.");
                }
            }

            //Create and set up the window.
            JFrame frame = new JFrame("JTree_Binary Tree Calculator");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //Add content to the window.
            frame.add(new tree_demo());

            //Display the window.
            frame.pack();
            frame.setVisible(true);
        }

        public static void main (String[]args)
        {
            //Schedule a job for the event dispatch thread:
            //creating and showing this application's GUI.
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    CreateAndShowGUI();
                }
            });
        }
    }



