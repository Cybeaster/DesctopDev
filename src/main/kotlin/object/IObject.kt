package `object`

import applcation.ObjectApplication
import habitat.Habitat
import javafx.scene.Group
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import java.util.Random
import java.util.function.Predicate

interface IObject {


    open fun destroy(){

    }

    open fun spawn(pane : Pane){
    }

    fun spawnImpl(pane : Pane, name : String){
        val image = Image(ObjectApplication::class.java.getResource(name).toString())
        val imageView = ImageView()
        imageView.image = image
        imageView.fitWidth = 50.0
        imageView.fitHeight = 50.0
        imageView.x = Habitat.fieldWidth  * Random().nextFloat() + Habitat.fieldOffset
        imageView.y = Habitat.fieldHeight  * Random().nextFloat() + Habitat.fieldOffset

        pane.children.add(imageView)
    }
}


class FirstObject : IObject {

    lateinit var pane: Pane
    override fun spawn(paneArg : Pane) {
        pane = paneArg
        super.spawnImpl(pane,"RicardoUpsideDown.jpg")
    }

    companion object {
        var spawnDelay = 2000.0
        var spawnChance = 0.8f
    }

}

class SecondObject : IObject {

    lateinit var pane: Pane
    override fun spawn(paneArg: Pane) {
        pane = paneArg
       super.spawnImpl(pane,"Ricardo.png")
    }

    companion object {
        var spawnDelay = 5000.0
        var spawnChance = 0.5f
    }
}