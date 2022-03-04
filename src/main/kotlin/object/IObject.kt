package `object`

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
        val image = Image(name)
        val imageView = ImageView()
        imageView.image = image
        imageView.x = Habitat.width * Random().nextFloat()
        imageView.y = Habitat.height * Random().nextFloat()
        imageView.fitWidth = 100.0
        imageView.fitHeight = 100.0
        root.children.add(imageView)
    }
}


class FirstObject : IObject {

    lateinit var root: Group
    override fun spawn(rootArg : Group) {
        root = rootArg
        super.spawnImpl(root,"RicardoUpsideDown.png")
    }

    companion object {
        val spawnDelay = 500L
        val spawnChance = 0.8f
    }

}

class SecondObject : IObject {

    lateinit var root: Group
    override fun spawn(rootArg : Group) {
        root = rootArg
       super.spawnImpl(root,"Ricardo.png")
    }

    companion object {
        val spawnDelay = 1000L
        val spawnChance = 0.5f
    }
}