package Nodes;

public interface IParameter<T> {
    T getParameter(Object... objects);
    Class getReturnType();
    Class[] getPrimitiveTypes();
}
