package GUI.GUIAtrriutes.ChainGUI;

import Exceptions.InappropriateInheritanceException;
import GUI.GUI;

public interface IChainable {

      default void next(IChainable gui){
           getCurrentGUI().close();

           gui.getCurrentGUI().open(getCurrentGUI().getOwner());
           getHandler().onNext(this,gui);
      }

      default GUI getCurrentGUI(){
          if(this instanceof GUI){
              return (GUI)this;
          }else throw new InappropriateInheritanceException(this.getClass().getSimpleName()+" Must inherit GUI");
      }

     default void prev(){
         IChainable prev = getHandler().onPrev(this);
         getCurrentGUI().close();
         if(prev == null)
             return;

         prev.getCurrentGUI().open(getCurrentGUI().getOwner());
     }

     default ChainHandler getHandler(){
          return ChainHandler.getHandler(getCurrentGUI().getOwner().getUniqueId());
     }
}
