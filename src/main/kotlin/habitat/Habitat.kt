package habitat
import `object`.FirstObject
import `object`.IObject
import javafx.scene.Group
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import java.util.LinkedList
import java.util.Objects
import java.util.TreeMap


class Habitat {

    fun destroyObjects(pane: Pane){

        synchronized(objects) {

            for (item in pane.children){
                if(item is ImageView){
                    item.image = null
                }
            }
            objects.clear()
        }
    }

    fun removeObjectFromPanel(obj : IObject, pane: Pane){
        for (paneObj in pane.children){
            if(obj.imageView == paneObj)
            {
                pane.children.remove(paneObj)
                return
            }

        }
    }


     public inline fun spawnObject(pane : Pane, clazz: Class<out IObject>, currentTime : Float) {
        val newRicardo = clazz.getConstructor().newInstance()
         newRicardo.spawn(pane)
         synchronized(objects){
             objects.add(newRicardo)
             objectIds.add(newRicardo.id)
             objectTimeBirth[newRicardo] = currentTime
         }
    }

    public inline fun tickDeleteTimer(deltaTime : Float,pane: Pane){
        for(currentObj in objects){
            currentObj.currentLifeTime -= deltaTime
            if(currentObj.currentLifeTime <= 0){

                synchronized(objects){
                    removeObjectFromPanel(currentObj,pane)
                    objectTimeBirth.remove(currentObj)
                    objectIds.remove(currentObj.id)
                    objects.remove(currentObj)
                }

            }
        }
    }

    fun update() {

    }

    companion object {
        const val width = 1000.0
        const val height = 800.0
        const val fieldOffset = 100.0
        const val fieldWidth = 500.0
        const val fieldHeight = 500.0
    }

    var objects  = LinkedList<IObject>()
    var objectIds = HashSet<Int>()
    var objectTimeBirth = TreeMap<IObject,Float>()
}