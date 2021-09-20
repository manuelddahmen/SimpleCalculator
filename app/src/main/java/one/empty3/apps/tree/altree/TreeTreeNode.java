package one.empty3.apps.tree.altree;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class TreeTreeNode extends TreeNode {
    private final AlgebricTree tree;
    private Method method = null;

    public TreeTreeNode(TreeNode t, Object[] objects, TreeNodeType type) {
        super(t, objects, type);
        tree = new AlgebricTree((String)objects[0], (Map<String, Double>)objects[1]);
        try {
            tree.construct();
            if(objects.length>=3 && objects[2] instanceof String) {
                String call =(String) objects[2];
                if(call.length()>1)
                    method = Math.class.getMethod(call, Double.class);
            }
        } catch (AlgebraicFormulaSyntaxException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Double eval() throws TreeNodeEvalException, AlgebraicFormulaSyntaxException {
        Double r = 0.0;
        r = tree.eval();
        if(method!=null) {
            try {
                r = (Double) method.invoke(r);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return r;

    }
}
