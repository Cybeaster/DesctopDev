package habitat
import `object`.IObject
import javafx.scene.Group
import javafx.scene.image.Image


class Habitat {

    fun destroyObjects(root: Group){
        for (item in root.children){
            if(item.javaClass == Image::javaClass){
                root.children.remove(item)
            }
        }
        objects.clear()
    }
     fun spawnObject(root : Group,entityClass : Class<IObject>) {
        val newRicardo = entityClass.getConstructor().newInstance()
         newRicardo.spawn(root)
         objects.add(newRicardo)
    }


    fun update() {

    }

    companion object {
        val width = 500.0
        val height = 1000.0
    }

    lateinit var objects: MutableList<IObject>
}