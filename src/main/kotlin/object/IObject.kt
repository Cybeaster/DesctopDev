package `object`

import applcation.ObjectApplication
import habitat.Habitat
import javafx.scene.Group
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import java.util.Random
import java.util.function.Predicate

abstract class IObject : Comparable<IObject> {

    open fun destroy(){

    }

    open fun spawn(pane : Pane){
    }

    fun spawnImpl(pane : Pane, name : String){
        val image = Image(ObjectApplication::class.java.getResource(name).toString())
        imageView = ImageView()
        imageView.image = image
        imageView.fitWidth = 50.0
        imageView.fitHeight = 50.0
        imageView.x = Habitat.fieldWidth  * Random().nextFloat() + Habitat.fieldOffset
        imageView.y = Habitat.fieldHeight  * Random().nextFloat() + Habitat.fieldOffset

        id = Random().nextInt(0,1000)
        pane.children.add(imageView)
    }
    public var id : Int = -1
    public var currentLifeTime = 0f
    public lateinit var imageView : ImageView

    override fun compareTo(other: IObject): Int {
        return id - other.id
    }
}


class FirstObject : IObject() {

    lateinit var pane: Pane
    override fun spawn(paneArg : Pane) {
        pane = paneArg
        super.spawnImpl(pane,"RicardoUpsideDown.jpg")
        currentLifeTime = lifeTime
    }

    companion object Properties{
        public var spawnDelay = 2000.0
        public var spawnChance = 0.8f
        public var lifeTime = 1000f
    }

}

class SecondObject : IObject() {

    lateinit var pane: Pane

    override fun spawn(paneArg: Pane) {
        pane = paneArg
        super.spawnImpl(pane,"Ricardo.png")
        currentLifeTime = lifeTime
    }

    companion object Properties{
        public var spawnDelay = 2000.0
        public var spawnChance = 0.8f
        public var lifeTime = 1000f
    }
}