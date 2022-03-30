package habitat
import `object`.IObject
import javafx.scene.Group
import javafx.scene.image.Image
import javafx.scene.image.ImageView


class Habitat {

    fun destroyObjects(root: Group){

        for (item in root.children){
            if(item is ImageView){
                item.image = null
            }
        }
        objects.clear()
    }
     public inline fun spawnObject(root : Group, clazz: Class<out IObject>) {
        val newRicardo = clazz.getConstructor().newInstance()
         newRicardo.spawn(root)
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