package habitat
import `object`.IObject
import javafx.scene.Group


class Habitat {

     fun spawnObject(root : Group,entityClass : Class<IObject>) {
        val newRicardo = entityClass.getConstructor().newInstance()
         newRicardo.spawn(root)
         objects.add(newRicardo)
    }


    fun update() {

    }

    companion object {
        val width = 1000.0
        val height = 1000.0
    }

    lateinit var objects: MutableList<IObject>
}