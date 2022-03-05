package `object`

import applcation.ObjectApplication
import habitat.Habitat
import javafx.scene.Group
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import java.util.Random
import java.util.function.Predicate

interface IObject {


    open fun destroy(){

    }

    open fun spawn(root : Group){
    }

    fun spawnImpl(root : Group, name : String){
        val image = Image(ObjectApplication::class.java.getResource(name).toString())
        val imageView = ImageView()
        imageView.image = image
        imageView.fitWidth = 50.0
        imageView.fitHeight = 50.0
        imageView.x = Habitat.fieldWidth  * Random().nextFloat() + Habitat.fieldOffset
        imageView.y = Habitat.fieldHeight  * Random().nextFloat() + Habitat.fieldOffset

        root.children.add(imageView)
    }
}


class FirstObject : IObject {

    lateinit var root: Group
    override fun spawn(rootArg : Group) {
        root = rootArg
        super.spawnImpl(root,"RicardoUpsideDown.jpg")
    }

    companion object {
        const val spawnDelay = 2000.0
        const val spawnChance = 0.8f
    }

}

class SecondObject : IObject {

    lateinit var root: Group
    override fun spawn(rootArg : Group) {
        root = rootArg
       super.spawnImpl(root,"Ricardo.png")
    }

    companion object {
        const val spawnDelay = 5000.0
        const val spawnChance = 0.5f
    }
}