package GUI.GUIAtrriutes.ChainGUI;

import Exceptions.InappropriateInheritanceException;
import GUI.AGUI;

/**
 * An interface for chainable GUIs
 */
public interface IChainable {

    /**
     * goes to the given gui as the next one in the chain
     * @param gui a given next gui
     * @param removeCurrent if to remove the current gui
     */
      default void next(IChainable gui,boolean removeCurrent){

            System.out.println("before next: "+gui.getCurrentGUI().getClass().getSimpleName()+" , "+getCurrentGUI().getClass().getSimpleName()+" , "+getHandler());

           IChainable curr;
           if(removeCurrent)
           {
               getHandler().removeCurrent();
               curr = getHandler().getCurrentChainable();
           }else curr = this;
            System.out.println("going to onNext");
            ChainHandler.onNext(curr,gui);
            getCurrentGUI().close();
            gui.getCurrentGUI().open(getCurrentGUI().getOwner());

      }

    /**
     *
     * @return the GUI of the current chain
     */
    default AGUI getCurrentGUI(){
          if(this instanceof AGUI){
              return (AGUI)this;
          }else throw new InappropriateInheritanceException(IChainable.class,this.getClass().getSimpleName()+" Must inherit AGUI");
      }

    /**
     * moving to the previous chainable in the chain. removes the current gui!
     */
    default void prev(){
        System.out.println("before prev: "+getCurrentGUI().getClass().getSimpleName()+" , "+getHandler()+" , Owner: "+getCurrentGUI().getOwner()+". ");
        IChainable prev = ChainHandler.onPrev(this);
        System.out.println("after getPrev");
         getCurrentGUI().close();
        System.out.println("after close");
         if(prev == null)
             return;

         prev.getCurrentGUI().open(getCurrentGUI().getOwner());
         System.out.println("after opening");
     }

    /**
     *
     * @return the handler of this chainable
     */
     default ChainHandler getHandler(){
          return ChainHandler.getHandler(getCurrentGUI().getOwner().getUniqueId());
     }

    /**
     * handles a close of the chainable gui, this is an override for GUI onClose function
     */
    default void onClosing(){
        if(getHandler() == null)
            return;
        System.out.println("onClosing: "+getHandler().getCurrentChainable()+" , "+this);
        if(getHandler().getCurrentChainable() == this)
            ChainHandler.remove(getCurrentGUI().getOwner().getUniqueId());
        System.out.println("after closing: "+getHandler());

    }
}
