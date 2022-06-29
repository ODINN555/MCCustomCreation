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
           getCurrentGUI().close();

           gui.getCurrentGUI().open(getCurrentGUI().getOwner());
           IChainable curr;
           if(removeCurrent)
           {
               getHandler().removeCurrent();
               curr = getHandler().getCurrentChainable();
           }else curr = this;
            getHandler().onNext(curr,gui);
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
         IChainable prev = getHandler().onPrev(this);
         getCurrentGUI().close();
         if(prev == null)
             return;

         prev.getCurrentGUI().open(getCurrentGUI().getOwner());
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
        //TODO check if this actually works
            ChainHandler.remove(getCurrentGUI().getOwner().getUniqueId());
     }
}
