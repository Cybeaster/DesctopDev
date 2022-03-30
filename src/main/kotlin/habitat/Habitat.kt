package habitat
import `object`.IObject
import javafx.scene.Group
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane


class Habitat {

    fun destroyObjects(pane: Pane){

        for (item in pane.children){
            if(item is ImageView){
                item.image = null
            }
        }
        objects.clear()
    }
     public inline fun spawnObject(pane : Pane, clazz: Class<out IObject>) {
        val newRicardo = clazz.getConstructor().newInstance()
         newRicardo.spawn(pane)
         objects.add(newRicardo)
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

    var objects  = arrayListOf<IObject>()
}